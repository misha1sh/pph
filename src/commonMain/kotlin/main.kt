import com.github.servb.pph.config.Data
import com.github.servb.pph.pheroes.game.WinMain
import com.soywiz.klogger.Logger
import com.soywiz.korge.Korge
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.std.openAsZip
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openAsync

lateinit var rootVfs: VfsFile
    private set


suspend fun main() = Korge(
    width = 960,
    height = 720,
    virtualWidth = 320,
    virtualHeight = 240,
    title = "Pocket Palm Heroes",
) {
    Logger.defaultLevel = Logger.Level.INFO

    launch {
        rootVfs =
            resourcesVfs["resources.zip"].readAll().openAsync()
                .openAsZip(caseSensitive = false)  // case insensitive to avoid strict matching
    }.join()

    launch { Data.init(rootVfs)}.join()

    WinMain(this, "")
//        mainDev()
}
