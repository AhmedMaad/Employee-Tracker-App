package com.maad.eta.hr

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.eta.R
import com.maad.eta.employee.SickRequest

class SickRequestAdapter(val activity: Activity, val requests: ArrayList<SickRequest>, val itemClickListener: ItemClickListener)
    :RecyclerView.Adapter<SickRequestAdapter.RequestVH>(){

    interface ItemClickListener {
        fun onImageClick(position: Int)
        fun onAcceptBtnClick(position: Int)
        fun onRejectBtnClick(position: Int)
    }

    inner class RequestVH(view: View): RecyclerView.ViewHolder(view) {
        val sickIV: ImageView = view.findViewById(R.id.sick_iv)
        val detailsTV: TextView = view.findViewById(R.id.details_tv)
        val acceptBtn: Button = view.findViewById(R.id.accept_btn)
        val rejectBtn: Button = view.findViewById(R.id.reject_btn)

        init {
            sickIV.setOnClickListener { itemClickListener.onImageClick(adapterPosition) }
            acceptBtn.setOnClickListener { itemClickListener.onAcceptBtnClick(adapterPosition) }
            rejectBtn.setOnClickListener { itemClickListener.onRejectBtnClick(adapterPosition) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequestVH(activity.layoutInflater.inflate(R.layout.sickness_request_list_item, parent, false))

    override fun onBindViewHolder(holder: RequestVH, position: Int) {
        Glide.with(activity).load(requests[position].picture).into(holder.sickIV)
        holder.detailsTV.text = requests[position].details
    }

    override fun getItemCount() = requests.size
}