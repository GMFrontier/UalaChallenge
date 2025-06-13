package com.frommetoyou.ualachallenge.filter_screen.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.Coordinates

class FakePagingSource : PagingSource<Int, City>() {
    override fun getRefreshKey(state: PagingState<Int, City>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return LoadResult.Page(
            data = listOf(City("Buenos Aires", "Argentina", 1L, Coordinates(0.0, 0.0), false)),
            prevKey = null,
            nextKey = null
        )
    }
}
