package ru.mugss.domain

import dagger.Subcomponent
import ru.mugss.scopes.PlayerScreenScope
import ru.mugss.ui.stateholders.PlayScreenViewModel

@PlayerScreenScope
@Subcomponent(modules = [PlayerScreenModule::class])
interface PlayerScreenComponent {
    fun getViewModel(): PlayScreenViewModel
}