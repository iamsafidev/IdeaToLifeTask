package com.iamsafidev.ideatolifetask.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.iamsafidev.ideatolifetask.data.local.SquareRepoDatabase
import com.iamsafidev.ideatolifetask.data.local.SquareRepoEntity
import com.iamsafidev.ideatolifetask.data.remote.SquareRepoApi
import com.iamsafidev.ideatolifetask.data.remote.SquareRepoMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): SquareRepoDatabase {
        return Room
            .databaseBuilder(
                context,
                SquareRepoDatabase::class.java,
                "square_repos.db"
            ).build()
    }

    @Provides
    @Singleton
    fun provideSquareRepoApi(): SquareRepoApi {
        return Retrofit.Builder()
            .baseUrl(SquareRepoApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSquareRepoPager(
        squareRepoDb: SquareRepoDatabase,
        squareRepoApi: SquareRepoApi
    ): Pager<Int, SquareRepoEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = SquareRepoMediator(
                squareRepoDatabase = squareRepoDb,
                squareRepoApi = squareRepoApi
            ),
            pagingSourceFactory = {
              squareRepoDb.dao.pagingSource()
            }
        )
    }

}