package com.example.new_task.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import com.example.new_task.R
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.modal.ResponseData
import com.example.new_task.api.post.modal.changePass
import com.example.new_task.databinding.FragmentProfileFragmentBinding
import com.example.new_task.databinding.PasswordDailogBinding
import com.example.new_task.databinding.UpdateprofileLayoutBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileFragmentBinding.inflate(layoutInflater, container, false)

        binding.etDob.setOnClickListener {
            val showCalender = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date Of Birth")
            val datePickerDialog = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(showCalender.build()).build()
            datePickerDialog.show(childFragmentManager, "MATERIAL_DATE_PICKER")

            datePickerDialog.addOnPositiveButtonClickListener {
                val format = SimpleDateFormat("yyyy/mm/dd", Locale.getDefault())
                val date = format.format(it)
                binding.etDob.setText(date)
            }

        }





        //Profile Data Geting
        ApiClint.init(requireContext()).getProfileData()
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    val user = response.body()
                    if (user != null) {
                        binding.tvNametitle.text = user.data.userName
                        binding.tvEmailtitle.text = user.data.userEmail
                        binding.etName.text = user.data.userName
                        binding.etEmail.text = user.data.userEmail
                        binding.etDob.text = user.data.userDob
                        binding.etMobile.text = user.data.userMobileNo
                    }
                    Log.e(TAG, "onResponse: $user")
                    Toast.makeText(requireContext(), "is Successfully", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(requireContext(), "is fail", Toast.LENGTH_SHORT).show()
                }

            })

        binding.tvChangePass.setOnClickListener {
            val bind = PasswordDailogBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(requireContext()).setView(bind.root)

            val dialog = builder.create()
            dialog.show()
            bind.btnDone.setOnClickListener {
                ApiClint.init(requireContext()).changePassword()
                    .enqueue(object : Callback<changePass> {
                        override fun onResponse(
                            call: Call<changePass>,
                            response: Response<changePass>
                        ) {
                            Log.e(TAG, "onResponse: ${response.body()}")
                            Toast.makeText(requireContext(), "successfully", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onFailure(call: Call<changePass>, t: Throwable) {
                            Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
                        }
                    })
                dialog.dismiss()
            }
        }

        //edit Api
        binding.ivEdit.setOnClickListener {
            val binding = UpdateprofileLayoutBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(requireContext()).setView(binding.root)

            val dailog = builder.create()
            dailog.show()

            binding.btnDone.setOnClickListener {
                //Put Api
                ApiClint.init(requireContext()).updateProfile()
                    .enqueue(object : Callback<ResponseData> {
                        override fun onResponse(
                            call: Call<ResponseData>,
                            response: Response<ResponseData>
                        ) {
                            val user = response.body()

                            if (user != null) {
                                binding.etName.setText(user.data.userName)
                                binding.etName.setText(user.data.userEmail)
                                binding.etName.setText(user.data.userMobileNo)
                                binding.etName.setText(user.data.userDob)
                            }
                            Toast.makeText(requireContext(), "successfully", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                            Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
                        }
                    })

            }
            dailog.dismiss()


        }



























        return (binding.root)
    }


}