package com.frommetoyou.ualachallenge.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.frommetoyou.domain.model.City

class FakePagingSource : PagingSource<Int, City>() {
    override fun getRefreshKey(state: PagingState<Int, City>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return LoadResult.Page(
            data = listOf(City.getDefaultCity()),
            prevKey = null,
            nextKey = null
        )
    }
}
