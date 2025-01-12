package org.gdglille.devfest.backend.events

import java.text.DecimalFormat
import java.time.LocalDateTime

data class EventAddressDb(
    val address: String = "",
    val country: String = "",
    val countryCode: String = "",
    val city: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)

data class LunchMenuDb(
    val name: String = "",
    val dish: String = "",
    val accompaniment: String = "",
    val dessert: String = ""
)

data class BilletWebConfigurationDb(
    val eventId: String = "",
    val userId: String = "",
    val apiKey: String = "",
)

data class EventDb(
    val year: String = "",
    val conferenceHallId: String = "",
    val openFeedbackId: String? = null,
    val billetWebConfig: BilletWebConfigurationDb? = null,
    val apiKey: String = "",
    val name: String = "",
    val address: EventAddressDb = EventAddressDb(),
    val startDate: String = "",
    val endDate: String = "",
    val formats: Map<String, Int> = emptyMap(),
    val menus: List<LunchMenuDb> = emptyList(),
    val twitterUrl: String? = null,
    val linkedinUrl: String? = null,
    val faqLink: String? = null,
    val codeOfConductLink: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

fun EventDb.openFeedbackUrl(): String? {
    if (this.openFeedbackId == null) return null
    return try {
        val startDate = LocalDateTime.parse(this.startDate.dropLast(1))
        val formatter = DecimalFormat("00")
        val dateFormatted = "${startDate.year}-${formatter.format(startDate.monthValue)}-${formatter.format(startDate.dayOfMonth)}"
        "https://openfeedback.io/${this.openFeedbackId}/$dateFormatted"
    } catch (ignored: Throwable) {
        null
    }
}
