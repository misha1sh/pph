package com.github.servb.pph.network

import com.soywiz.korio.async.AsyncCloseable
import com.soywiz.korio.async.launch
import com.soywiz.korio.lang.Cancellable
import com.soywiz.korio.net.AsyncClient
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.getOrElse
import readMsg
import writeMsg
import kotlin.coroutines.coroutineContext

class LocalNetManager (val client: AsyncClient, val connection: AsyncCloseable, val internal: Any) : AsyncCloseable {
    private var job: Job? = null;
    private var messagesToSend = Channel<Any>(Channel.UNLIMITED)
    private var messagesToReceive = Channel<Any>(Channel.UNLIMITED)

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun run() {
        job = GlobalScope.launch {
            loop()
        }
    }

    private suspend fun loop() = coroutineScope {
        when (internal) {
            is LocalNetClient -> {
                while (internal.State() != ClientState.SUCCESS) {
                    delay(100)
                }
            }
            is LocalNetServer -> {
                while (internal.State() != ServerState.SUCCESS) {
                    delay(100)
                }
            }
            else -> throw IllegalArgumentException("Illegal type $internal")
        }
        launch {
            println("Reading started")
            while (client.connected) {
                val msg = messagesToSend.receiveCatching().getOrNull() ?: break
                print("Sending $msg")
                client.writeMsg(msg)
            }
            println("Reading stopped")
        }
        launch {
            println("Writing started")
            while (client.connected) {
                val msg = client.readMsg()
                println("Received $msg")
                messagesToReceive.send(msg)
            }
            println("Writing stopped")
        }
    }

    fun inputMessages(): Channel<Any> = messagesToReceive

    fun sendMessage(message: Any) {
        messagesToSend.trySend(message).getOrThrow();
    }

    suspend fun receiveMessage(): Any {
        return messagesToReceive.receive()
    }

    override suspend fun close() {
        job?.cancel()
        client.close()
        connection.close()
        messagesToSend.close()
        messagesToReceive.close()
    }



}

