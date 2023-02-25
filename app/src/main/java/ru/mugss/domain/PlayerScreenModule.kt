package ru.mugss.domain

import com.yandex.yatagan.Module
import com.yandex.yatagan.Provides
import ru.mugss.scopes.PlayerScreenScope
import ru.mugss.ui.stateholders.PlayScreenViewModel

interface PlayerScreenModule {
    companion object {
        @Provides
        @PlayerScreenScope
        fun providePlayerScreenViewModel(interactor: TrackInteractor): PlayScreenViewModel =
            PlayScreenViewModel(interactor)
    }
}