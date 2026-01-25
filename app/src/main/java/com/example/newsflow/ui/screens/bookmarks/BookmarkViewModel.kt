package com.example.newsflow.ui.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsflow.data.local.BookmarkEntity
import com.example.newsflow.data.remote.Article
import com.example.newsflow.data.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: BookmarkRepository
) : ViewModel() {

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.getAll().stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000), emptyList()
    )

    fun add(article: Article) {
        viewModelScope.launch { repository.add(article) }
    }

    fun remove(url: String) {
        viewModelScope.launch { repository.remove(url) }
    }

    fun toggle(article: Article) {
        val url = article.url ?: return
        viewModelScope.launch {
            val exists = bookmarks.value.any { it.url == url }
            if (exists) repository.remove(url) else repository.add(article)
        }
    }

}