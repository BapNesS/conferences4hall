package org.gdglille.devfest.models.inputs

import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventAddressInput(
    val address: String,
    val country: String,
    @SerialName("country_code")
    val countryCode: String,
    val city: String,
    val lat: Double,
    val lng: Double
): Validator {
    override fun validate(): List<String> {
        val errors = arrayListOf<String>()
        if (lat <= 0) errors.add("Your latitude should be positive")
        if (lng <= 0) errors.add("Your longitude should be positive")
        return errors
    }
}

@Serializable
data class LunchMenuInput(
    val name: String,
    val dish: String,
    val accompaniment: String,
    val dessert: String
): Validator {
    override fun validate(): List<String> = emptyList()
}

@Serializable
data class BilletWebConfigInput(
    @SerialName("event_id")
    val eventId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("api_key")
    val apiKey: String
): Validator {
    override fun validate(): List<String> = emptyList()
}

@Serializable
data class EventInput(
    val name: String,
    val address: EventAddressInput,
    @SerialName("open_feedback_id")
    val openFeedbackId: String?,
    @SerialName("billet_web_config")
    val billetWebConfig: BilletWebConfigInput?,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String,
    val formats: Map<String, Int> = emptyMap(),
    val menus: List<LunchMenuInput> = emptyList(),
    @SerialName("twitter_url")
    val twitterUrl: String?,
    @SerialName("linkedin_url")
    val linkedinUrl: String?,
    @SerialName("faq_link")
    val faqLink: String,
    @SerialName("code_of_conduct_link")
    val codeOfConductLink: String,
    @SerialName("update_at")
    val updatedAt: Long = Clock.System.now().epochSeconds
): Validator {
    override fun validate(): List<String> {
        val errors = arrayListOf<String>()
        if (twitterUrl?.contains("twitter.com") == false) errors.add("Your twitter url is malformed")
        if (linkedinUrl?.contains("linkedin.com") == false) errors.add("Your linkedin url is malformed")
        errors.addAll(address.validate())
        formats.values.forEach {
            if (it <= 0) errors.add("The time can't be null or negative")
        }
        return errors
    }
}
