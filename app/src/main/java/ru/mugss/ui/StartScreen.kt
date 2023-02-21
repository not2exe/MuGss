package ru.mugss.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navigate
import ru.mugss.R

@Composable
fun StartScreen(navController: NavController<Screen>) {
    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(state)
    ) {
        val modifierCenter = Modifier
            .align(CenterHorizontally)
            .padding(8.dp)
        val buttonShape = RoundedCornerShape(CornerSize(8.dp))
        val buttonModifier = modifierCenter
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
        LazyRow(
            content = {
            }, modifier = modifierCenter
                .height(300.dp)
        )
        Button(
            modifier = modifierCenter
                .width(112.dp)
                .height(164.dp)
                .padding(top = 60.dp),
            shape = buttonShape,
            onClick = { navController.navigate(Screen.PlayScreen) }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_play_arrow_96),
                contentDescription = null
            )
        }
        Button(
            modifier = buttonModifier.padding(top = 40.dp),
            shape = buttonShape,
            onClick = { navController.navigate(Screen.TopScreen) }) {
            ButtonRow(idIcon = R.drawable.baseline_filter_list_48, idString = R.string.top)
        }
        Button(
            modifier = buttonModifier,
            shape = buttonShape,
            onClick = { navController.navigate(Screen.AchievementsScreen) }) {
            ButtonRow(idIcon = R.drawable.baseline_star_48, idString = R.string.achievements)
        }
        Button(
            modifier = buttonModifier,
            shape = buttonShape,
            onClick = { navController.navigate(Screen.SettingsScreen) }) {
            ButtonRow(
                idIcon = R.drawable.baseline_settings_48,
                idString = R.string.settings,
            )
        }
    }
}

@Composable
fun ButtonRow(idIcon: Int, idString: Int) {
    Icon(
        painter = painterResource(id = idIcon),
        contentDescription = null,
        modifier = Modifier
            .padding(horizontal = 10.dp)
    )
    Text(
        text = stringResource(id = idString),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(horizontal = 8.dp)
    )

}