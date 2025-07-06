package com.dgmltn.worldclock.ui.screen.citylist

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.R
import com.dgmltn.worldclock.domain.ALL_CITIES
import com.dgmltn.worldclock.domain.City
import com.dgmltn.worldclock.ui.component.fadeInOut
import com.dgmltn.worldclock.ui.theme.LocalColorPalette
import com.dgmltn.worldclock.ui.theme.WcPreview
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    suggestions: List<City>,
    onQueryChange: (String) -> Unit,
    onClick: (City) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    var query by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            windowInsets = WindowInsets(left = 0.dp, top = contentPadding.calculateTopPadding(), right = 0.dp, bottom = contentPadding.calculateBottomPadding()),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchTextField(
                    query = query,
                    onQueryChange = {
                        query = it
                        onQueryChange(it)
                    },
                    onSearch = {},
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                dividerColor = Color.Transparent
            ),
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Column(
                modifier = Modifier
                    .fadeInOut(Dp.Unspecified, 96.dp + 24.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 96.dp)
            ) {
                suggestions.forEach { city ->
                    SearchResultItem(
                        city = city,
                        onClick = {
                            onClick(city)
                            query = ""
                            expanded = false
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val focused = interactionSource.collectIsFocusedAsState().value
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TextField(
        shape = SearchBarDefaults.inputFieldShape,
        singleLine = true,
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .shadow(24.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { if (it.isFocused) onExpandedChange(true) }
        ,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        colors = TextFieldDefaults.colors(
            focusedTextColor = LocalColorPalette.current.searchBoxText,
            focusedContainerColor = LocalColorPalette.current.searchBoxContainer,
            unfocusedContainerColor = LocalColorPalette.current.searchBoxContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
        interactionSource = interactionSource,
        placeholder = { Text(stringResource(R.string.Search_Cities)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
    )

    val shouldClearFocus = !expanded && focused
    LaunchedEffect(expanded) {
        if (shouldClearFocus) {
            // Not strictly needed according to the motion spec, but since the animation
            // already has a delay, this works around b/261632544.
            delay(100.milliseconds)
            focusManager.clearFocus()
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    var suggestions by remember { mutableStateOf(ALL_CITIES) }

    WcPreview {
        Search(
            suggestions = suggestions,
            onQueryChange = { query ->
                suggestions = ALL_CITIES.filter { it.name.contains(query, ignoreCase = true) }
            },
            onClick = {},
            contentPadding = PaddingValues(top = 24.dp)
        )
    }
}