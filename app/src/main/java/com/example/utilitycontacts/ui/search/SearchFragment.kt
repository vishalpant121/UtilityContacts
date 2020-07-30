package com.example.utilitycontacts.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.utilitycontacts.ui.infoList.InfoViewModel
import com.example.utilitycontacts.R
import com.example.utilitycontacts.ui.home.HomeFragment

class SearchFragment : Fragment() {

    interface SearchFragmentListener {
        fun searchList(location : String?, occupation : String?)
    }

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var infoViewModel: InfoViewModel
    private var allLocations: List<String>? = null
    private var allOccByLoc: List<String>? = null
    private var locations: Spinner? = null
    private var occupations: Spinner? = null
    private var searchButton: Button? = null
    private var listener : SearchFragmentListener? = null
    private var selectedLocation : String? = null
    private var selectedOccupation : String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        locations = root.findViewById(R.id.loc_spinner)
        occupations = root.findViewById(R.id.occ_spinner)
        searchButton = root.findViewById(R.id.search)

        searchButton?.setOnClickListener {
            listener = context as SearchFragmentListener
            if(selectedLocation == " - ") {
                selectedLocation = null
            }
            if(selectedOccupation == " - ") {
                selectedOccupation = null
            }
            listener?.searchList(selectedLocation, selectedOccupation)
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, HomeFragment(), "NewFragmentTag")
            ft.addToBackStack("back")
            ft.commit()
        }

        infoViewModel.loadAllLocations().observe(viewLifecycleOwner, Observer {
            val locSet : MutableList<String> = mutableListOf()
            locSet.add(0, " - ")
            locSet.addAll(it)
            allLocations = locSet.distinct()
            searchViewModel.locationList.observe(viewLifecycleOwner, Observer {

                ArrayAdapter(context!!, android.R.layout.simple_spinner_item, allLocations!!)
                    .also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        locations?.adapter = adapter

                    }
            })

            locations?.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    selectedLocation = allLocations!![position]
                    infoViewModel.loadAllOccupationOfLocation(selectedLocation!!)
                        .observe(viewLifecycleOwner, Observer {items ->
                            val occSet : MutableList<String> = mutableListOf()
                            occSet.add(0, " - ")
                            occSet.addAll(items)
                            allOccByLoc = occSet.distinct()
                            searchViewModel.occupationList.observe(viewLifecycleOwner, Observer {
                                ArrayAdapter(
                                    context!!,
                                    android.R.layout.simple_spinner_item,
                                    allOccByLoc!!
                                )
                                    .also { adapter ->
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        occupations?.adapter = adapter
                                    }

                            })
                            occupations?.onItemSelectedListener = object : OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedOccupation = allOccByLoc!![position]
                                }

                            }
                        })
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            }

        })

        return root
    }
}
