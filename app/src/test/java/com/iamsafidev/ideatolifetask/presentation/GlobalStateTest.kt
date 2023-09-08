package com.iamsafidev.ideatolifetask.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.iamsafidev.ideatolifetask.domain.SquareRepo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GlobalStateTest {

    lateinit var globalState: GlobalState

    @BeforeEach
    fun setUp() {
        globalState = GlobalState()
    }

    @Test
    fun `Test Shared State of selected Square Repo`() = runTest {
          globalState.sharedState.test {
              val emission1 = awaitItem()
              assertThat(emission1).isNull()

              globalState.setSquareRepoDetails(SquareRepo(id=1,name="iamsafidev", stargazersCount = 1500))

              val emission2 = awaitItem()
              assertThat(emission2?.name).isEqualTo("iamsafidev")
          }
    }
    
}