package com.paligot.conferences.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paligot.conferences.repositories.AgendaUi
import com.paligot.conferences.ui.components.talks.ScheduleItem
import com.paligot.conferences.ui.components.talks.talkItem
import com.paligot.conferences.ui.theme.Conferences4HallTheme

@Composable
fun Agenda(
    agenda: AgendaUi,
    modifier: Modifier = Modifier,
    onTalkClicked: (id: String) -> Unit,
    onFavoriteClicked: (id: String, isFavorite: Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(agenda.talks.keys.toList()) {
            ScheduleItem(
                time = it,
                talks = agenda.talks[it]!!,
                onTalkClicked = onTalkClicked,
                onFavoriteClicked = onFavoriteClicked
            )
        }
    }
}

@Preview
@Composable
fun AgendaPreview() {
    Conferences4HallTheme {
        Scaffold {
            Agenda(
                agenda = AgendaUi(
                    talks = mapOf(
                        "10:00" to arrayListOf(talkItem, talkItem),
                        "11:00" to arrayListOf(talkItem, talkItem),
                        "12:00" to arrayListOf(talkItem, talkItem),
                        "13:00" to arrayListOf(talkItem, talkItem),
                        "14:00" to arrayListOf(talkItem, talkItem),
                        "15:00" to arrayListOf(talkItem, talkItem),
                    ),
                ),
                onTalkClicked = {},
                onFavoriteClicked = { _, _ -> }
            )
        }
    }
}