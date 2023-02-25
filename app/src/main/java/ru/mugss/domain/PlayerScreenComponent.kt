package ru.mugss.domain

import com.yandex.yatagan.Component
import ru.mugss.scopes.PlayerScreenScope
import ru.mugss.ui.stateholders.PlayScreenViewModel

@PlayerScreenScope
@Component(isRoot = false, modules = [PlayerScreenModule::class])
interface PlayerScreenComponent {
    @Component.Builder
    interface Builder{
        fun build():PlayerScreenComponent
    }
    fun getViewModel(): PlayScreenViewModel
}