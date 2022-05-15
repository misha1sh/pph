package com.github.servb.pph.network

import com.soywiz.korma.geom.IPointInt
import com.soywiz.korma.geom.PointInt
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.json.Json


@Serializable
data class ServerInitMsg(val version: String);



@Serializable
data class ClientInitMsg(val action: String);


@Serializable
data class ShotMessage(
    @Serializable(with=IPointIntSerializer::class)
    val pos: IPointInt,
    val penalty: Int
);


@Serializable
data class Heartbeat(val v: Int)

@Serializable
data class ServerStartGame(val v: Unit)

fun serializeCode(message: Any): Pair<Int, String> {
    return when (message) {
        is ShotMessage -> Pair(1, Json.encodeToString(message))
        is ServerInitMsg -> Pair(2, Json.encodeToString(message))
        is ClientInitMsg -> Pair(3, Json.encodeToString(message))
        is Heartbeat -> Pair(4, Json.encodeToString(message))
        is ServerStartGame -> Pair(5, Json.encodeToString(message))
        else -> throw IllegalArgumentException("`$message` is not supported")
    }
}

fun deserializeCode(code: Int, json: String): Any {
    return when (code) {
        1 -> Json.decodeFromString<ShotMessage>(json)
        2 -> Json.decodeFromString<ServerInitMsg>(json)
        3 -> Json.decodeFromString<ClientInitMsg>(json)
        4 -> Json.decodeFromString<Heartbeat>(json)
        5 -> Json.decodeFromString<ServerStartGame>(json)
        else -> throw IllegalArgumentException("Invalid message code $code")
    }
}

object IPointIntSerializer : KSerializer<IPointInt> {
    @Serializable
    data class SerializablePointI(val x: Int, val y: Int);

    override val descriptor = SerializablePointI.serializer().descriptor;

    override fun deserialize(decoder: Decoder): IPointInt {
        val p = decoder.decodeSerializableValue(SerializablePointI.serializer())
        return PointInt(p.x, p.y)
    }

    override fun serialize(encoder: Encoder, value: IPointInt) {
        encoder.encodeSerializableValue(SerializablePointI.serializer(), SerializablePointI(value.x, value.y))
    }
}
