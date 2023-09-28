package com.example.dayread.ui.screens.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.dayread.DayRedTopAppBar
import com.example.dayread.R
import com.example.dayread.network.BookInfo
import com.example.dayread.network.Links
import com.example.dayread.ui.AppViewModelProvider
import com.example.dayread.ui.navigation.NavigationDestination
import com.example.dayread.ui.screens.home.NetworkStatus
import com.example.dayread.ui.tools.ErrorBox
import com.example.dayread.ui.tools.LoadingBox
import com.example.dayread.ui.tools.MainDivider
import kotlinx.coroutines.launch

object CategoryDestination : NavigationDestination {
    override val route = "category"
    override val titleRes = R.string.category
    const val categoryEncodedArg = "categoryEncoded"
    val routeWithArgs = "$route/{$categoryEncodedArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateUp: () -> Unit,
    linkWebView: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val gridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    if (bottomSheetScaffoldState.bottomSheetState.isVisible && gridState.isScrollInProgress) {
        LaunchedEffect(key1 = "sheet") {
            scope.launch { bottomSheetScaffoldState.bottomSheetState.hide() }
        }
    }

    LinksBottomSheet(
        sheetScaffoldState = bottomSheetScaffoldState,
        links = uiState.links,
        goToWebView = {
            linkWebView(it)
        }
    ) {
        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                DayRedTopAppBar(
                    title = CategoryDestination.titleRes,
                    scrollBehavior = scrollBehavior,
                    canNavigateBack = true,
                    navigateUp = onNavigateUp
                )
            }
        ) { paddingValues ->
            CategoryBody(
                uiState = uiState,
                buyLinks = {
                    viewModel.updateUiState(uiState.copy(links = it))
                    scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                },
                tryAgain = viewModel::loadBooks,
                gridState = gridState,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun CategoryBody(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    uiState: CategoryUiState,
    buyLinks: (List<Links>) -> Unit,
    tryAgain: () -> Unit,
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
            BooksList(
                buyLinks = buyLinks,
                booksList = uiState.books.results.booksList,
                gridState = gridState,
                modifier = modifier
            )
        }
    }
}

@Composable
fun BooksList(
    modifier: Modifier = Modifier,
    booksList: List<BookInfo>,
    buyLinks: (List<Links>) -> Unit,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium)),
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.medium))
    ) {
        items(
            items = booksList,
            key = { it.rank }
        ) { book ->
            var expanded by remember {
                mutableStateOf(false)
            }

            Card(
                elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.tiny)),
                shape = RectangleShape,
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.medium))
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = !expanded })
                    .animateContentSize()
            ) {
                ImageBox(
                    imageUrl = book.image,
                    rank = book.rank.toString(),
                    modifier = Modifier.fillMaxWidth()
                )
                BookInfoColumn(
                    title = book.title,
                    description = book.description,
                    expanded = expanded,
                    modifier = Modifier.padding(dimensionResource(R.dimen.small))
                )
                if (expanded) {
                    MoreInfoColumn(
                        author = book.author,
                        publisher = book.publisher,
                        contributor = book.contributor,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(R.dimen.small),
                                end = dimensionResource(R.dimen.small),
                                bottom = dimensionResource(R.dimen.small)
                            )
                    )
                } else {
                    Text(
                        text = stringResource(R.string.more_info),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.extra_small))
                    )
                }
                BuyButton(
                    onClick = { buyLinks(book.links) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    imageUrl: String,
    rank: String
) {
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .memoryCacheKey(imageUrl)
                .diskCacheKey(imageUrl)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .placeholder(R.drawable.loading_2to3)
                .error(R.drawable.error_2to3)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.large)
        )
        Text(
            text = rank,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .rotate(45f)
                .padding(end = dimensionResource(R.dimen.small))
        )
    }
}

@Composable
fun BookInfoColumn(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    expanded: Boolean
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = if (expanded) 5 else 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = description.ifBlank { stringResource(R.string.no_description) },
            style = MaterialTheme.typography.bodySmall,
            maxLines = if (expanded) 20 else 5,
            minLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MoreInfoColumn(
    modifier: Modifier = Modifier,
    author: String,
    publisher: String,
    contributor: String
) {
    Column(
        modifier = modifier
    ) {
        val style = MaterialTheme.typography.bodyMedium
        Text(
            text = stringResource(R.string.author, author),
            style = style
        )
        Text(
            text = stringResource(R.string.publisher, publisher),
            style = style
        )
        Text(
            text = stringResource(R.string.contributor, contributor),
            style = style
        )
    }
}

@Composable
fun BuyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MainDivider()
    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.buy_button)
        )
    }
    MainDivider()
}
