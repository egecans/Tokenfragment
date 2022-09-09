package com.example.tokenfragment.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.datalib.data.db.TestsDatabase
import com.example.datalib.data.db.entities.Tests
import com.example.datalib.data.repositories.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


//Activity değil application context tutabilirsin
//provide data to UI and survive configuration changes. Like communication center btw Repo and UI

//repositoryi atadı buraya
//buraya repo atanınca factoryle çalışmalı böyle olmasın diye çağırdığın her yere DI atamalısın

/** This class is for provide data to UI and survive configuration changes.
 * It behaves like communication center between Repository and UI (User Interface)
 * We annotate with HiltViewModel to tell Hilt (Dependency Injection) to it's our viewModel
 * lately we call this viewModel without passing its parameter thanks to  hilt like in the repository here
 * We won't call repository while we call ViewModel class because we define our repository in AppModule with Hilt.
 */
@HiltViewModel
class TestsViewModel @Inject constructor(
    application: Application, private val repository: TestRepository ): AndroidViewModel(application){

    /**
     * this value is for getting all the tests as LiveData List in init block by calling getAllTests from repository
     * which access the database and then data access object and tell this method
     */
    val getAllTests: LiveData<List<Tests>>
    //private val repository: TestRepository

    init {
        getAllTests = repository.getAllTests()
    }

    /**
     * This is for getting test by id
     */
    fun getTestById(id: Int): Tests {
        return repository.getTestById(id)
    }

    /**
     * This is for update and inserting the data at the same time, we made this modification in Dao as you remembered
     * It's Coroutine function because of that we define it before as a suspend function.
     * It's working in viewModelScope and work in IO thread instead of Main thread
     * thanks to this our program is little bit faster than before.
     */
    fun upsert(tests: Tests){
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsert(tests)    //the arrow with sign at right is because it's a suspend function
        }
    }


}

