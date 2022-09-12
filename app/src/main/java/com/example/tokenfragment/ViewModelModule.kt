package com.example.tokenfragment

import android.app.Application
import com.example.datalib.data.repositories.TestRepository
import com.example.tokenfragment.ui.TestsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/** This is a module object for Dependency Injection with Hilt
 * It defines the viewModel inside that object
 * Because ViewModel's life is different than App's Life in lifecycle we have to define it another module
 * @InstallIn(ViewModelComponent::class) means it's life as much as ViewModel's life
 */
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    /**
     * It defines our viewModel, app and repository are gotten from Hilt DI
     */
    @Provides
    @ViewModelScoped
    fun provideViewModel(app: Application,repository: TestRepository): TestsViewModel =
        TestsViewModel(app,repository)

}