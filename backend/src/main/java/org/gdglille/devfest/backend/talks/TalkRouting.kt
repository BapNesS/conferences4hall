package org.gdglille.devfest.backend.talks

import org.gdglille.devfest.backend.events.EventDao
import org.gdglille.devfest.backend.receiveValidated
import org.gdglille.devfest.backend.speakers.SpeakerDao
import org.gdglille.devfest.models.inputs.TalkInput
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registerTalksRoutes(
    eventDao: EventDao, speakerDao: SpeakerDao, talkDao: TalkDao
) {
    val repository = TalkRepository(eventDao, speakerDao, talkDao)

    get("/talks") {
        val eventId = call.parameters["eventId"]!!
        call.respond(HttpStatusCode.OK, repository.list(eventId))
    }
    get("/talks/{id}") {
        val eventId = call.parameters["eventId"]!!
        val talkId = call.parameters["id"]!!
        call.respond(HttpStatusCode.OK, repository.get(eventId, talkId))
    }
    post("/talks") {
        val eventId = call.parameters["eventId"]!!
        val apiKey = call.request.headers["api_key"]!!
        val talkInput = call.receiveValidated<TalkInput>()
        call.respond(HttpStatusCode.Created, repository.create(eventId, apiKey, talkInput))
    }
    put("/talks/{id}") {
        val eventId = call.parameters["eventId"]!!
        val apiKey = call.request.headers["api_key"]!!
        val talkId = call.parameters["id"]!!
        val talkInput = call.receiveValidated<TalkInput>()
        call.respond(HttpStatusCode.OK, repository.update(eventId, apiKey, talkId, talkInput))
    }
}
