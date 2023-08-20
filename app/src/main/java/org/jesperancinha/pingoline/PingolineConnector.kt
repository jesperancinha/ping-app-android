package org.jesperancinha.pingoline

import java.net.InetAddress

class LogData(
    val result: String,
    val comment: String = ""
)

object PingolineConnector {

    fun ping(ipAddress: String): Result<LogData> {
        val nowStamp = System.currentTimeMillis()
        return kotlin.runCatching {
            val inet = InetAddress.getByName(ipAddress)
            val textResult =
                String.format(
                    "Adress:%s\nHostname:%s",
                    inet.hostAddress,
                    inet.hostName
                )
            val endStamp = System.currentTimeMillis()
            val comment =
                String.format(
                    "Ping lasted %d miliseconds",
                    endStamp - nowStamp
                )
            LogData(
                result = textResult,
                comment = comment
            )
        }.onFailure {
            val textResult = String.format("Host not found!\n%s", it.message)
            LogData(result = textResult)
        }
    }
}