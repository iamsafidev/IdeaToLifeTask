package com.iamsafidev.ideatolifetask.presentation

import com.iamsafidev.ideatolifetask.domain.SquareRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalState @Inject constructor(){

    private val _sharedState = MutableStateFlow<SquareRepo?>(null)
    val sharedState = _sharedState.asStateFlow()

    fun setSquareRepoDetails(squareRepo: SquareRepo) {
        _sharedState.value = squareRepo
    }
}