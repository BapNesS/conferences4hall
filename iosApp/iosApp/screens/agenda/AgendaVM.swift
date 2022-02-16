//
//  AgendaVM.swift
//  iosApp
//
//  Created by GERARD on 07/02/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

enum NavigationState: Equatable {
    case none
    case talk(String)
}

struct AgendaVM: View {
    @ObservedObject var viewModel: AgendaViewModel
    @State private var navigationState = NavigationState.none
    let agendaRepository: AgendaRepository
    
    init(agendaRepository: AgendaRepository) {
        self.agendaRepository = agendaRepository
        self.viewModel = AgendaViewModel(repository: agendaRepository)
    }

    var body: some View {
        let uiState = viewModel.uiState
        NavigationView {
            Group {
                switch uiState {
                    case .success(let agenda):
                        Agenda(
                            agenda: agenda,
                            talkItem: { talk in
                                ZStack(alignment: .leading) {
                                    NavigationLink(isActive: Binding.constant(self.navigationState == .talk(talk.id))) {
                                        ScheduleDetailVM(
                                            agendaRepository: self.agendaRepository,
                                            scheduleId: talk.id,
                                            speakerAction: { String in }
                                        )
                                    } label: {
                                        EmptyView()
                                    }
                                    TalkItemView(
                                        talk: talk,
                                        onFavoriteClicked: { id, isFavorite in
                                            viewModel.markAsFavorite(scheduleId: id, isFavorite: isFavorite)
                                        }
                                    )
                                        .onTapGesture {
                                            self.navigationState = NavigationState.talk(talk.id)
                                        }
                                }
                                NavigationLink {
                                    ScheduleDetailVM(
                                        agendaRepository: self.agendaRepository,
                                        scheduleId: talk.id,
                                        speakerAction: { String in }
                                    )
                                } label: {
                                    
                                }
                            }
                        )
                        .onDisappear {
                            viewModel.stop()
                        }
                    case .loading:
                        Agenda(
                            agenda: AgendaUi(talks: [:]),
                            talkItem: { talk in }
                        )
                        .onAppear {
                            viewModel.fetchAgenda()
                        }
                }
            }
            .navigationTitle(Text("Agenda"))
            .navigationBarTitleDisplayMode(.automatic)
        }
    }
}
