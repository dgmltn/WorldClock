package com.dgmltn.worldclock.ui.screen.citylist

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import com.dgmltn.worldclock.domain.City
import com.dgmltn.worldclock.ui.MainViewModel
import com.dgmltn.worldclock.ui.component.fadeInOut
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.viewmodel.defaultExtras

// https://github.com/InsertKoinIO/koin/issues/1504#issuecomment-1876905675
//TODO: Fixed here: https://github.com/InsertKoinIO/koin/pull/2207
//TODO: But the official one has a bug with the extras, so we use our own version for now
//TODO: https://github.com/InsertKoinIO/koin/issues/2227
@Composable
inline fun <reified T : ViewModel> koinActivityViewModel(
    qualifier: Qualifier? = null,
    key: String? = null,
    extras: CreationExtras = defaultExtras(LocalActivity.current as ComponentActivity),
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
) = koinViewModel<T>(
    qualifier = qualifier,
    viewModelStoreOwner = LocalActivity.current as ComponentActivity,
    key = key,
    extras = extras,
    scope = scope,
    parameters = parameters,
)

@Composable
fun CityListScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    vm: MainViewModel = koinActivityViewModel(),
) {
    val cities by vm.cities.collectAsState()
    val suggestions by vm.suggestions.collectAsState()

    CityListScreen(
        cities = cities,
        suggestions = suggestions,
        onClickSuggestion = vm::addCity,
        onQueryChange = vm::updateQuery,
        onDeleteCity = vm::deleteCity,
        contentPadding = contentPadding,
        modifier = modifier
    )
}

@Composable
fun CityListScreen(
    cities: List<City>,
    suggestions: List<City>,
    onClickSuggestion: (City) -> Unit,
    onQueryChange: (String) -> Unit,
    onDeleteCity: (City) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val searchBarHeight = 96.dp
    val bottomNavBarHeight = 96.dp
    val topBarHeight = contentPadding.calculateTopPadding() + searchBarHeight
    val bottomBarHeight = contentPadding.calculateBottomPadding()

    Box(modifier = modifier
        .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = topBarHeight, bottom = bottomBarHeight),
            modifier = Modifier
                .fillMaxSize()
                .fadeInOut(topBarHeight, bottomNavBarHeight + 24.dp)
        ) {
            itemsIndexed(items = cities, key = { i, city -> city.id }) { i, city ->
                DismissableCityListItem(
                    city = city,
                    onDismiss = { onDeleteCity(city) },
                    modifier = Modifier.animateItem(),
                    isDismissEnabled = cities.size > 1 // Don't allow deletion if there's only one city
                )
                if (i != cities.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }

        // Search overlay
        Search(
            suggestions = suggestions,
            onQueryChange = onQueryChange,
            onClick = onClickSuggestion,
            contentPadding = contentPadding,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )
    }
}
