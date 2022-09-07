package com.example.datalib.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.datalib.data.db.TestsDatabase
import com.example.datalib.data.db.entities.Tests

//abstracts access to multiple data sources, don't have to do that but it's a good practice
class TestRepository(
    private val database: TestsDatabase //buraya daoyu da getirebilirsin
) {


    suspend fun upsert(test: Tests){
        database.getTestsDao().upsert(test)
    }


    suspend fun insert(test: Tests){
        database.getTestsDao().insert(test)
        //getAllTests()
        Log.d("repo","insert")
    }

    suspend fun update(test: Tests){
        database.getTestsDao().update(test)
        //getAllTests()
        Log.d("repo","update")
    }

    fun getAllTests(): LiveData<List<Tests>> {
        val result = database.getTestsDao().getAllTests()
        Log.d("repo","get all test: ${result.toString()}")
        return result
    }

    fun getTestById(id: Int): Tests {
        val result = database.getTestsDao().getTestById(id)
        Log.d("repo","get test by id: ${result.toString()}")
        return result
    }

}