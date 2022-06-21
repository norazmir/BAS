package com.norazmir.bas.data.student

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object{

        private const val DATABASE_NAME = "bas.db"
        private const val table_student = "student_table"
        private const val ID = "ID"
        private const val studentID = "studentID"
        private const val studentName = "studentName"
        private const val studentAddress = "studentAddress"
        private const val studentSchool = "studentSchool"
        private const val guardianName = "guardianName"
        private const val busNumber = "busNumber"
        private const val signIn = "signIn"
        private const val signOut = "signOut"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableStudent = ("create table " + table_student + "("
                + ID + "INTEGER PRIMARY KEY," + studentID + "TEXT," +
                studentName + "TEXT," + studentAddress + "TEXT," + studentSchool +
                "TEXT," + guardianName + "TEXT," + busNumber + "TEXT," + signIn +
                "TEXT," + signOut + "TEXT" + ")")
        p0?.execSQL(createTableStudent)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $table_student")
        onCreate(p0)
    }


    @SuppressLint("Range")
    fun getStudentDetails(getStudID:String): Student {
        val studentDetails = Student()
        val selectQuery = "select * from $table_student where $studentID = $getStudID"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        studentDetails.studentID = cursor.getString(cursor.getColumnIndex(studentID))
        studentDetails.studentName = cursor.getString(cursor.getColumnIndex(studentName))
        studentDetails.studentAddress = cursor.getString(cursor.getColumnIndex(studentAddress))
        studentDetails.studentSchool = cursor.getString(cursor.getColumnIndex(studentSchool))
        studentDetails.guardianName = cursor.getString(cursor.getColumnIndex(guardianName))
        studentDetails.busNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(busNumber)))
        studentDetails.signIn = (cursor.getString(cursor.getColumnIndex(signIn))).toLong()
        studentDetails.signOut = (cursor.getString(cursor.getColumnIndex(signOut))).toLong()
        cursor.close()
        return studentDetails
    }

}