package com.example.new_task.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.transition.TransitionInflater
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.modal.ResponseData
import com.example.new_task.api.post.modal.changePass
import com.example.new_task.databinding.FragmentProfileFragmentBinding
import com.example.new_task.databinding.PasswordDailogBinding
import com.example.new_task.databinding.UpdateprofileLayoutBinding
import com.example.new_task.preference.AppConstans
import com.example.new_task.preference.PrefClass
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
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
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = format.format(it)
                binding.etDob.text = date
            }

        }

        //Profile Data Geting
        ApiClint.init(requireContext()).getProfileData()
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful){
                        if (response.body() != null){
                            val user = response.body()
                            var getApi = PrefClass(requireContext()).getUserDataApi()
                            if (user != null) {
                                if (getApi != null) {
                                    binding.tvNametitle.text = getApi.userName
                                    binding.tvEmailtitle.text = getApi.userEmail
                                    binding.etName.text = getApi.userName
                                    binding.etEmail.text = getApi.userEmail
                                    binding.etDob.text = getApi.userDob
                                    binding.etMobile.text = getApi.userMobileNo
                                    val img = getApi.userProfilePic
                                    Log.e(TAG, "onResponse: "+Gson().toJson(img))
                                    val baseUri = AppConstans.IMG_BASE_URI
                                    val finalUri = baseUri + img.toString()
                                    Glide
                                        .with(context!!)
                                        .load(finalUri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_baseline_sync_24)
                                        .into(binding.ivImage)
                                }
                                Log.e(TAG, "onResponse: ${getApi?.userProfilePic}")


                            }
                            Log.e(TAG, "onResponse: $user")
                            Toast.makeText(requireContext(), "is Successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else{
                        try {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseData>(){}.type
                            val responseData: ResponseData? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (responseData != null) {
                                Toast.makeText(requireContext(), responseData.msg, Toast.LENGTH_SHORT).show()
                            }

                        }catch (e: IOException){
                            e.printStackTrace()
                        }


                    }

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
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    Log.e(TAG, "onResponse: ${response.body()}")
                                    Toast.makeText(
                                        requireContext(),
                                        "successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<changePass>, t: Throwable) {
                            Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
                        }
                    })
                dialog.dismiss()
            }
        }

        //edit Api
        binding.btnEdit.setOnClickListener {
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
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    val user = response.body()
                                    if (user != null) {
                                        binding.etName.setText(user.data.userName)
                                        binding.etMobileNo.setText(user.data.userMobileNo)
                                        binding.etDob.setText(user.data.userDob)
                                    }
                                    Toast.makeText(
                                        requireContext(),
                                        "successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            } else {
                                //error
                            }
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
    private fun base64ToBitmap(base64: String?): Bitmap?{
        val imageAsBytes: ByteArray = Base64.decode(base64?.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)

    }
    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }




}