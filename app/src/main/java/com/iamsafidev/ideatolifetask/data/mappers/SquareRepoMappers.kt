package com.iamsafidev.ideatolifetask.data.mappers

import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.iamsafidev.ideatolifetask.data.remote.SquareRepoDto
import com.iamsafidev.ideatolifetask.domain.SquareRepo

fun SquareRepoDto.toSquareRepoEntity(): SquareRepoEntity {
    return SquareRepoEntity(
        id = id.toInt(),
        name = name,
        stargazersCount = stargazers_count
    )
}

fun SquareRepoEntity.toSquareRepo(): SquareRepo {
    return SquareRepo(
        id = id,
        name = name,
        stargazersCount = stargazersCount
    )
}