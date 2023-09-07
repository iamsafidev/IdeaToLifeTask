package com.iamsafidev.ideatolifetask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.iamsafidev.ideatolifetask.data.mappers.toSquareRepo
import com.iamsafidev.ideatolifetask.domain.SquareRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SquareRepoViewModel @Inject constructor(
    pager: Pager<Int, SquareRepoEntity>,
    val state: GlobalState
) : ViewModel() {

    val sharedState = state.sharedState

    val squareRepoPagingFlow = pager
        .flow.map { pagingData ->
            pagingData
                .map { it.toSquareRepo() }
        }.cachedIn(viewModelScope)

    fun setSelectionState(squareRepo: SquareRepo) {
        state.setSquareRepoDetails(squareRepo = squareRepo)
    }
}