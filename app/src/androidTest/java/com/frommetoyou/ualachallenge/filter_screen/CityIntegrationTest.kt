package com.frommetoyou.ualachallenge.filter_screen

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.PagingSource
import assertk.assertThat
import assertk.assertions.isTrue
import com.frommetoyou.data.data_source.CityDao
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.City.Companion.getDefaultCity
import com.frommetoyou.presentation.MapViewModel
import com.frommetoyou.ualachallenge.AppAndroidTest
import com.frommetoyou.ualachallenge.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CityIntegrationTest : AppAndroidTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var dao: CityDao

    lateinit var baCity: City

    @Before
    fun setup() {
        baCity = getDefaultCity()
    }

    @After
    fun tearDown() {
        dao.deleteCities()
    }

    @Test
    fun testThatRoomSavesFavoriteCity_PagingReturnsSavedCity() = runTest {
        baCity = baCity.copy(isFavorite = false)
        dao.saveCity(baCity)
        val viewModel: MapViewModel = composeRule.activity.viewModels<MapViewModel>().value

        viewModel.toggleFavorite(baCity)

        dao.observeCities("").first { citiesList ->
            citiesList.any { it.id == baCity.id && it.isFavorite }
        }

        val pagingSource = dao.getCities("", false)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = Int.MAX_VALUE,
                placeholdersEnabled = false
            )
        )


        val cities = when (result) {
            is PagingSource.LoadResult.Page -> result.data
            else -> emptyList()
        }


        assertThat(cities.first { it.id == baCity.id }.isFavorite).isTrue()
    }
}