package com.paligot.conferences.database

import com.paligot.conferences.db.Conferences4HallDatabase
import com.paligot.conferences.models.ScheduleItem
import com.paligot.conferences.repositories.TalkItemUi
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class ScheduleDao(private val db: Conferences4HallDatabase, private val eventId: String) {
    fun fetchSchedules(): Flow<List<TalkItemUi>> =
        db.agendaQueries.selectScheduleItems(eventId) { id, _, time, room, title, speakers, is_favorite ->
            TalkItemUi(id = id, room = room, time = time, title = title, speakers = speakers, isFavorite = is_favorite)
        }.asFlow().mapToList()

    fun markAsFavorite(scheduleId: String, isFavorite: Boolean) {
        db.agendaQueries.markAsFavorite(isFavorite, scheduleId)
    }

    fun insertOrUpdateSchedules(schedules: List<ScheduleItem>, isUpdate: Boolean) = db.transaction {
        schedules.forEach { schedule ->
            if (schedule.talk != null) {
                val talk = schedule.convertToModelDb()
                db.talkQueries.insertTalk(
                    id = talk.id,
                    title = talk.title,
                    date = talk.date,
                    room = talk.room,
                    level = talk.level,
                    abstract_ = talk.abstract_,
                    category = talk.category,
                    format = talk.format,
                    open_feedback = talk.open_feedback
                )
                val speakers = schedule.talk!!.speakers.map { it.convertToModelDb() }
                speakers.forEach {
                    db.speakerQueries.insertSpeaker(
                        id = it.id,
                        display_name = it.display_name,
                        bio = it.bio,
                        company = it.company,
                        photo_url = it.photo_url,
                        twitter = it.twitter,
                        github = it.github
                    )
                    db.talkQueries.insertTalkWithSpeaker(speaker_id = it.id, talk_id = talk.id)
                }
            }
            if (isUpdate) {
                db.agendaQueries.updateSchedule(
                    event_id = eventId,
                    time = schedule.time,
                    room = schedule.room,
                    title = schedule.talk?.title ?: "Pause",
                    speakers = schedule.talk?.speakers?.map { it.displayName } ?: emptyList(),
                    id = schedule.id
                )
            } else {
                db.agendaQueries.insertSchedule(
                    id = schedule.id,
                    event_id = eventId,
                    time = schedule.time,
                    room = schedule.room,
                    title = schedule.talk?.title ?: "Pause",
                    speakers = schedule.talk?.speakers?.map { it.displayName } ?: emptyList(),
                    is_favorite = false
                )
            }
        }
    }
}