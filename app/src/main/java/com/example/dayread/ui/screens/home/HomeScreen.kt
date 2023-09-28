package com.example.dayread.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dayread.DayRedCenterTopAppBar
import com.example.dayread.R
import com.example.dayread.network.CategoryInfo
import com.example.dayread.ui.AppViewModelProvider
import com.example.dayread.ui.navigation.NavigationDestination
import com.example.dayread.ui.tools.ErrorBox
import com.example.dayread.ui.tools.LoadingBox
import com.example.dayread.ui.tools.MainDivider

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToCategory: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DayRedCenterTopAppBar(
                title = HomeDestination.titleRes,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        HomeBody(
            uiState = uiState,
            navigateToCategory = navigateToCategory,
            tryAgain = viewModel::loadCategory,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    tryAgain: () -> Unit,
    navigateToCategory: (String) -> Unit
) {
    when (uiState.status) {
        NetworkStatus.Loading -> {
            LoadingBox(modifier)
        }

        NetworkStatus.Error -> {
            ErrorBox(
                tryAgain = tryAgain,
                modifier = modifier
            )
        }

        NetworkStatus.Done -> {
            HomeList(
                modifier = modifier,
                categoryList = uiState.category.categoryList,
                navigateToCategory = navigateToCategory
            )
        }
    }
}

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    categoryList: List<CategoryInfo>,
    navigateToCategory: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.medium))
    ) {
        items(
            items = categoryList,
            key = { it.encodedName }
        ) {
            Card(
                elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.tiny)),
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.small))
                    .fillMaxWidth()
                    .clickable(
                        onClick = { navigateToCategory(it.encodedName) }
                    )
            ) {
                TitleColumn(title = it.displayName)
                InfoRow(
                    updated = it.updated,
                    newDate = it.newDate,
                    oldDate = it.oldDate,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.small))
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    updated: String, newDate: String, oldDate: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.updated))
            Text(text = updated)
        }
        Text(text = stringResource(R.string.published))
        Column {
            Text(text = stringResource(R.string.newest_date, newDate))
            Text(text = stringResource(R.string.oldest_date, oldDate))
        }
    }
}

@Composable
fun TitleColumn(
    modifier: Modifier = Modifier,
    title: String
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.small))
                .fillMaxWidth()
        )
        MainDivider()
    }
}
