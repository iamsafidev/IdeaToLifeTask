package com.iamsafidev.ideatolifetask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class SquareRepoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val stargazersCount: Int
)
