package com.example.new_task.Activity


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.new_task.DataBase.Userdatabase
import com.example.new_task.Prefrencee.PrefClass
import com.example.new_task.R
import com.example.new_task.databinding.ActivitySignInBinding
import com.example.new_task.databinding.DialogBoxBinding
import java.util.regex.Pattern


class SignInActivity : AppCompatActivity() {

    private lateinit var db: Userdatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //password formula
        val pattern: Pattern = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        )

        //database init
        db = Room.databaseBuilder(this, Userdatabase::class.java, getString(R.string.Db))
            .allowMainThreadQueries().build()


        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPass.text.toString().trim()


            if (binding.etEmail.text.toString().isEmpty()) {
                binding.tvEmail.isErrorEnabled = false
                binding.tvEmail.error = getString(R.string.enter_valid_email)
                binding.etEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                binding.tvEmail.isErrorEnabled = false
                binding.tvEmail.error = getString(R.string.email_not_valid)
                binding.etEmail.requestFocus()
            } else if (binding.etPass.text.toString().isEmpty()) {
                binding.tvEmail.isErrorEnabled = false
                binding.tvEmail.error = getString(R.string.enter_valid_password)
                binding.etEmail.requestFocus()
            } else if (!pattern.matcher(binding.etPass.text.toString()).matches()) {
                binding.tvPass.isErrorEnabled = false
                binding.tvPass.error = getString(R.string.password_not_valid)
                binding.etEmail.requestFocus()
            } else {
                val eData = db.userdaos().findByData(email)
                if (eData?.email != binding.etEmail.text.toString().trim()) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.email_is_not_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (eData.pass != binding.etPass.text.toString().trim()) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.enter_valid_password),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val manager = PrefClass(this)
                    manager.setLoginStatus(true)
                    if (binding.btnSwitch.isChecked) {
                        PrefClass(this).insertRecode(email = email, pass = pass)
                    }else {
                        PrefClass(this).removeData()
                    }
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.done_signIn),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                }


            }

        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
            finish()
        }


        //forgot password
        binding.btnForgotp.setOnClickListener {
            forgot()
        }
    }

    private fun forgot() {
        //password formula
        val pattern: Pattern = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        )

        val bind = DialogBoxBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
            .setView(bind.root)

        val dialog = builder.create()
        dialog.show()

        bind.btnDone.setOnClickListener {

            val email = bind.etEmail.text.toString().trim()
            val nPassword = bind.etNpass.text.toString().trim()
            val cPassword = bind.etCpass.text.toString().trim()
            val eData = db.userdaos().findByData(email)

            if (bind.etEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.enter_valid_email),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (eData?.email != bind.etEmail.text.toString()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.enter_valid_password),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (bind.etNpass.text.toString().isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.enter_new_password),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!pattern.matcher(bind.etNpass.text.toString()).matches()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.enter_valid_password),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (nPassword != cPassword) {
                bind.etCpass.setText("")
                Toast.makeText(
                    applicationContext,
                    getString(R.string.enter_same_password),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                db.userdaos().updateUser(email, nPassword)
                Toast.makeText(applicationContext, getString(R.string.done), Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }


        }


    }

}





