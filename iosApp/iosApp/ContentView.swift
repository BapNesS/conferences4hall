import SwiftUI
import shared

struct ContentView: View {
    var agendaRepository: AgendaRepository

	var body: some View {
        TabView {
            AgendaVM(agendaRepository: agendaRepository)
                .tabItem {
                    Label("Agenda", systemImage: "calendar")
                }
            
            EventVM(agendaRepository: agendaRepository)
                .tabItem {
                    Label("Event", systemImage: "ticket")
                }
        }
	}
}
