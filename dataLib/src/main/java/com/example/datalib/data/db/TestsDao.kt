package com.example.datalib.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.datalib.data.db.entities.Tests

//burada methodların gerekliliklerini tanımlayacaksın, sonra üzerine yazacaksın
//Defining methods that access the database
/**
 * @author egecans
 * This interface is for defining methods that accessed by database
 * First we annotate that Interface with @Dao to say Room, it is a Data Access Object
 * Then in the interface, we annotate methods in SQL syntax and give them parameters to use lately.
 */
@Dao
interface TestsDao {

    /**
     * This method can both update and insert the data, because of that its name is upsert which is comes from update's up
     * & insert's sert. If an object hasn't been created before then it's insert it to the database, if it is already
     * created than update the data
     * It's a suspend function because it is needed for using with Coroutine which we will do it later in viewModel class
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(tests: Tests)

    /**
     * This method is get all the data as a LiveData List,
     */
    @Query("SELECT * FROM tests")
    fun getAllTests(): LiveData<List<Tests>>
    //degisecek burası o yüzden live data

    /**
     * This method is for selecting the item that has same id with given parameter
     * It's used for update the data lately.
     */
    @Query("SELECT * FROM tests WHERE test_id = :id")
    fun getTestById(id: Int): Tests



}