package org.gdglille.devfest.backend.network.conferencehall

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ConferenceHallApi(
  private val client: HttpClient,
  private val apiKey: String,
  private val baseUrl: String = "https://conference-hall.io/api"
) {
  suspend fun fetchSpeakerAvatar(url: String) = client.get(url).body<ByteArray>()

  suspend fun fetchEventConfirmed(eventId: String) =
    client.get("$baseUrl/v1/event/$eventId?key=$apiKey&state=confirmed").body<Event>()
  suspend fun fetchEventAccepted(eventId: String) =
    client.get("$baseUrl/v1/event/$eventId?key=$apiKey&state=accepted").body<Event>()

  object Factory {
    fun create(apiKey: String, enableNetworkLogs: Boolean): ConferenceHallApi =
      ConferenceHallApi(
        client = HttpClient(Java.create()) {
          install(ContentNegotiation) {
            json(Json {
              isLenient = true
              ignoreUnknownKeys = true
            })
          }
          if (enableNetworkLogs) {
            install(Logging) {
              logger = Logger.DEFAULT
              level = LogLevel.INFO
            }
          }
        },
        apiKey = apiKey
      )
  }
}
