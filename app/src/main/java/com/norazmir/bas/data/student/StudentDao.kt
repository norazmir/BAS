package com.norazmir.bas.data.student

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.selects.select

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Query("select * from student where studentID = :searchStudID" )
    fun getStudentDetails(searchStudID: String)
}