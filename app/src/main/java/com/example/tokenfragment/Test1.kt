package com.example.tokenfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.datalib.data.db.entities.Tests
import com.example.tokenfragment.databinding.FragmentTest1Binding
import com.example.tokenfragment.ui.TestsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/** This class is for fragment Test1 which doesn't include any info
 * It basically assign its data first (when the Test1 is null) and then gets those data from database and operate
 * them corresponding button user has clicked.
 * It has @AndroidEntryPoint because it's getting viewModel by Dependency Injection
 */
@AndroidEntryPoint
class Test1() : Fragment() {

    /** Here gets viewModel by Hilt DI and then define binding as nullable and
     * get its nullable binding instance as nonnull one.
     */
    private val mTestsViewModel: TestsViewModel by viewModels()
    private var _binding: FragmentTest1Binding? = null
    private val binding get() = _binding!!


    /**
     * In here it holds initial data for creating the Tests entity, at first
     * its counts are 0. Then they are increasing by corresponding button user has clicked
     */
    companion object{
        var failCount: Int = 0
        var successCount: Int = 0
        var lastResult: Boolean = false
    }

    /**
     * Here define view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTest1Binding.inflate(inflater,container,false)
        return binding.root     //ama bu olamaz

    }

    /**
     * Those are operations after view has been created.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //this is the operations after user clicked submit button
        binding.btnSubmitTest1.setOnClickListener {
            var result =""
            val currTest = mTestsViewModel.getTestById(1) //it gets the Test whose id is 1
            if (binding.rbSuccessTest1.isChecked ){ //if user clicked the success button
                if (currTest == null){  //if Test whose id is 1 is null which means hasn't created yet
                    successCount++  //then it increases successCount as 1 (0 + 1 = 1)
                }
                else{   //if it is created before then it gets its results and define them.
                    successCount = currTest.successCount + 1
                    failCount = currTest.failCount
                }
                lastResult = true
            }
            else{   //if fail the test
                if (currTest == null){
                    failCount++
                }
                else{
                    failCount = currTest.failCount + 1
                    successCount = currTest.successCount
                }
                lastResult = false
            }
            // define the test with corresponding values
            var test = Tests(1,"Test1", successCount, failCount, lastResult,"")
            //then upsert it (if it hasn't been created yet, insert else, update)
            mTestsViewModel.upsert(test)

        }
    }

    /**
     * This is for avoiding memory leak for binding
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null //bidaha null yapsın destroyladığında memory leak olmasın
    }

}