package com.example.datalib.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.datalib.data.db.TestsDatabase
import com.example.datalib.data.db.entities.Tests
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Named

/** @author egecans
 * Actually this class isn't needed it is for access to multiple data sources but we have only 1
 * but i create that because it's a good practice in MVVM architecture
 * @param database is database class, it's coming from Dependency Injection thanks to Inject annotation,
 * I define it in AppModule
 */

class TestRepository @Inject constructor (
    private val database: TestsDatabase
) {

    /**
     * It gets the Data Access Object and make upsert operation there with parameter test which is our data class
     */
    suspend fun upsert(test: Tests){
        database.getTestsDao().upsert(test)
    }

    /**
     * It gets all the Tests as LiveData List
     */
    fun getAllTests(): LiveData<List<Tests>> {
        val result = database.getTestsDao().getAllTests()
        return result
    }

    /**
     * It gets the specific test by its id
     */
    fun getTestById(id: Int): Tests {
        val result = database.getTestsDao().getTestById(id)
        return result
    }

}