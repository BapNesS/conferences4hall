package com.paligot.conferences.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paligot.conferences.repositories.TalkUi
import com.paligot.conferences.ui.components.appbars.TopAppBar
import com.paligot.conferences.ui.components.speakers.SpeakerBox
import com.paligot.conferences.ui.components.speakers.SpeakerItem
import com.paligot.conferences.ui.components.talks.TalkSection
import com.paligot.conferences.ui.components.talks.talk
import com.paligot.conferences.ui.theme.Conferences4HallTheme

@Composable
fun ScheduleDetail(
    talk: TalkUi,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onSpeakerClicked: (id: String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = "Talk Details",
                navigationIcon = { Back(onClick = onBackClicked) }
            )
        },
        content = {
            val contentPadding = 8.dp
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    TalkSection(
                        talk = talk,
                        modifier = Modifier.padding(horizontal = contentPadding)
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        talk.speakers.forEach {
                            SpeakerBox(onClick = { onSpeakerClicked(it.id) }) {
                                SpeakerItem(
                                    speakerUi = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = contentPadding, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun TalkDetailPreview() {
    Conferences4HallTheme {
        ScheduleDetail(
            talk = talk,
            onBackClicked = {},
            onSpeakerClicked = {}
        )
    }
}