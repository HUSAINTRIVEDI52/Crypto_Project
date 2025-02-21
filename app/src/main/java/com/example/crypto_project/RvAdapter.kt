package com.example.crypto_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.example.crypto_project.Model

import androidx.recyclerview.widget.RecyclerView
//import androidx.xr.scenecore.Model
import com.example.crypto_project.databinding.RvItemBinding

class RvAdapter(val context: Context,var data:ArrayList<Model>): RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:RvItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
val view=RvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setAnim(holder.itemView)
    holder.binding.Name.text=data[position].name
    holder.binding.Symbol.text=data[position].symbol
    holder.binding.Price.text=data[position].price
    }

    override fun getItemCount(): Int   {
        return data.size
    }
    fun setAnim(view: View){
        val anim=AlphaAnimation(0.0f,1.0f)
        anim.duration=1000
        view.startAnimation(anim)

    }

    fun changeData(filterData: ArrayList<Model>) {
        data=filterData
        notifyDataSetChanged()

    }
}