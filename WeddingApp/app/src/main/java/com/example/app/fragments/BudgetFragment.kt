package com.example.app.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.app.adapter.HedarAdapter
import com.example.app.model.Box
import com.example.app.model.Hedar
import com.example.weddingapp.R
import com.example.weddingapp.databinding.FragmentBudgetBinding


class BudgetFragment : Fragment() {

    private lateinit var binding: FragmentBudgetBinding

    var hedarList = mutableListOf<Hedar>()
    lateinit var hedaradapter: HedarAdapter

    lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBudgetBinding.inflate(inflater, container, false)

        preparaData()

        hedaradapter = HedarAdapter(requireContext(), hedarList)
        binding.rcvHedarBudget.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvHedarBudget.adapter = hedaradapter


        binding.etBudget.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


            }

            override fun afterTextChanged(s: Editable) {

                filter(s.toString())
            }
        })

        return binding.root
    }

    private fun filter(text: String?) {

        var filterHedarList = mutableListOf<Hedar>()

        for (item in hedarList) {

            if (text != null) {
                if (item.hedar.lowercase().contains(text.lowercase())) {
                    filterHedarList.add(item)
                }
            }
        }

        if (filterHedarList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            hedaradapter.filterList(filterHedarList)
        }
    }

    private fun preparaData() {

        var budgetList = mutableListOf<Box>()

        budgetList.add(Box(1, R.drawable.ic_box, "Wedding Hall", 1500.2))
        budgetList.add(Box(1, R.drawable.ic_box, "Wedding Hall", 1500.2))

        hedarList = mutableListOf()
        hedarList.add(Hedar(1, "Wedding Hall : \$30,000", budgetList = budgetList))
        hedarList.add(Hedar(1, "Food and Drinks : \$5,000", budgetList = budgetList))
        hedarList.add(Hedar(1, "Photographers : \$2,000", budgetList = budgetList))
        hedarList.add(Hedar(1, "Heir Styler : \$2,000", budgetList = budgetList))
        hedarList.add(Hedar(1, "Wedding Dress : \$5,000", budgetList = budgetList))

    }

}