package com.paligot.conferences.android.screens.agenda

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paligot.conferences.repositories.AgendaRepository
import com.paligot.conferences.ui.screens.Agenda

@Composable
fun AgendaVM(
    agendaRepository: AgendaRepository,
    modifier: Modifier = Modifier,
    onTalkClicked: (id: String) -> Unit,
) {
    val viewModel: AgendaViewModel = viewModel(
        factory = AgendaViewModel.Factory.create(agendaRepository)
    )
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is AgendaUiState.Loading -> Text("Loading...")
        is AgendaUiState.Failure -> Text("Something wrong happened")
        is AgendaUiState.Success -> Agenda(
            agenda = (uiState.value as AgendaUiState.Success).agenda,
            modifier = modifier,
            onTalkClicked = onTalkClicked,
            onFavoriteClicked = { id, isFavorite ->
                viewModel.markAsFavorite(id, isFavorite)
            }
        )
    }
}
