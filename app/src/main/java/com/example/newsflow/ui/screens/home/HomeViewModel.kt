package com.example.newsflow.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsflow.data.remote.Article
import com.example.newsflow.data.remote.NewApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsApi: NewApi) : ViewModel() {

    var articles: MutableList<Article> = mutableListOf()

    var isLoading by mutableStateOf(true)

    init {
        Log.d("APPSTART","fETCH CALLED")
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            try {
                Log.d("APPSTART",".................")
                val response = newsApi.getTopHeadlines(
                    apiKey = "7556654e1e8cee15d06ce3eecf88dd9b"
                )
                articles = response.articles.toMutableList()
                Log.d("APPSTART",articles.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}