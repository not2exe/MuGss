package ru.mugss.domain


import dagger.Module
import dagger.Provides
import ru.mugss.scopes.PlayerScreenScope
import ru.mugss.ui.stateholders.PlayScreenViewModel

@Module
interface PlayerScreenModule {
    companion object {
        @Provides
        @PlayerScreenScope
        fun providePlayerScreenViewModel(interactor: TrackInteractor): PlayScreenViewModel =
            PlayScreenViewModel(interactor)
    }
}