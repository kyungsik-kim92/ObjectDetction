package com.example.objectdetection.ui.search.detect

import com.example.objectdetection.base.ViewState
import com.example.objectdetection.network.response.DictionaryResponseItem

sealed class SelectDetectionViewState : ViewState {
    data class GetSearchWord(val word: DictionaryResponseItem) : SelectDetectionViewState()
    data class BookmarkState(val isBookmark: Boolean) : SelectDetectionViewState()
    data class ShowToast(val message: String) : SelectDetectionViewState()

    object NotSearchWord : SelectDetectionViewState()

    object ShowProgress : SelectDetectionViewState()
    object HideProgress : SelectDetectionViewState()
}