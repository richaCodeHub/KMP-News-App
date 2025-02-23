package com.kmp.newsapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.coding.meet.newsapp.theme.cardMinSize
import com.coding.meet.newsapp.theme.mediumPadding
import com.kmp.newsapp.data.model.Article
import com.kmp.newsapp.ui.navigation.Route
import com.kmp.newsapp.utils.KEY_ARTICLE
import com.kmp.newsapp.utils.getRandomId
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun NewsListScreen(
    articleList: List<Article>,
    rootNavController: NavController
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(cardMinSize),
        verticalItemSpacing = mediumPadding,
        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
        contentPadding = PaddingValues(mediumPadding),
    ) {
        items(articleList, key = {
            it.publishedAt + getRandomId()
        }) { item ->
            ArticleItem(article = item, onClick = {

                val articleStr = Json.encodeToString(item)
                rootNavController.currentBackStackEntry?.savedStateHandle?.apply {
                    set(KEY_ARTICLE, articleStr)
                }
                rootNavController.navigate(Route.NewsDetail)
            })
        }
    }

}