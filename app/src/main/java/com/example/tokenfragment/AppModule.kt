package com.example.tokenfragment

import android.app.Application
import com.example.datalib.data.db.TestsDatabase
import com.example.datalib.data.repositories.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** This is a Module for Hilt, Hilt has some modules to define some values. If you define those values there, you
 * don't need to define them everytime to call those. The only thing to do is @Inject method before you call those values
 * If you Inject them in constructor you need to @Inject constructor, else if in class first you should
 * annotate that class with @AndroidEntryPoint then you specify its class and add @Inject annotation to head of that.
 * Because there are different lifecycles in Android, in @InstallIn parameter you specify
 * lifecycle of values that those class defines
 * This is SingletonComponent because those values should live as application does
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** It returns a TestDatabase instance
     * @Provide is for providing dependency which class it returns.
     * To do that without errors, You should specify only one provide method for each class
     * otherwise you should named them and call them with their names.
     * @Singleton is for making it single instance of this. If it doesn't exist, everytime we call this it creates new instance
     * @param app is Application, as I mentioned in TestApplication class, it comes from there and returns ActivityContext
     */
    @Provides
    @Singleton
    fun provideDatabase(app: Application): TestsDatabase = TestsDatabase.getDatabaseInstance(app)
    //app TestApplicationda @HiltAndroidApp dediğimiz için oradan geldi Application

    /**
     * It returns Repository instance
     * @param database is comes from provideDatabase method automatically
     */
    @Provides
    @Singleton
    fun provideRepository(database: TestsDatabase): TestRepository = TestRepository(database)


}