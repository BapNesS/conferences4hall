package org.gdglille.devfest.backend.talks

import org.gdglille.devfest.backend.NotFoundException
import org.gdglille.devfest.backend.events.EventDao
import org.gdglille.devfest.backend.speakers.SpeakerDao
import org.gdglille.devfest.models.inputs.TalkInput
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class TalkRepository(
    private val eventDao: EventDao,
    private val speakerDao: SpeakerDao,
    private val talkDao: TalkDao
) {
    suspend fun list(eventId: String) = coroutineScope {
        val eventDb = eventDao.get(eventId) ?: throw NotFoundException("Event $eventId Not Found")
        val talks = talkDao.getAll(eventId)
        val asyncItems = talks.map {
            async {
                it.convertToModel(speakerDao.getByIds(eventId, *it.speakerIds.toTypedArray()), eventDb)
            }
        }
        return@coroutineScope asyncItems.awaitAll()
    }

    suspend fun create(eventId: String, apiKey: String, talkInput: TalkInput) = coroutineScope {
        eventDao.getVerified(eventId, apiKey)
        val talkDb = talkInput.convertToDb()
        val id = talkDao.createOrUpdate(eventId, talkDb)
        eventDao.updateUpdatedAt(eventId)
        return@coroutineScope id
    }

    suspend fun get(eventId: String, talkId: String) = coroutineScope {
        val eventDb = eventDao.get(eventId) ?: throw NotFoundException("Event $eventId Not Found")
        val talk = talkDao.get(eventId, talkId) ?: throw NotFoundException("Talk $talkId Not Found")
        eventDao.updateUpdatedAt(eventId)
        return@coroutineScope talk.convertToModel(speakerDao.getByIds(eventId, *talk.speakerIds.toTypedArray()), eventDb)
    }

    suspend fun update(eventId: String, apiKey: String, talkId: String, talkInput: TalkInput) = coroutineScope {
        eventDao.getVerified(eventId, apiKey)
        talkDao.createOrUpdate(eventId, talkInput.convertToDb(id = talkId))
        eventDao.updateUpdatedAt(eventId)
        return@coroutineScope talkId
    }
}