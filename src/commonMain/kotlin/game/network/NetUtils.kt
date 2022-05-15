import com.github.servb.pph.network.deserializeCode
import com.github.servb.pph.network.serializeCode
import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.stream.readS32LE
import com.soywiz.korio.stream.readString
import com.soywiz.korio.stream.write32LE
import com.soywiz.korio.stream.writeString


suspend inline fun AsyncClient.writeMsg(msg: Any) {
    val (code, string) = serializeCode(msg)

    write32LE(code)
    write32LE(string.length)
    writeString(string)
}

suspend inline fun AsyncClient.readMsg() : Any {
    val code = readS32LE()
    val len = readS32LE()
    val string = readString(len)
    return deserializeCode(code, string)
}

suspend inline fun <reified T> AsyncClient.readMsgT() : T {
    val msg = readMsg()
    if (msg is T) {
        return msg
    }
    throw IllegalArgumentException("Wrong message type")
}
