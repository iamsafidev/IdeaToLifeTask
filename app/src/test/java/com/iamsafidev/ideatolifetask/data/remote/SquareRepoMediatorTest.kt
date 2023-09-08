package com.iamsafidev.ideatolifetask.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iamsafidev.ideatolifetask.data.local.SquareRepoDatabase
import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.squareup.moshi.Moshi
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@OptIn(ExperimentalPagingApi::class)
internal class SquareRepoMediatorTest {
    lateinit var squareRepoDatabase: SquareRepoDatabase
    private lateinit var mockWebServer: MockWebServer
    lateinit var squareRepoApi: SquareRepoApi
    lateinit var squareRepoMediator:SquareRepoMediator


    private val squareRepoDto = listOf(
        SquareRepoDto(id = 1, name = "Abc", stargazers_count = 12),
        SquareRepoDto(id = 2, name = "Def", stargazers_count = 2),
        SquareRepoDto(id = 3, name = "Ghi", stargazers_count = 10),

    )
    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        val moshi = Moshi.Builder().build()
        squareRepoApi = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()
        
        squareRepoDatabase = mockk()

        squareRepoMediator = SquareRepoMediator(squareRepoDatabase = squareRepoDatabase,squareRepoApi=squareRepoApi)
    }

    @Test
    fun `when users are given then users paging source returns success load result`() =
        runTest {
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(jsonBody)
            mockWebServer.enqueue(mockResponse)

            val expected = PagingSource
                .LoadResult
                .Page(
                    data = squareRepoDto,
                    prevKey = null,
                    nextKey = 20
                )

            val pagingState =PagingState<Int, SquareRepoEntity>(
                listOf(),
                null,
                PagingConfig(20),
                10
            )

            val actual = squareRepoMediator.load(LoadType.REFRESH,pagingState)

            // then
            assertThat(expected).isEqualTo(actual)
        }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}