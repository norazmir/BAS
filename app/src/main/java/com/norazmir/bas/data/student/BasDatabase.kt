package com.norazmir.bas.data.student

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.norazmir.bas.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Student::class], version = 1)
abstract class BasDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    class Callback @Inject() constructor(
        private val database: Provider<BasDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operations
            val dao = database.get().studentDao()

            applicationScope.launch {
                dao.insertStudent(Student("2016595951","Ali Bin Abu","Taman Melawati, Selangor", "SK Taman Melawati", "Abu Bin Bakar",200))
            }
        }
    }
}