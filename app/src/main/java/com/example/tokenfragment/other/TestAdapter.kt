package com.example.tokenfragment.other

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datalib.data.db.entities.Tests
import com.example.tokenfragment.databinding.TestBinding

/** This class is for implementing some methods to items on recycler view.
 * Tests is the item on recycler view in that project, because of that this class is implement methods on test.xml layout
 * which is a part of recyclerview in fragment_list.xml
 * It inherited from RecyclerView.Adapter, however because it needs ViewHolder,
 * we define TestViewHolder as an inner class.
 * Other methods are inherited from RecyclerView.Adapter you must override those to implement this inherit.
 * Hint: You can easily access those functions as press Ctrl + i
 */
class TestAdapter(): RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    /**
     * It's needed for inheriting
     */
    inner class TestViewHolder(val binding: TestBinding): RecyclerView.ViewHolder(binding.root)

    /**
     * This is the list that contains datas which is Tests here.
     */
    private var testList = emptyList<Tests>()

    /** This is a needed method for inheriting RecyclerView.Adapter
     * You create a view Holder here, I prefer to do that with Binding which is an extra property
     * because it has some advantages like null and type safety
     * comes from app build gradle. Instead of R.layout.<layout_name>, it generates layout_nameBinding for
     * every layout classes, you can access those layouts easily by calling that Binding name
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.test,parent,false)
        //return TestViewHolder(view)
        val binding = TestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TestViewHolder(binding)
    }

    /** This is a needed method for inheriting RecyclerView.Adapter
     * It basically tells what BindViewHolder does
     * @param holder is TestViewHolder
     * @param position is index of testList
     */
    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val currTest = testList[position]
        //instead holder.itemView.findViewById<TextView>(R.id.tvTag) I prefer binding again
        holder.binding.tvTag.text = currTest.tag
        holder.binding.tvInfo.text = currTest.info
        holder.binding.tvCountFail.text = "${currTest.failCount}"
        holder.binding.tvCountSuccess.text = currTest.successCount.toString()
        holder.binding.tvLastResult.text = if (currTest.successLast) "Success" else "Fail"
    }

    /** This is a last needed method for inheriting RecyclerView.Adapter
     * It's only return the Item Count which is equals to List's size
     */
    override fun getItemCount(): Int {
        return testList.size
    }

    /** This is an extra function to setData, the whole List that contains data (Tests here)
     * is setting the parameter which should be the current state of the list
     * and notifies that changes in data here to not do it in MainActivity
     */
    fun setData(test: List<Tests>){
        Log.d("Adapter/setDATA","${test.toString()}")
        this.testList = test
        notifyDataSetChanged()
    }

}