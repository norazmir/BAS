package com.norazmir.bas.data.student

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/*@Entity(tableName = "student")
@Parcelize*/
class Student{
    var studentID: String = ""
    var studentName: String = ""
    var studentAddress:String = ""
    var studentSchool:String = ""
    var guardianName:String = ""
    var busNumber:Int = 0
    var signIn: Long = System.currentTimeMillis()
    var signOut: Long = System.currentTimeMillis()
}

//    @PrimaryKey(autoGenerate = true) val id: Int = 0
