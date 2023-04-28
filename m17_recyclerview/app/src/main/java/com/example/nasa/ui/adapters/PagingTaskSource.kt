package com.example.nasa.ui.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nasa.model.Photo
import com.example.nasa.network.ServiceApi
import retrofit2.HttpException

class PagingTaskSource(private val serviceApi: ServiceApi) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(10)
            val response = serviceApi.getPhotosCuriosityPaging(currentPage)
            return if (response.isSuccessful) {
                val data = checkNotNull(response.body()).photos
                val prevKey = if (currentPage == 1) null else currentPage - 1
                val nextKey = if (data.size < pageSize) null else currentPage + 1
                LoadResult.Page(data, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}