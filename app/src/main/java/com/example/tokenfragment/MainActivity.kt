package com.example.tokenfragment

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tokenfragment.databinding.ActivityMainBinding
import com.example.tokenfragment.ui.TestsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is the Main Activity Class, which runs our app.
 * We need to call methods those run in here.
 * It's @AndroidEntryPoint because, we get ViewModel inside of class,
 * Even if we wouldn't get the ViewModel here, we have to annotate it with @AndroidEntryPoint
 * because we get ViewModel in Fragments which we call there.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() { //KodeinAware


    private fun isExternalStorageWritable(): Boolean{
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.d("Main/Writable","yes is writable")
            return true
        }
        Log.d("Main/Writable","no isn't writable")
        return false
    }

    //path de yazılıyomuş external yerde
    private fun isExternalStorageReadable(): Boolean{
        if(isExternalStorageWritable() || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            Log.d("Main/Readable","yes is readable")
            return true
        }
        Log.d("Main/Readable","no isn't readable")
        return false
    }


    /**
     * Methods for running app are calling there. Views are displayed in activity_main layout
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        //instead //setContentView(R.layout.activity_main) I do that with binding
        setContentView(binding.root)

        isExternalStorageReadable()
        isExternalStorageReadable()


        val currentDBPath = getDatabasePath("test_database")
        Log.d("Main/DBPath", "db path is $currentDBPath")

        //define viewModel by Hilt
        val mViewModel: TestsViewModel by viewModels()

        //initialize instances of those classes here
        val emptyFragment = EmptyFragment()
        val test1Fragment = Test1()
        val test2Fragment = Test2(mViewModel)
        val fragmentList = FragmentList(mViewModel)

        /**
         * This is for recyclerView, it begins fragment transaction here
         * and replace the FrameLayout with fragmentList.
         */
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.rvTests,fragmentList)
            commit()
        }

        /** This is for if user click Test1 button, if s/he does then it starts the Test1 Fragment and
         * add those to the BackStack if we don't add to the backStack then when we clicked back button at the bottom
         * we close the app because stack was empty.
         * instead we use binding again findViewById<Button>(R.id.btnTest1)
         */
        //
        binding.btnTest1.setOnClickListener {
            //starts the display fragment, apply is for minimizing the code if we don't have apply block
            //then we always says supportFragmentManager.beginTransaction at the beginning of the operations inside of that block
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,test1Fragment)
                addToBackStack(null)    //now it adds it to the fragment stack, otherwise just start a new fragment
                commit()
            }
            //supportFragmentManager.popBackStack() buton ekleyip yap
        }
        /**
         * This is for clicking Test2.
         */
        binding.btnTest2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,test2Fragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}