package com.example.newsflow.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsflow.ui.screens.components.NewsItem
import com.example.newsflow.ui.screens.bookmarks.BookmarkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState(initial = emptyList())
    val bookmarkedUrls = remember(bookmarks) { bookmarks.mapNotNull { it.url }.toSet() }

    Scaffold { padding ->

        if (viewModel.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = padding.calculateTopPadding() + 8.dp,
                    bottom = padding.calculateBottomPadding() + 32.dp
                )
            ) {
                item { TodayHeader() }

                items(
                    items = viewModel.articles,
                    key = { it.url ?: it.title ?: it.hashCode().toString() }
                ) { article ->
                    val url = article.url
                    val isMarked = !url.isNullOrEmpty() && bookmarkedUrls.contains(url)

                    NewsItem(
                        article = article,
                        isBookmarked = isMarked,
                        onBookmarkClick = { bookmarkViewModel.toggle(article) },
                        onOpen = {
                            if (!url.isNullOrEmpty()) {
                                navController.navigate("webview/${Uri.encode(url)}")
                            }
                        }
                    )
                }
            }

        }
    }
}


@Composable
fun TodayHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 16.dp, top = 12.dp, bottom = 16.dp)
    ) {
        Text(
            text = "TUESDAY 2 JUNE",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.4.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Today",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}


