package com.example.datalib.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

//java ya da kotlin kodda çalışıyor JUnit genelde ama biz android env deyiz JVM de değiliz o yüzden emin olmak istedik
@RunWith(AndroidJUnit4::class) //JUnit 4la çalıştığına
@SmallTest  //Yazdığımız şeylerin UnitTest olduğunu belirtiyor, Integrated için Medium, UI için LargeTest
class TestsDaoTest {

    private lateinit var database: TestsDatabase
    private lateinit var dao: TestsDao

    @Before //this function is implemented before the whole test functions runs.
    fun setup(){
        database = Room.inMemoryDatabaseBuilder( //not real database, inMemory means it holds db on RAM it's only for testcase.
            ApplicationProvider.getApplicationContext(),
            TestsDatabase::class.java
        ).allowMainThreadQueries().build()
        //single threadde gitmesini isteriz testcaselerde, farklı threadler birbirini etkileyebilir
        dao = database.getTestsDao()
    }

    @After  // this function is called and runs after tests func run
    fun teardown(){
        database.close()    //temporarydi zaten, kapasın test bittikten sonra
    }


}