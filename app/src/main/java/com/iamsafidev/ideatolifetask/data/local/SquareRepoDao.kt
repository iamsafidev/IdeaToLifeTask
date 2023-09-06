package com.iamsafidev.ideatolifetask.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SquareRepoDao {

    @Upsert
    suspend fun upsertAll(squareRepos: List<SquareRepoEntity>)

    @Query("SELECT * FROM repositories")
    fun pagingSource(): PagingSource<Int, SquareRepoEntity>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()

}