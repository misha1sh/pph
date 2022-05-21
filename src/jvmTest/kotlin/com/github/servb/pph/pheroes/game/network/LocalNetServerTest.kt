package com.github.servb.pph.pheroes.game.network

import com.github.servb.pph.network.*
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.async.suspendTest
import com.soywiz.korio.net.createTcpClient
import com.soywiz.korio.net.createTcpServer
import com.soywiz.korio.util.OS
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import readMsgT
import writeMsg

class LocalNetServerTest : StringSpec({
    "Connects successfully" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val server = LocalNetServer(33500)
            server.State() shouldBe ServerState.EMPTY

            val job = launchImmediately {
                server.loop()
            }
            delay(10.milliseconds)
            server.State() shouldBe ServerState.SEARCHING

            val client = createTcpClient("0.0.0.0", 33500)
            client.readMsgT<ServerInitMsg>() shouldBe ServerInitMsg("1.0")
            client.writeMsg(ClientInitMsg("start_game"))


            val heartbeat = client.readMsgT<Heartbeat>()
            server.convert()
            server.State() shouldBe ServerState.FOUND

            client.writeMsg(heartbeat)
            client.readMsgT<ServerStartGame>()
            server.State() shouldBe ServerState.SUCCESS

//            server.close()
//            job.cancel()

        }
    }

    "Cancel successfully" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val server = LocalNetServer(33501)
            server.State() shouldBe ServerState.EMPTY

            server.run()

            delay(10.milliseconds)
            server.State() shouldBe ServerState.SEARCHING

            val client = createTcpClient("0.0.0.0", 33501)
            client.readMsgT<ServerInitMsg>() shouldBe ServerInitMsg("1.0")
            client.writeMsg(ClientInitMsg("start_game"))

            val heartbeat = client.readMsgT<Heartbeat>()
            server.hasClient() shouldBe true

            server.close()
            server.State() shouldBe ServerState.EMPTY
        }
    }
})