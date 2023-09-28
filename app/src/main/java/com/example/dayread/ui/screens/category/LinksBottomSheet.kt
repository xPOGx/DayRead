package com.example.dayread.ui.screens.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.dayread.R
import com.example.dayread.network.Links
import com.example.dayread.ui.tools.MainDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinksBottomSheet(
    modifier: Modifier = Modifier,
    sheetScaffoldState: BottomSheetScaffoldState,
    links: List<Links>,
    goToWebView: (String) -> Unit,
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = sheetScaffoldState,
        sheetContent = {
            Column {
                SheetHeader()
                SheetForm(
                    links = links,
                    goToWebView = goToWebView,
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(R.dimen.medium),
                            vertical = dimensionResource(R.dimen.small)
                        )
                )
            }
        }
    ) {
        content()
    }
}

@Composable
fun SheetHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(dimensionResource(R.dimen.small)),
            text = stringResource(R.string.buy_links),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        MainDivider()
    }
}

@Composable
fun SheetForm(
    modifier: Modifier = Modifier,
    links: List<Links>,
    goToWebView: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = links,
            key = { it.name }
        ) {
            Column {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(onClick = { goToWebView(it.url) })
                        .padding(bottom = dimensionResource(R.dimen.small))
                        .fillMaxWidth()
                )
            }
        }
    }
}