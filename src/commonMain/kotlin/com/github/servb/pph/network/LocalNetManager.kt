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

class LocalNetManager (val client: AsyncClient, val connection: AsyncCloseable) : AsyncCloseable {
    companion object Factory {
        // will return only when other client connected
        suspend fun hostServer(): Result<LocalNetManager> {
            return runCatching {
                runServer(3337)
            }.map {
                return Result.success(LocalNetManager(it.client, it.server))
            }
        }

        suspend fun connectAsClient(hostIp: String): Result<LocalNetManager> {
            return runCatching {
                runClient(hostIp, 3337)
            }.map {
                return Result.success(LocalNetManager(it, it))
            }
        }
    }


//    private var scope = MainScope()
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
        launch {
            println("Reading started")
            while (client.connected) {
                val msg = messagesToSend.receiveCatching().getOrNull() ?: break
                client.writeMsg(msg)
            }
            println("Reading stopped")
        }
        launch {
            println("Writing started")
            while (client.connected) {
                val msg = client.readMsg()
                messagesToReceive.send(msg)
            }
            println("Writing stopped")
        }
    }


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

