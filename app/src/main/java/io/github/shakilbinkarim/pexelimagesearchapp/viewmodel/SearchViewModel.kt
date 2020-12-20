package io.github.shakilbinkarim.pexelimagesearchapp.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import io.github.shakilbinkarim.pexelimagesearchapp.repository.PexelRepository

class SearchViewModel @ViewModelInject constructor(
    private val repository: PexelRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "tokyo"
        private const val CURRENT_QUERY = "current_query"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

}