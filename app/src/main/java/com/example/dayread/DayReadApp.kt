package com.example.dayread

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dayread.ui.navigation.DayReadNavHost

@Composable
fun DayReadApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    linkWebView: (String) -> Unit
) {
    DayReadNavHost(
        navController = navController,
        linkWebView = linkWebView,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayRedCenterTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(title)) },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayRedTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateUp: () -> Unit = {},
    canNavigateBack: Boolean = false
) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                NavigationIcon(navigateUp)
            }
        },
        modifier = modifier
    )
}

@Composable
fun NavigationIcon(
    navigateUp: () -> Unit
) {
    IconButton(onClick = navigateUp) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back)
        )
    }
}
