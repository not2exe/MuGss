package ru.mugss.app

import com.yandex.yatagan.Component
import ru.mugss.domain.PlayerScreenComponent
import ru.mugss.scopes.AppScope

@AppScope
@Component
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
    fun playerScreenComponent():PlayerScreenComponent.Builder

}