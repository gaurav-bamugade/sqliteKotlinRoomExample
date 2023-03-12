package com.example.sqlitekotlinroomexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitekotlinroomexample.databinding.RcItemBinding
import com.example.sqlitekotlinroomexample.db.Subscriber

//updated recycler
class MyRecyclerViewAdapter(private val clickListener:(Subscriber)->Unit)
    :RecyclerView.Adapter<MyViewHolder>() {

    private val subscriberList=ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutinflater=LayoutInflater.from(parent.context)
        val binding:RcItemBinding=DataBindingUtil.inflate(layoutinflater,R.layout.rc_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  subscriberList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position],clickListener)
    }
    fun setList(subscriber: List<Subscriber>)
    {
        subscriberList.clear()
        subscriberList.addAll(subscriber)
    }

}
class MyViewHolder(val binding: RcItemBinding ):RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber:Subscriber,clickListener:(Subscriber)->Unit)
    {
        binding.nameTextView.text=subscriber.name
        binding.emailTextView.text=subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}

/*
class MyRecyclerViewAdapter(private val subscriberList: List<Subscriber>,
                            private val clickListener:(Subscriber)->Unit)
    :RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutinflater=LayoutInflater.from(parent.context)
        val binding:RcItemBinding=DataBindingUtil.inflate(layoutinflater,R.layout.rc_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  subscriberList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position],clickListener)
    }

}
class MyViewHolder(val binding: RcItemBinding ):RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber:Subscriber,clickListener:(Subscriber)->Unit)
    {
        binding.nameTextView.text=subscriber.name
        binding.emailTextView.text=subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}*/
