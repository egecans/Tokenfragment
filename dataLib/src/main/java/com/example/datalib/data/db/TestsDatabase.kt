package com.example.datalib.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.datalib.data.db.entities.Tests

/** @author egecans
 * This class is an abstract database class, it's basically getting the Data Access Object and create
 * a single database instance. First we annotate this is a database with @Database
 * and tell it's entity classes by entities parameter, and version number to update it whenever we change
 * something in database
 * It's inherited from RoomDatabase() class
 */
@Database(
    entities = [Tests::class],
    version = 3,
)
abstract class TestsDatabase: RoomDatabase() {

    /**
     * it's only get Data Access Object, it's needed for lately to access the methods with using data
     */
    abstract fun getTestsDao(): TestsDao

    /**
     * Everything in companion object (values, functions...) is single, like static in java
     */
    companion object{

        /**
         * It's database instance, it's declared as nullable because it's null before it's created
         * Volatile annotation create it ones, not everytime it's called.
         */
        @Volatile
        private var INSTANCE: TestsDatabase? = null


        /**
         * This function is for creating database if it hasn't been created before, and returns the database
         */
        fun getDatabaseInstance(context: Context): TestsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance     //if instance already exists return that instance
            }
            synchronized(this){ //if not than sychronizely create and return that instance
                val instance = Room.databaseBuilder(
                    context.applicationContext, //application context
                    TestsDatabase::class.java,  //class of database
                    "test_database" //name of database
                ).allowMainThreadQueries()  //to throw some exceptions
                    .fallbackToDestructiveMigration()   //if you update the version (to change something in database)
                        //then it needs to destroy the database before, becasue of that we use that method
                    //.createFromAsset("database/test_db.db")
                    .build()    //to build it
                INSTANCE = instance
                return instance
            }
        }

    }

}