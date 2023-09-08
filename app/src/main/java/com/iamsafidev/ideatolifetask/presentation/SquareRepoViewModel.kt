package com.iamsafidev.ideatolifetask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.iamsafidev.ideatolifetask.data.mappers.toSquareRepo
import com.iamsafidev.ideatolifetask.domain.SquareRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SquareRepoViewModel @Inject constructor(
    private val pager: Pager<Int, SquareRepoEntity>,
    private val state: GlobalState
) : ViewModel() {

    val sharedState = state.sharedState

    fun getSquareReposPagingFlow(): Flow<PagingData<SquareRepo>> {
        return pager
            .flow.map { pagingData ->
                pagingData
                    .map { it.toSquareRepo() }
            }.cachedIn(viewModelScope)
    }

    fun setSelectionState(squareRepo: SquareRepo) {
        state.setSquareRepoDetails(squareRepo = squareRepo)
    }
}