package com.frommetoyou.ualachallenge.filter_screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.City.Companion.getDefaultCity
import com.frommetoyou.domain.repository.LocalRepository
import com.frommetoyou.ualachallenge.AppAndroidTest
import com.frommetoyou.ualachallenge.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FilterScreenTest : AppAndroidTest() {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var localRepository: LocalRepository

    lateinit var baCity: City
    lateinit var lpCity: City

    @Before
    fun setup() {
        baCity = getDefaultCity()
        lpCity = baCity.copy(name = "La Plata", id = 2L)
        localRepository.saveCity(
            baCity,
        )
        localRepository.saveCity(
            lpCity
        )
    }

    @After
    fun tearDown() {
        localRepository.deleteCities()
    }

    @Test
    fun testFavoriteClickTogglesState_DisplayFavoritesShouldListOnlyFavorites() {
        composeRule.onNodeWithText("${lpCity.name}, ${lpCity.country}")
            .assertIsDisplayed()
        composeRule.onNodeWithTag("Favorite:${lpCity.id}").performClick()
        composeRule.onNodeWithTag("Show only favorites").performClick()
        composeRule.onNodeWithText("${lpCity.name}, ${lpCity.country}")
            .assertIsDisplayed()
    }

    @Test
    fun testThatOnCityDetailClickOpensCityDetailScreen() {
        composeRule.onNodeWithText("${baCity.name}, ${baCity.country}")
            .assertIsDisplayed()
        composeRule.onNodeWithTag("Info${baCity.id}").performClick()
        composeRule.onNodeWithText("${baCity.name}, ${baCity.country}")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Id: ${baCity.id}").assertIsDisplayed()
    }
}