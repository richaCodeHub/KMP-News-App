package com.kmp.newsapp.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.coding.meet.newsapp.theme.xSmallPadding
import com.kmp.newsapp.ui.common.EmptyContent
import com.kmp.newsapp.ui.common.NewsListScreen
import com.kmp.newsapp.ui.common.ShimmerEffect
import com.kmp.newsapp.ui.navigation.Route
import com.kmp.newsapp.ui.search.components.SearchBar
import com.kmp.newsapp.utils.navigationItemsLists
import kmp_news_app.composeapp.generated.resources.Res
import kmp_news_app.composeapp.generated.resources.ic_browse
import kmp_news_app.composeapp.generated.resources.ic_network_error
import kmp_news_app.composeapp.generated.resources.type_to_search
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues,
) {
    val searchViewModel = koinViewModel<SearchViewModel>()
    val uiState by searchViewModel.newsStateFlow.collectAsState()
    val originDirection = LocalLayoutDirection.current

    Column(
        modifier = Modifier.fillMaxSize().padding(
            start = paddingValues.calculateStartPadding(originDirection),
            end = paddingValues.calculateEndPadding(originDirection),
            bottom = paddingValues.calculateBottomPadding()
        ),
        verticalArrangement = Arrangement.spacedBy(xSmallPadding)
    ) {
        TopAppBar(title = {
            Text(
                text = stringResource(navigationItemsLists[1].title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }, actions = {
            IconButton(onClick = {
                rootNavController.navigate(Route.SettingDetail)
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                )
            }
        })
        SearchBar(
            text = searchViewModel.query,
            onValueChange = { q ->
                searchViewModel.query = q
            },
            onSearch = { q ->
                if (q.trim().isNotEmpty()) {
                    searchViewModel.searchQuery(q.trim())
                }
            },
        )
        uiState.DisplayResult(
            onIdle = {
                EmptyContent(
                    message = stringResource(Res.string.type_to_search),
                    icon = Res.drawable.ic_browse,
                    isOnRetryBtnVisible = false
                )
            },
            onLoading = {
                ShimmerEffect()
            },
            onSuccess = { articleList ->
                if (articleList.isEmpty()) {
                    EmptyContent(
                        message = stringResource(Res.string.type_to_search),
                        icon = Res.drawable.ic_browse, onRetryClick = {
                            if (searchViewModel.query.trim().isNotEmpty()) {
                                searchViewModel.searchQuery(searchViewModel.query.trim())
                            }
                        })
                } else {
                    NewsListScreen(
                        articleList = articleList,
                        rootNavController = rootNavController
                    )
                }
            },
            onError = {
                EmptyContent(message = it, icon = Res.drawable.ic_network_error, onRetryClick = {
                    if (searchViewModel.query.trim().isNotEmpty()) {
                        searchViewModel.searchQuery(searchViewModel.query.trim())
                    }
                })
            }
        )
    }
}