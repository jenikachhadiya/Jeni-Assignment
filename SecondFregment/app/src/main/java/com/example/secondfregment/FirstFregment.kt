package com.example.secondfregment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.secondfregment.databinding.ActivityMainBinding
import com.example.secondfregment.databinding.FragmentFirstFregmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFregment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFregment : Fragment() {

    lateinit var binding: FragmentFirstFregmentBinding


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentFirstFregmentBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {

            var name = binding.etName.text.toString().trim()
            var email = binding.etEmail.text.toString().trim()
            var age = binding.etAge.text.toString().trim()


            var bundle = Bundle()
            bundle.putString("Name",name)
            bundle.putString("Email",email)
            bundle.putString("Age",age)


            //navigrate to second Fregment

            var fragment = SecondFregment()
            fragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.Fregment_Contanier,fragment)
                    .addToBackStack(null)
                    .commit()


        }

    }



}