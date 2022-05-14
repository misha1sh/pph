package com.github.servb.pph.network

import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.net.createTcpClient
import readMsgT
import writeMsg

suspend fun runClient(hostIp: String, hostPort: Int): AsyncClient {
    val client = createTcpClient(port=hostPort, host=hostIp)

    val data = client.readMsgT<ServerInitMsg>();
    if (data.version != "1.0") {
        throw IllegalArgumentException("Invalid version")
    }
    client.writeMsg(ClientInitMsg("start_game"))

    return client
}
