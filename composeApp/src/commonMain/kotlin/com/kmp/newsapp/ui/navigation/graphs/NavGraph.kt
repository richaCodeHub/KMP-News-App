package com.kmp.newsapp.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kmp.newsapp.data.model.Article
import com.kmp.newsapp.ui.bookmark.BookmarkScreen
import com.kmp.newsapp.ui.headline.HeadlineScreen
import com.kmp.newsapp.ui.navigation.Route
import com.kmp.newsapp.ui.newsDetail.NewsDetailScreen
import com.kmp.newsapp.ui.search.SearchScreen
import com.kmp.newsapp.ui.settings.SettingScreen
import com.kmp.newsapp.ui.settings.SettingViewModel
import com.kmp.newsapp.utils.KEY_ARTICLE
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues,
    settingViewModel: SettingViewModel
) {
    NavHost(
        navController = rootNavController,
        startDestination = Route.Headline,
    ) {
        composable<Route.Headline> {
            HeadlineScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
        composable<Route.Search> {
            SearchScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
        composable<Route.Bookmark> {
            BookmarkScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
        composable<Route.NewsDetail> {
            rootNavController.previousBackStackEntry?.savedStateHandle?.get<String>(KEY_ARTICLE)?.let { article ->
                val currentArticle: Article = Json.decodeFromString(article)
                NewsDetailScreen(rootNavController, currentArticle)
            }
        }
        composable<Route.SettingDetail> {
            SettingScreen(navController = rootNavController, settingViewModel)
        }
    }
}