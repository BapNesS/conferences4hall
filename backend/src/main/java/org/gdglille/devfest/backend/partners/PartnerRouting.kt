package org.gdglille.devfest.backend.partners

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.gdglille.devfest.backend.events.EventDao
import org.gdglille.devfest.backend.receiveValidated
import org.gdglille.devfest.models.inputs.PartnerInput

fun Route.registerPartnersRoutes(
    eventDao: EventDao, partnerDao: PartnerDao
) {
    val repository = PartnerRepository(eventDao, partnerDao)

    post("/partners") {
        val eventId = call.parameters["eventId"]!!
        val apiKey = call.request.headers["api_key"]!!
        val partner = call.receiveValidated<PartnerInput>()
        call.respond(HttpStatusCode.Created, repository.create(eventId, apiKey, partner))
    }
    put("/partners/{id}") {
        val eventId = call.parameters["eventId"]!!
        val apiKey = call.request.headers["api_key"]!!
        val partnerId = call.parameters["id"]!!
        val partner = call.receiveValidated<PartnerInput>()
        call.respond(HttpStatusCode.OK, repository.update(eventId, apiKey, partnerId, partner))
    }
}