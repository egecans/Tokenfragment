package com.example.tokenfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.datalib.data.db.entities.Tests
import com.example.tokenfragment.databinding.FragmentTest2Binding
import com.example.tokenfragment.ui.TestsViewModel
import javax.inject.Inject

/** This class is almost same as Test1 which is another fragment class of Tests,
 *  the only difference here is, it contains an info and it is define
 *  testView in constructor with Hilt for practicing both dependency.
 *  You can look Test1 if you wonder what those operations are for
 */
class Test2 @Inject constructor(private val mTestsViewModel: TestsViewModel) : Fragment() {

    private var _binding: FragmentTest2Binding? = null
    private val binding get() = _binding!!

    companion object{
        var failCount: Int = 0
        var successCount: Int = 0
        var lastResult: Boolean = false
        var info: String = ""
        var test = Tests(2,"Test2", successCount, failCount, false, info)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTest2Binding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmitTest2.setOnClickListener {
            val currTest = mTestsViewModel.getTestById(2)
            if (binding.rbSuccessTest2.isChecked ){
                if (currTest == null){
                    successCount++
                }
                else{   //databaseteki değerleri çek
                    successCount = currTest.successCount + 1
                    failCount = currTest.failCount
                }
                lastResult = true
                info = binding.etInfo.text.toString()
            }
            else{
                if (currTest == null){
                    failCount++
                }
                else{   //databaseteki değerleri çek
                    successCount = currTest.successCount
                    failCount = currTest.failCount + 1
                }
                lastResult = false
                info = binding.etInfo.text.toString()
            }

            test = Tests(2,"Test2", successCount, failCount, lastResult, info)
            mTestsViewModel.upsert(test)
            parentFragmentManager.popBackStack()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}