package com.example.tokenfragment

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tokenfragment.data.db.TestsDatabase
import com.example.tokenfragment.data.db.entities.Tests
import com.example.tokenfragment.data.repositories.TestRepository
import com.example.tokenfragment.databinding.FragmentTest1Binding
import com.example.tokenfragment.other.TestAdapter
import com.example.tokenfragment.ui.TestsViewModel


//testi gönderemiyoruz başka bir şey yapmalıyız, interface'e parametre koyarsak gönderebiliriz

class Test1() : Fragment() {

    private lateinit var mTestsViewModel: TestsViewModel
    private var _binding: FragmentTest1Binding? = null
    private val binding get() = _binding!!

    companion object{
        var failCount: Int = 0
        var successCount: Int = 0
        var lastResult: Boolean = false
        //var test = Tests(1,"Test1", successCount, failCount, false,"")    //0 0 olunca eklemiyomuş
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_test1, container, false)
        _binding = FragmentTest1Binding.inflate(inflater,container,false)   //bu null olabilir
        return binding.root     //ama bu olamaz

    }
    //içerde interface tanımlarsan parametreyle uğraşmazsın?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTestsViewModel = ViewModelProvider(this).get(TestsViewModel::class.java)   //viewModeli tanımladık

        binding.btnSubmitTest1.setOnClickListener {

            var result =""
            val currTest = mTestsViewModel.getTestById(1)
            if (binding.rbSuccessTest1.isChecked ){ //if Pass the test
                if (currTest == null){
                    successCount++
                }
                else{   //databaseteki değerleri çek
                    successCount = currTest.successCount + 1
                    failCount = currTest.failCount
                }
                lastResult = true
                result = "Success"
            }
            else{   //if fail the test
                if (currTest == null){  //eğer boşsa currTest default 0'ı atıyor countlara
                    failCount++
                }
                else{   //databaseteki değerleri çek
                    failCount = currTest.failCount + 1
                    successCount = currTest.successCount
                }
                lastResult = false
                result = "Fail"
            }
            /*
            Log.d("testID before","${test.id}")
            if (successCount + failCount <= 1){
                test = Tests(1,"Test1", successCount, failCount, lastResult,"") //companiondakini değiştirsin tıklayınca
                insertDatatoDatabase(test)
                Log.d("Test1", "Insert successfully")
            }
            else{
                test = Tests(1,"Test1", successCount, failCount, lastResult,"")
                mTestsViewModel.update(test)
                Log.d("Test1", "Update successfully")
            }

             */
            var test = Tests(1,"Test1", successCount, failCount, lastResult,"")
            mTestsViewModel.upsert(test)

            Log.d("testID after","${test.id}")
            Log.d("test","${test.toString()}")
            /*else{
                mTestsViewModel.upsert(test)
                Log.d("Test1", "Update successfully")
            }
             */

            Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_SHORT).show()
            val tag = "Test1"   //fragment adı getirebilirsem çok iyi ama bulamadım
            val str = "You are $result on $tag"
            Log.d(tag, str)
        }
    }

     private fun insertDatatoDatabase(test: Tests) { //suspend yaptım
        mTestsViewModel.insert(test)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null //bidaha null yapsın destroyladığında memory leak olmasın
    }

}