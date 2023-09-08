package com.iamsafidev.ideatolifetask.presentation


import androidx.paging.Pager
import androidx.paging.PagingData
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.iamsafidev.ideatolifetask.domain.SquareRepo
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SquareRepoViewModelTest {

    private lateinit var squareRepoViewModel: SquareRepoViewModel
    private lateinit var state: GlobalState
    private lateinit var pager: Pager<Int, SquareRepoEntity>

    @BeforeEach
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        state = GlobalState()
        pager = mockk()
        squareRepoViewModel = SquareRepoViewModel(pager = pager, state = state)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getSharedState() = runTest{
        squareRepoViewModel.sharedState.test {
            val emission1 = awaitItem()
            assertThat(emission1).isNull()

            squareRepoViewModel.setSelectionState(SquareRepo(1,"Test",200))
            val emission2 = awaitItem()
            assertThat(emission2?.id).isEqualTo(1)
        }
    }

    @Test
    fun getSquareRepoPagingFlow() = runTest(UnconfinedTestDispatcher()) {
        val squareRepo = listOf(
            SquareRepo(1,"",20),
            SquareRepo(2,"",20)
        )
          
        val flow = flow {
            emit(PagingData.from(squareRepo))
        }

        val squareRepoEntities = listOf(
            SquareRepoEntity(1,"",20),
            SquareRepoEntity(2,"",20)
        )
        coEvery { pager.flow } returns flowOf(PagingData.from(squareRepoEntities))
        every { squareRepoViewModel.getSquareReposPagingFlow() } returns flow
        val result = squareRepoViewModel.getSquareReposPagingFlow().toList()

        advanceUntilIdle()

        assertThat(squareRepoEntities.size).isEqualTo(result.size)
    }

}

