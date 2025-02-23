package com.kmp.newsapp.ui.headline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.coding.meet.newsapp.theme.xSmallPadding
import com.kmp.newsapp.ui.common.EmptyContent
import com.kmp.newsapp.ui.common.NewsListScreen
import com.kmp.newsapp.ui.common.ShimmerEffect
import com.kmp.newsapp.ui.navigation.Route
import com.kmp.newsapp.utils.categoryList
import com.kmp.newsapp.utils.navigationItemsLists
import kmp_news_app.composeapp.generated.resources.Res
import kmp_news_app.composeapp.generated.resources.ic_browse
import kmp_news_app.composeapp.generated.resources.ic_network_error
import kmp_news_app.composeapp.generated.resources.no_news
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineScreen(
    rootNavController: NavController, paddingValues: PaddingValues
) {

    val headlineViewModel = koinViewModel<HeadlineViewModel>()
    val originDirection = LocalLayoutDirection.current

    val uiState by headlineViewModel.newsStateFlow.collectAsState()
    Column(
        Modifier.fillMaxSize().padding(
            start = paddingValues.calculateStartPadding(originDirection),
            end = paddingValues.calculateEndPadding(originDirection),
            bottom = paddingValues.calculateBottomPadding(),
        )
    ) {
        TopAppBar(title = {
            Text(
                text = stringResource(navigationItemsLists[0].title),
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
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = xSmallPadding),
            horizontalArrangement = Arrangement.spacedBy(xSmallPadding, Alignment.CenterHorizontally)
        ) {
            items(categoryList, key = { it }) { category ->
                FilterChip(
                    selected = headlineViewModel.category == category,
                    onClick = {
                        headlineViewModel.category = category
                        headlineViewModel.getHeadlines(headlineViewModel.category)
                    }, label = {
                        Text(category)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                )
            }
        }
        uiState.DisplayResult(
            onLoading = {
                ShimmerEffect()
            },
            onSuccess = { articleList ->
                if (articleList.isEmpty()) {
                    EmptyContent(
                        message = stringResource(Res.string.no_news),
                        icon = Res.drawable.ic_browse,
                        onRetryClick = {
                            headlineViewModel.getHeadlines(headlineViewModel.category)
                        }
                    )
                } else {
                    NewsListScreen(
                        articleList = articleList,
                        rootNavController = rootNavController
                    )
                }
            },
            onError = {
                EmptyContent(
                    message = it,
                    icon = Res.drawable.ic_network_error,
                    onRetryClick = {
                        headlineViewModel.getHeadlines(headlineViewModel.category)
                    }
                )
            }
        )
    }
}