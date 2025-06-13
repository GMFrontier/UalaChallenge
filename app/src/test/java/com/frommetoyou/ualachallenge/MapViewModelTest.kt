package com.frommetoyou.ualachallenge

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.core.util.Result
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.repository.LocalRepository
import com.frommetoyou.domain.repository.MapRepository
import com.frommetoyou.domain.use_case.MapUseCase
import com.frommetoyou.presentation.MapViewModel
import com.frommetoyou.presentation.UiState
import com.frommetoyou.ualachallenge.utils.FakePagingSource
import com.frommetoyou.ualachallenge.utils.MainCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class MapViewModelTest {

    private lateinit var viewModel: MapViewModel
    private lateinit var useCase: MapUseCase

    private val localRepository: LocalRepository = mockk()
    private val remoteRepository: MapRepository = mockk()

    lateinit var baCity: City

    companion object {
        @OptIn(ExperimentalCoroutinesApi::class)
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {
        useCase = MapUseCase(remoteRepository, localRepository)
        baCity = City.getDefaultCity()

        viewModel = MapViewModel(
            mapUseCase = useCase,
            dispatcherProvider = CoroutinesDispatcherProvider()
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `viewModel's city loading show correct states`() =
        runTest {
            coEvery { localRepository.getCities() } returns FakePagingSource()
            coEvery { localRepository.countCities("", false) } returns 1
            coEvery { remoteRepository.getCities() } returns flowOf(Result.Success(listOf()))

            viewModel.getCities()

            viewModel.uiState.test {

                val emission1 = awaitItem()
                assertThat(emission1).isEqualTo(UiState.Nothing)
                val emission2 = awaitItem()
                assertThat(emission2).isEqualTo(UiState.Loading)
                val emission3 = awaitItem()
                assertThat(emission3).isEqualTo(
                    UiState.Nothing
                )
                cancelAndConsumeRemainingEvents()
            }
        }
}