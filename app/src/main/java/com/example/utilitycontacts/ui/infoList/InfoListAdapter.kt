package com.example.utilitycontacts.ui.infoList

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.utilitycontacts.R
import com.example.utilitycontacts.room.entity.Info


class InfoListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context : Context = context
    private var infoList = emptyList<Info>() // Cached copy of words

    inner class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoName: TextView = itemView.findViewById(R.id.name)
        val infoOcc: TextView  = itemView.findViewById(R.id.occupation)
        val infoAddress: TextView = itemView.findViewById(R.id.address)
        val infoPhone : TextView = itemView.findViewById(R.id.phone)
        val infoAltPhone : TextView = itemView.findViewById(R.id.alt_phone)
        val infoAvailability: TextView = itemView.findViewById(R.id.tv_av_hrs)
        val infoImage : ImageView = itemView.findViewById(R.id.info_image)
        val callPhone : ImageView = itemView.findViewById(R.id.call_phone)
        val callAltPhone : ImageView = itemView.findViewById(R.id.call_alt_phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val current = infoList[position]
        holder.infoName.text = current.name
        holder.infoAddress.text = current.address
        holder.infoAltPhone.text = current.altPhone
        holder.infoAvailability.text = "Available from " + current.from + " to "+ current.to+ " "
        holder.infoOcc.text = current.occ
        holder.infoPhone.text = current.phone
        holder.infoImage.setImageResource(R.drawable.default_colorful_placeholder)
        holder.callPhone.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + current.phone)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(context, "Calling...", Toast.LENGTH_SHORT).show()
                context.startActivity(callIntent)
            }
        }

        holder.callAltPhone.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + current.altPhone)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(context, "Calling...", Toast.LENGTH_SHORT).show()
                context.startActivity(callIntent)
            }
        }
    }


    internal fun setInfoList(infoList: List<Info>) {
        this.infoList = infoList
        notifyDataSetChanged()
    }

    override fun getItemCount() = infoList.size
}
