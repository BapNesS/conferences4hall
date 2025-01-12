package org.gdglille.devfest.backend.schedulers

import org.gdglille.devfest.backend.date.FormatterPattern
import org.gdglille.devfest.backend.date.format
import org.gdglille.devfest.models.ScheduleItem
import org.gdglille.devfest.models.Talk
import org.gdglille.devfest.models.inputs.ScheduleInput
import java.time.LocalDateTime

fun ScheduleDb.convertToModel(talk: Talk?) = ScheduleItem(
  id = this.id,
  time = LocalDateTime.parse(this.startTime).format(FormatterPattern.HoursMinutes),
  startTime = this.startTime,
  endTime = this.endTime,
  room = this.room,
  talk = talk
)

fun ScheduleInput.convertToDb(endTime: String, talkId: String? = null) = ScheduleDb(
  startTime = this.startTime,
  endTime = endTime,
  room = this.room,
  talkId = talkId
)
