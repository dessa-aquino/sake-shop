package com.example.sakeshop.presentation.viewmodel


import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.sakeshop.domain.model.SakeStore
import com.example.sakeshop.domain.repository.SakeStoreRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlin.test.assertTrue
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SakeStoreViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var mockRepository: SakeStoreRepository
    private lateinit var viewModel: SakeStoreViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialState should be loading before any transition`() = runTest(testDispatcher) {

        coEvery { mockRepository.getSakeStores() } coAnswers {
            kotlinx.coroutines.delay(100)
            Result.success(emptyList())
        }

        viewModel = SakeStoreViewModel(mockRepository)
        assertTrue(
            viewModel.uiState.value is SakeStoreUiState.Loading,
            "ViewModel UI state should be Loading initially"
        )

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is SakeStoreUiState.Success ||
                viewModel.uiState.value is SakeStoreUiState.Error)
    }

    @Test
    fun `loadSakeStores returns Success when stores are available`() = runTest(testDispatcher) {
        val testStores = listOf(
            SakeStore(
                name = "Sake Paradise",
                description = "Melhor loja de sake da região",
                picture = "https://example.com/sake-paradise.jpg",
                rating = 4.5,
                address = "Rua Principal, 123",
                coordinates = listOf(-23.5505, -46.6333),
                googleMapsLink = "https://maps.google.com/sake-paradise",
                website = "https://sakeparadise.com"
            ),
            SakeStore(
                name = "Nihon Sake",
                description = "Especialistas em sake tradicional",
                picture = "https://example.com/nihon-sake.jpg",
                rating = 4.2,
                address = "Avenida Secundária, 456",
                coordinates = listOf(-23.5600, -46.6400),
                googleMapsLink = "https://maps.google.com/nihon-sake",
                website = "https://nihonsake.com"
            )
        )
        coEvery { mockRepository.getSakeStores() } returns Result.success(testStores)

        viewModel = SakeStoreViewModel(mockRepository)


        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState is SakeStoreUiState.Success, "UI State should be Success. Was: $uiState")
        assertEquals(testStores, uiState.stores)
    }

    @Test
    fun `loadSakeStores returns Error when repository fails`() = runTest(testDispatcher) {
        val errorMessage = "Network error"
        coEvery { mockRepository.getSakeStores() } returns Result.failure(Exception(errorMessage))

        viewModel = SakeStoreViewModel(mockRepository)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState is SakeStoreUiState.Error, "UI State should be Error. Was: $uiState")
        assertEquals(errorMessage, uiState.message)
    }

    @Test
    fun `loadSakeStores returns Error when there's no store with the correct info`() = runTest {
        coEvery { mockRepository.getSakeStores() } returns Result.failure(
            IllegalArgumentException("Store description cannot be empty")
        )

        viewModel = SakeStoreViewModel(mockRepository)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is SakeStoreUiState.Error)
    }

    @Test
    fun `loadStoreDetails returns selected store info when store is available`() = runTest(testDispatcher) {
        val testStores = listOf(
            SakeStore(
                name = "Sake Paradise",
                description = "Melhor loja de sake da região",
                picture = "https://example.com/sake-paradise.jpg",
                rating = 4.5,
                address = "Rua Principal, 123",
                coordinates = listOf(-23.5505, -46.6333),
                googleMapsLink = "https://maps.google.com/sake-paradise",
                website = "https://sakeparadise.com"
            ),
            SakeStore(
                name = "Nihon Sake",
                description = "Especialistas em sake tradicional",
                picture = "https://example.com/nihon-sake.jpg",
                rating = 4.2,
                address = "Avenida Secundária, 456",
                coordinates = listOf(-23.5600, -46.6400),
                googleMapsLink = "https://maps.google.com/nihon-sake",
                website = "https://nihonsake.com"
            )
        )
        coEvery { mockRepository.getSakeStores() } returns Result.success(testStores)

        viewModel = SakeStoreViewModel(mockRepository)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is SakeStoreUiState.Success, "Stores should be loaded successfully first.")

        val targetStoreName = "Nihon Sake"
        viewModel.loadStoreDetails(targetStoreName)

        val selectedStore = viewModel.selectedStore.value
        assertEquals(targetStoreName, selectedStore?.name, "The correct store was selected.")
    }
}