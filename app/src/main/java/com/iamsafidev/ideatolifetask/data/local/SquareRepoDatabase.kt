package com.iamsafidev.ideatolifetask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SquareRepoEntity::class],
    version = 1
)
abstract class SquareRepoDatabase : RoomDatabase() {

    abstract val dao: SquareRepoDao
}