package com.example.presentation.ui.home

import com.example.model.BookmarkWord
import com.example.presentation.base.ViewEvent



sealed class HomeViewEvent : ViewEvent {
    data class AddBookmark(val item: BookmarkWord) : HomeViewEvent()
    data class DeleteBookmark(val item: BookmarkWord) : HomeViewEvent()
}