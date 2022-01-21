package com.paligot.conferences.backend.schedulers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ScheduleDb(
  val time: String = "",
  val room: String = "",
  val talkId: String? = null
) {
  val id: String = talkId ?: "${time}-pause"
}

@Serializable
data class ScheduleInput(
  val time: String,
  val room: String,
  @SerialName("talk_id")
  val talkId: String? = null
)
