package com.example.datalib.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author egecans
 * This is a Data class that hold datas, first to create table in database, we annotate a table name
 * @param id is for distinguish different tests, by using this we find out which one is Test1, which one is Test2
 * @param tag is name of the Test
 * @param successCount is a count of successfully results of corresponding test
 * @param failCount is a count of failed results of corresponding test
 * @param successLast is a boolean that holds the last result of test, if it is success returns true else false
 * @param info some tests (i.e Test2) have additional info, that parameter is for keeping that info
 */
@Entity(tableName = "tests")
data class Tests(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "test_id")
    public var id: Int,
    @ColumnInfo(name = "test_name")
    var tag: String,
    @ColumnInfo(name = "test_success_count")
    var successCount: Int,
    @ColumnInfo(name = "test_fail_count")
    var failCount: Int,
    @ColumnInfo(name = "test_success_last")
    var successLast: Boolean,
    @ColumnInfo(name = "test_info") //bos kalabilir mi nasıl bakarız
    var info: String    //bir şey girilmezse null say manasında
)