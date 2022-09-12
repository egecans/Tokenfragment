package com.example.datalib.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.datalib.data.db.entities.Tests
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


//viewModel için parametreleri girmemek için fakeRepository kuruyorsun normal testte
//Repositoryn bir interface'ten inherit oluyor, fakeRepo da aynı yerden inherit
//viewModelse repository interfaceten testteki de fakerepositoryden inherit
//fake repositoryde MutableList falan aynı şekilde tanımlı insert yerine liste add var aynı functionality diye
//ve bu sayede androidtestte değil JVM de çok daha hızlı çalışıyor UI la ilgisi yok çünkü
//viewModeli bu şekilde yapıyon ama ben structure'ı değiştirmek istemediğim için repositoryde uğraşmadım buna


//java ya da kotlin kodda çalışıyor JUnit genelde ama biz android env deyiz JVM de değiliz o yüzden emin olmak istedik
@RunWith(AndroidJUnit4::class) //JUnit 4la çalıştığına Hiltle olduğundan buna gerek kalmadı dependencye de ekledik
@SmallTest  //Yazdığımız şeylerin UnitTest olduğunu belirtiyor, Integrated için Medium, UI için LargeTest
//@HiltAndroidTest    //android entry pointin test versiyonu
class TestsDaoTest {

    //@get:Rule
    //var hiltRule = HiltAndroidRule(this)

    @get:Rule   //livedata problem çıkarıyordu asynchron olduğundan, bu yüzden tüm kodu burada execute edeceğimizi bildiriyoruz
    //aynı thread içinde yoksa job hasn't been completed yet erroru yiyoruz
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    //@Inject
    //@Named("tester_db") //hangisini yapacağını bilmiyor 2 db olduğundan
    lateinit var database: TestsDatabase    //we cannot inject private vals so delete
    private lateinit var dao: TestsDao

    @Before //this function is implemented before the whole test functions runs.
    fun setup(){
        //hiltRule.inject()
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun upsertTest() = runTest {//Coroutine diye böyle, delay varsa onu aşıyor mainde çalıştırıyor
        val currTest = Tests(3,"tag",0,0,false,"")
        dao.upsert(currTest)


        //val getTest = dao.getTestById(3)
        //assertThat(getTest==currTest)//de yapabilirdin
        //LiveData değil Liste dönsün diye bu methodu ekledik
        val allTests = dao.getAllTests().getOrAwaitValue()
        assertThat(allTests).contains(currTest)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTestById() = runTest {
        val currTest = Tests(3, "tag", 0, 0, false, "")
        dao.upsert(currTest)

        val getTest = dao.getTestById(3)
        assertThat(getTest == currTest) //insert ekliyorsa bu da olmalı
    }


}