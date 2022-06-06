package com.maad.eta.employee

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.eta.R
import com.maad.eta.employee.SickRequest

class SickStatusAdapter(val activity: Activity, val requests: ArrayList<SickRequest>) :
    RecyclerView.Adapter<SickStatusAdapter.RequestVH>() {

    inner class RequestVH(view: View) : RecyclerView.ViewHolder(view) {
        val sickIV: ImageView = view.findViewById(R.id.sick_iv)
        val detailsTV: TextView = view.findViewById(R.id.details_tv)
        val statusTV: TextView = view.findViewById(R.id.status_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequestVH(
            activity.layoutInflater.inflate(
                R.layout.view_sick_status_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RequestVH, position: Int) {
        Glide.with(activity).load(requests[position].picture).into(holder.sickIV)
        holder.detailsTV.text = requests[position].details
        holder.statusTV.text = requests[position].status
        val color = when (requests[position].status) {
            "Pending" -> android.R.color.darker_gray
            "Approved" -> android.R.color.holo_green_dark
            else -> android.R.color.holo_red_dark //"Rejected" case
        }
        holder.statusTV.setTextColor(ContextCompat.getColor(activity, color))

    }

    override fun getItemCount() = requests.size
}