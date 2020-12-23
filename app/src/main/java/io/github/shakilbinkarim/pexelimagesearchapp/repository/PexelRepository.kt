package io.github.shakilbinkarim.pexelimagesearchapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PexelRepository @Inject constructor (private val pexelApi: PexelApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PexelPagingSource(pexelApi, query)
            }
        ).liveData

}