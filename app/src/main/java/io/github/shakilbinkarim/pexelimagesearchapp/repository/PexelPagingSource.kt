package io.github.shakilbinkarim.pexelimagesearchapp.repository

import androidx.paging.PagingSource
import io.github.shakilbinkarim.pexelimagesearchapp.model.PexelPhoto
import retrofit2.HttpException
import java.io.IOException

private const val PEXEL_STARTING_PAGE_INDEX = 1

class PexelPagingSource (
    private val pexelApi: PexelApi,
    private val query: String
) : PagingSource<Int, PexelPhoto>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PexelPhoto> {
        val position = params.key ?: PEXEL_STARTING_PAGE_INDEX
        return try{
            val response = pexelApi.searchPhotos(query = query, perPage = params.loadSize, page=position)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (position == PEXEL_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch(e: HttpException){
            LoadResult.Error(e)
        }
    }

}