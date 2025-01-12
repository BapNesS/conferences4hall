package org.gdglille.devfest.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.gdglille.devfest.Platform
import org.gdglille.devfest.models.Agenda
import org.gdglille.devfest.models.Attendee
import org.gdglille.devfest.models.Event

class ConferenceApi(
    private val client: HttpClient, private val baseUrl: String, private val eventId: String
) {
    suspend fun fetchEvent() = client.get("$baseUrl/events/$eventId").body<Event>()
    suspend fun fetchAttendee(barcode: String) = client.get("$baseUrl/events/$eventId/billet-web/$barcode")
        .body<Attendee>()
    suspend fun fetchAgenda() = client.get("$baseUrl/events/$eventId/agenda").body<Agenda>()

    companion object {
        fun create(baseUrl: String, eventId: String, enableNetworkLogs: Boolean): ConferenceApi =
            ConferenceApi(baseUrl = baseUrl, eventId = eventId, client = HttpClient(Platform().engine) {
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
            })
    }
}
