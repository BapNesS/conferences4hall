package org.gdglille.devfest.database

import org.gdglille.devfest.db.Event
import org.gdglille.devfest.db.Speaker
import org.gdglille.devfest.db.Talk
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.gdglille.devfest.models.ScheduleItem

fun org.gdglille.devfest.models.Speaker.convertToModelDb(): Speaker = Speaker(
    id = this.id,
    display_name = this.displayName,
    bio = this.bio,
    company = this.company,
    photo_url = this.photoUrl,
    twitter = this.twitter,
    github = this.github
)

fun ScheduleItem.convertToModelDb(): Talk = Talk(
    id = this.id,
    title = this.talk!!.title,
    start_time = this.startTime,
    end_time = this.endTime,
    room = this.room,
    level = this.talk!!.level,
    abstract_ = this.talk!!.abstract,
    category = this.talk!!.category,
    format = this.talk!!.format,
    open_feedback = this.talk!!.openFeedback,
    open_feedback_url = this.talk!!.openFeedback
)

fun org.gdglille.devfest.models.Event.convertToModelDb(): Event = Event(
    id = this.id,
    name = this.name,
    address = this.address.address,
    date = this.startDate.dropLast(1).toLocalDateTime().format(),
    twitter = this.twitterUrl?.split("twitter.com/")?.get(1),
    twitter_url = this.twitterUrl,
    linkedin = this.name,
    linkedin_url = this.linkedinUrl,
    faq_url = this.faqLink!!,
    coc_url = this.codeOfConductLink!!,
    updated_at = this.updatedAt
)

private fun LocalDateTime.format(): String = "${this.dayOfWeek.name} ${this.dayOfMonth}, ${this.month.name} ${this.year}"
