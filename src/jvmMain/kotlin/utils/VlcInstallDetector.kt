package utils


import java.io.BufferedReader
import java.io.InputStreamReader

fun isVLCInstalled(): Boolean {
    if (OsDetector.isWindows()) return true
    try {
        val process = Runtime.getRuntime().exec("which vlc")
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            if (line!!.contains("/vlc")) {
                return true
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}