package org.jesperancinha.pingoline

import java.net.InetAddress

data class LogData(
    val result: String,
    val comment: String = ""
)

object PingolineConnector {

    fun ping(ipAddress: String): Result<LogData> {
        val nowStamp = System.currentTimeMillis()
        return runCatching {
            val inet = InetAddress.getByName(ipAddress)
            val textResult = inet.run { "Address:${hostAddress}\nHostname:${hostName}" }
            val endStamp = System.currentTimeMillis()
            val comment ="Ping lasted ${endStamp - nowStamp} milliseconds"
            LogData(
                result = textResult,
                comment = comment
            )
        }.onFailure {
            val textResult = "Host not found!\n${it.stackTraceToString()}"
            LogData(result = textResult)
        }
    }

    fun traceroute(ipAddress: String): Result<LogData> {
        val nowStamp = System.currentTimeMillis()
        return runCatching {
            val inets = InetAddress.getAllByName(ipAddress)
            val sb = StringBuffer()
            sb.append(String.format("Hostname:%s", ipAddress))
            for (inet in inets) {
                sb.append(String.format("\n%s", inet.hostAddress))
            }
            val textResult = sb.toString()
            val endStamp = System.currentTimeMillis()
            val comment =
                String.format(
                    "Traceroute lasted %d milliseconds",
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