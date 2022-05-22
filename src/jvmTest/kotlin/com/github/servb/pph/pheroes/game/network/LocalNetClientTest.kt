package com.github.servb.pph.pheroes.game.network

import com.github.servb.pph.network.*
import com.soywiz.klock.*
import com.soywiz.korio.async.*
import com.soywiz.korio.net.createTcpServer
import com.soywiz.korio.util.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import readMsgT
import writeMsg

class LocalNetClientTest : StringSpec({
    "Connects successfully" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val server = createTcpServer(33456)
            val client = LocalNetClient("localhost", 33456)

            launchImmediately {
                client.loop()
            }

            val client2 = server.accept()

            client2.writeMsg(ServerInitMsg("1.0"))

            client2.readMsgT<ClientInitMsg>().action shouldBe "start_game"
            delay(10.milliseconds)
            client.connected() shouldBe true

            client2.writeMsg(ServerStartGame(Unit))

            delay(100.milliseconds)
            client.State() shouldBe ClientState.STARTED
            client.started() shouldBe true

            client.convert().connected shouldBe true
            client.State() shouldBe ClientState.SUCCESS

        }
    }

    "Close before connecting working" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val client = LocalNetClient("localhost", 33460)

            launchImmediately {
                client.loop()
            }

            client.close()

            client.State() shouldBe ClientState.EMPTY
        }
    }

    "Close after connecting working" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val server = createTcpServer(33457)
            val client = LocalNetClient("localhost", 33457)

            launchImmediately {
                client.loop()
            }

            val client2 = server.accept()

            client2.writeMsg(ServerInitMsg("1.0"))

            client2.readMsgT<ClientInitMsg>().action shouldBe "start_game"
            delay(10.milliseconds)
            client.connected() shouldBe true

            client.close()

            client.State() shouldBe ClientState.EMPTY
        }
    }

    "Heartbeat working" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val server = createTcpServer(33458)
            val client = LocalNetClient("localhost", 33458)

            launchImmediately {
                client.loop()
            }

            val client2 = server.accept()

            client2.writeMsg(ServerInitMsg("1.0"))

            client2.readMsgT<ClientInitMsg>().action shouldBe "start_game"
            client.connected() shouldBe true

            repeat(5) { i ->
                client2.writeMsg(Heartbeat(i))
                client2.readMsgT<Heartbeat>().v shouldBe i
            }

            client.close()

            client.State() shouldBe ClientState.EMPTY
        }
    }
})