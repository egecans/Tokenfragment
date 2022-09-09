package com.example.tokenfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenfragment.databinding.FragmentListBinding
import com.example.tokenfragment.other.TestAdapter
import com.example.tokenfragment.ui.TestsViewModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

/** This class is for display data (Tests) that stores in LiveData List.
 * It is another fragment that contains recyclerview in layout file.
 * @param mTestViewModel a viewModel It comes from DI
 */
class FragmentList @Inject constructor(private val mTestViewModel: TestsViewModel) : Fragment() {
    //the lateinit keyword is used for those variables which are initialized after the declaration
    //private lateinit var mTestViewModel: TestsViewModel

    /**
     * It's for excepting some errors because of Null Pointer Exception,
     * You should have one nullable binding instance and one nonnull version of them
     * Whenever you need some changes in binding first you should change the nullable one then get this
     * to nonnull object
     */
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    /** Here is the function that applies while creating view
     * It returns view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //
        /** Instead val view = inflater.inflate(R.layout.fragment_list, container, false), I use binding
         * because it has some advantages. To use binding without errors first I equalize the null one to a binding
         * then get its nonnull version by calling binding and its root is the view.
         */
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val view = binding.root

        /**
         * In here I rearrange recyclerview which is in layout file. I defined its adapter and layoutManager
         */
        val adapter = TestAdapter()
        val recyclerView = binding.recyclerview  //fragment_listten aldım
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ViewModel
        //mTestViewModel = ViewModelProvider(this).get(TestsViewModel::class.java)
        //this olmadı bu oluyor lifecycleda
        /**
         * Here first we access the all tests, and its return value which is LiveData List of Tests
         * then observe that list which is basically warning the Adapter data has changed and give those data to list.
         */
        mTestViewModel.getAllTests.observe(viewLifecycleOwner, Observer { testList ->
            adapter.setData(testList)   //because you tell notifyDataSetChanged in setData you don't need to call it here too.
        })
        return view
    }

    /** This function is not necessary but it avoid you from memory leak while using binding
     * Whenever you destroy the fragment it equalize nullable binding to null value
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}