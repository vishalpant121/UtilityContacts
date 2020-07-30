package com.example.utilitycontacts.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utilitycontacts.ui.infoList.InfoListAdapter
import com.example.utilitycontacts.ui.infoList.InfoViewModel
import com.example.utilitycontacts.R


class HomeFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel
    private var selectedLocation: String? = null
    private var selectedOccupation: String? = null
    private var listener: HomeFragmentListener? = null

    interface HomeFragmentListener {
        fun getLocation(): String?
        fun getOccupation(): String?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        listener = context as HomeFragmentListener
        checkPermission()
        populateRV(root)


        return root
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            }

        } else {
            Log.e("DB", "PERMISSION GRANTED")
        }
    }

    private fun populateRV(root: View) {

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter =
            InfoListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        if (listener?.getLocation() == null && listener?.getOccupation() == null) {
            infoViewModel.allItems.observe(viewLifecycleOwner, Observer { items ->
                items?.let { adapter.setInfoList(it) }
                Log.i("MainActivity", "observing items = $items")
            })
        } else if (listener?.getLocation() != null && listener?.getOccupation() == null) {
            infoViewModel.loadAllByLocation(listener?.getLocation()!!)
                .observe(viewLifecycleOwner, Observer { items ->
                    items?.let { adapter.setInfoList(it) }
                    Log.i("MainActivity", "observing items = $items")
                })
        } else if (listener?.getLocation() == null && listener?.getOccupation() != null) {
            infoViewModel.loadAllByOccupation(listener?.getLocation()!!)
                .observe(viewLifecycleOwner, Observer { items ->
                    items?.let { adapter.setInfoList(it) }
                    Log.i("MainActivity", "observing items = $items")
                })
        } else {
            infoViewModel.loadAllByLocationAndOccupation(
                location = listener?.getLocation()!!,
                occupation = listener?.getOccupation()!!
            )
                .observe(viewLifecycleOwner, Observer { items ->
                    items?.let { adapter.setInfoList(it) }
                    Log.i("MainActivity", "observing items = $items")
                })

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                   Toast.makeText(context, "PERMISSION DENIED : Some functions would not work!", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}
