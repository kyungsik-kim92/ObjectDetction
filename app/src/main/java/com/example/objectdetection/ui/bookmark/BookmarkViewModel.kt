package com.example.objectdetection.ui.bookmark

import androidx.lifecycle.viewModelScope
import com.example.objectdetection.base.BaseViewModel
import com.example.objectdetection.data.repo.FirebaseRepository
import com.example.objectdetection.ext.getWordList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    fun getBookmarkList() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.getWordList {list->
                if (!list.isNullOrEmpty()) {
                    onChangedViewState(BookmarkViewState.GetBookmarkList(list))
                } else {
                    onChangedViewState(BookmarkViewState.EmptyBookmarkList)
                }
            }
        }
    }
}