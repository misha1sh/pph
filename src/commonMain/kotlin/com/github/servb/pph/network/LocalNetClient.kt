package com.github.servb.pph.network

import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.withTimeout
import com.soywiz.korio.lang.CancelException
import com.soywiz.korio.lang.Closeable
import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.net.createTcpClient
import kotlinx.coroutines.*
import readMsg
import readMsgT
import writeMsg


enum class ClientState {
    EMPTY,
    SEARCHING,
    STARTED,
    SUCCESS
}


class LocalNetClient(val hostIp: String, val hostPort: Int) : Closeable {
    private var connectedClient : AsyncClient? = null
    private var job: Job? = null

    private var state = ClientState.EMPTY
    fun State() = state

    @OptIn(DelicateCoroutinesApi::class)
    fun run() {
        job = GlobalScope.launch {
            loop()
        }
    }

    suspend fun loop() {
        state = ClientState.SEARCHING
        while (state == ClientState.SEARCHING) {
            try {
                println("CLIENT: Trying to connect")
                val client = createTcpClient(port=hostPort, host=hostIp)

                val data = client.readMsgT<ServerInitMsg>();
                if (data.version != "1.0") {
                    throw IllegalArgumentException("Invalid version")
                }
                client.writeMsg(ClientInitMsg("start_game"))

                connectedClient = client
                println("CLIENT: Connected")
                while (connectedClient!!.connected) {
                    val msg = withTimeout(1000.milliseconds) {
                        return@withTimeout connectedClient!!.readMsg()
                    }
                    when (msg) {
                        is Heartbeat -> {
                            println("CLIENT: Heartbeat" + msg.v)
                            client.writeMsg(msg)
                        }
                        is ServerStartGame -> {
                            println("CLIENT: Game started")
                            state = ClientState.STARTED
                            return
                        }
                        else -> throw IllegalArgumentException("Unknown message")
                    }
                    delay(100.milliseconds)
                }
                connectedClient = null;

            } catch (ex: Throwable) {
                if (ex is CancelException) {
                    println("CLIENT: Searching cancelled")
                    throw ex
                }
                println("ERROR in client: ${ex.message}")
                connectedClient?.close()
                connectedClient = null
                delay(1.seconds)
            }
        }
        println("CLIENT: Safe cancelled")
    }

    fun connected(): Boolean = connectedClient != null

    fun started(): Boolean = connected() && state == ClientState.STARTED

    fun convert(): AsyncClient {
        if (state != ClientState.STARTED) {
            throw IllegalStateException("Not started")
        }
        if (connectedClient == null) {
            throw IllegalArgumentException("No client")
        }
        state = ClientState.SUCCESS
        return connectedClient!!
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun close() {
        if (state != ClientState.SUCCESS) {
            println("CLIENT: force cancelling")
            state = ClientState.EMPTY
            job?.cancel()
        }
        GlobalScope.launch {
            if (state != ClientState.SUCCESS) {
                println("CLIENT: closing")
                connectedClient?.close()
            }
        }
    }
}


