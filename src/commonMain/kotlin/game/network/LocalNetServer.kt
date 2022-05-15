package com.github.servb.pph.network

import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launch
import com.soywiz.korio.lang.CancelException
import com.soywiz.korio.lang.Closeable
import com.soywiz.korio.net.*
import kotlinx.coroutines.*
import readMsg
import readMsgT
import writeMsg

data class ServerWithOneClient(val server: AsyncServer, val client: AsyncClient)
enum class ServerState {
    EMPTY,
    SEARCHING,
    FOUND,
    SUCCESS
}

class LocalNetServer(val port: Int) : Closeable {
    private var server: AsyncServer? = null
    private var job: Job? = null

    private var connectedClient: AsyncClient? = null

    @OptIn(DelicateCoroutinesApi::class)
    fun run() {
        job = GlobalScope.launch {
            loop()
        }
    }

    private var state = ServerState.EMPTY
    fun State() = state

    suspend fun loop() {
        state = ServerState.SEARCHING
        while (state == ServerState.SEARCHING) {
            try {
                if (server == null) {
                    println("SERVER: Creating server")
                    server = createTcpServer(port=port, host="0.0.0.0")
                }

                println("SERVER: Searching")
                val client = server!!.accept();
                client.writeMsg(ServerInitMsg("1.0"))
                val clientMsg = client.readMsgT<ClientInitMsg>()
                if (clientMsg.action == "start_game") {
                    connectedClient = client
                    println("SERVER: Found client")
                    var i = 0
                    while (connectedClient!!.connected) {
                        val v = i++;
                        client.writeMsg(Heartbeat(v))
                        println("SERVER: Heartbeat sent " + v)

                        // TODO: a little bit unsafe
                        withTimeout(1000) {
                            var vv = client.readMsgT<Heartbeat>()
                            println("SERVER: Heartbeat received " + v)
                        }
                        when(state) {
                            ServerState.EMPTY -> {
                                println("SERVER: Safe cancelling")
                                break
                            }
                            ServerState.SEARCHING -> {
                                delay(100.milliseconds)
                            }
                            ServerState.FOUND -> {
                                client.writeMsg(ServerStartGame(Unit))
                                println("SERVER: Search successful")
                                state = ServerState.SUCCESS
                                return
                            }
                        }
                    }
                    connectedClient?.close()
                    println("SERVER: Client disconnected")
                    connectedClient = null
                } else {
                    client.close()
                }
            } catch (ex: Throwable) {
                if (ex is CancelException || (ex.message?.contains("cancel")) == true) {
                    println("SERVER: Searching force cancelled")
                    return
                }
                connectedClient?.close()
                println("SERVER: Client disconnected")
                connectedClient = null
                println("ERROR in server: ${ex.message}")
                ex.printStackTrace()
                delay(1.seconds)
            }
        }

        println("SERVER: Searching safe cancelled")
    }

    fun hasClient(): Boolean {
        return connectedClient != null && connectedClient?.connected == true
    }

    fun convert(): ServerWithOneClient {
        if (state != ServerState.SEARCHING)
            throw IllegalStateException("Not searching")
        if (connectedClient == null) {
            throw IllegalStateException("No connected client")
        }

        state = ServerState.FOUND

        return ServerWithOneClient(server!!, connectedClient!!)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun close() {
        if (state == ServerState.SEARCHING && job != null) {
            println("SERVER: Force cancel job")
            state = ServerState.EMPTY
            job?.cancel()
        }
        GlobalScope.launch {
            if (state == ServerState.EMPTY && server != null) {
                println("SERVER: Force closed")
                server!!.close()
            }
        }
    }
}

