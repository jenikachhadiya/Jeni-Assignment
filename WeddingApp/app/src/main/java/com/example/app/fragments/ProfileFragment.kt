package com.example.app.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.app.activitys.SettingActivity
import com.example.weddingapp.R
import com.example.weddingapp.databinding.FragmentProfileBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding


    val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it.let {
            binding.ivProfileIcon.setImageURI(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.ivProfileIcon.setOnClickListener {

            showOptionDialog()
        }

        binding.etDate.setOnClickListener {

            pickDob()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ivSetting.setOnClickListener {
            startActivity(Intent(requireActivity(), SettingActivity::class.java))
        }
    }

    private fun showOptionDialog() {

        val items = arrayOf("From Gallery", "From Camera")

        AlertDialog.Builder(requireActivity())
            .setTitle("Select option")
            .setItems(items, DialogInterface.OnClickListener { dialogInterface, i ->

                when (i) {
                    0 -> {
                        // Caller
                        galleryContract.launch("image/*")

                    }
                    1 -> {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (checkCameraPermission()) {
                                pickImageFromCamera()
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                                }

                            }
                        } else {
                            pickImageFromCamera()
                        }
                    }
                }


            }).show()
    }

    private fun pickImageFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 200) {
            data?.let {
                binding.ivProfileIcon.setImageBitmap(data.extras!!.get("data") as Bitmap)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                pickImageFromCamera()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }



    private fun pickDob() {

        binding.etDate.setOnClickListener {
            val constraintsBuilderRange = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

            MaterialDatePicker.Builder.datePicker().setTitleText(getString(R.string._select_date))
            val datePickerDialog = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilderRange.build()).build()
            datePickerDialog.show(childFragmentManager, "MATERIAL_DATE_PICKER")

            datePickerDialog.addOnPositiveButtonClickListener {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = sdf.format(it)
                binding.etDate.setText(date)
            }
        }
    }

}