package com.github.servb.pph.network

import com.soywiz.korio.net.*
import readMsgT
import writeMsg

data class ServerWithOneClient(val server: AsyncServer, val client: AsyncClient)

suspend fun runServer(port: Int): ServerWithOneClient {
    val server = createTcpServer(port=port, host="0.0.0.0")

    while (true) {
        try {
            val client = server.accept();
            client.writeMsg(ServerInitMsg("1.0"))
            val clientMsg = client.readMsgT<ClientInitMsg>()
            if (clientMsg.action == "start_game") {
                return ServerWithOneClient(server, client)
            } else {
                client.close()
            }
        } catch (ex: Throwable) {
            println("ERROR in server: ${ex.message}")
        }
    }
}
