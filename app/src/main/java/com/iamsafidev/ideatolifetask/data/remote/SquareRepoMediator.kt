package com.iamsafidev.ideatolifetask.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.iamsafidev.ideatolifetask.data.local.SquareRepoDatabase
import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.iamsafidev.ideatolifetask.data.mappers.toSquareRepoEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class SquareRepoMediator(
    private val squareRepoDatabase: SquareRepoDatabase,
    private val squareRepoApi: SquareRepoApi
) : RemoteMediator<Int, SquareRepoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SquareRepoEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val squareRepos = squareRepoApi.getRepositories(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            squareRepoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    squareRepoDatabase.dao.clearAll()
                }

                val squareRepoEntities = squareRepos.map { it.toSquareRepoEntity() }

                squareRepoDatabase.dao.upsertAll(squareRepos = squareRepoEntities)
            }
            
            MediatorResult.Success(endOfPaginationReached = squareRepos.isEmpty())
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}