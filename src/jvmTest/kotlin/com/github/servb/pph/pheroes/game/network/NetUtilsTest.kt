package com.github.servb.pph.pheroes.game.network

import com.github.servb.pph.network.ClientInitMsg
import com.github.servb.pph.network.ServerInitMsg
import com.soywiz.klock.seconds
import com.soywiz.korio.async.suspendTest
import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.net.FakeAsyncClient
import com.soywiz.korio.stream.*
import com.soywiz.korio.util.OS
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import readMsg
import readMsgT
import writeMsg

class NetUtilsTest : StringSpec({
    "writeMsg" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val client = FakeAsyncClient()
            client.writeMsg(ClientInitMsg("test_action"))

            client.clientToServer.readS32LE() shouldBe 3 // code
            client.clientToServer.readS32LE() shouldBe 24 // len
            client.clientToServer.readString(24) shouldBe """{"action":"test_action"}""" // string
        }
    }

    "readMsg" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val client = FakeAsyncClient()

            client.serverToClient.write32LE(3) // code
            client.serverToClient.write32LE(24) // len
            client.serverToClient.writeString("""{"action":"test_action"}""") // string

            client.readMsg() shouldBe ClientInitMsg("test_action")

        }
    }

    "readMsgT" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val client = FakeAsyncClient()

            client.serverToClient.write32LE(3) // code
            client.serverToClient.write32LE(24) // len
            client.serverToClient.writeString("""{"action":"test_action"}""") // string

            client.readMsgT<ClientInitMsg>() shouldBe ClientInitMsg("test_action")

        }
    }

    "readMsgT wrong type" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val client = FakeAsyncClient()

            client.serverToClient.write32LE(3) // code
            client.serverToClient.write32LE(24) // len
            client.serverToClient.writeString("""{"action":"test_action"}""") // string

            val exception = shouldThrow<IllegalArgumentException> {
                client.readMsgT<ServerInitMsg>()
            }
            exception.message shouldBe "Wrong message type"
        }
    }
})