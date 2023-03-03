package com.example.new_task.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.room.Room
import com.example.new_task.R
import com.example.new_task.dataBase.Userdatabase
import com.example.new_task.databinding.ActivitySignInBinding
import com.example.new_task.databinding.DialogBoxBinding
import com.example.new_task.preference.PrefClass
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import java.util.*
import java.util.regex.Pattern


class SignInActivity : AppCompatActivity() {

    private lateinit var db: Userdatabase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdb: DatabaseReference
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManger: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        //Google signing
        //request to send Default signing id
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()

        //google
        binding.btnGoogle.setOnClickListener {
            signIn()
        }

        //facebook
        callbackManger = CallbackManager.Factory.create()

        binding.btnFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        }

        LoginManager.getInstance()
            .registerCallback(callbackManger, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(result: LoginResult) {
                    //navigateToHome(null
                    handleFacebookAccessToken(result.accessToken)
                    Log.e(TAG, "onSuccess: $result")

                }

            })

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
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        finish()
                        Toast.makeText(
                            applicationContext, "Auth in Firebase", Toast.LENGTH_SHORT
                        ).show()
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
                            // if (manager.getUserData() != null) {
                            if (binding.btnSwitch.isChecked) {
                                PrefClass(this).insertRecode(email = email, pass = pass)
                                // PrefClass(this).saveUserData(user = manager.getUserData())
                            } else {
                                PrefClass(this).removeData()
                            }
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.done_signIn),
                                Toast.LENGTH_SHORT
                            ).show()

                            // }
                        }


                    }

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


    //Google
    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        launcher.launch(intent)

    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSigning(task)
            }
        }

    private fun handleSigning(task: Task<GoogleSignInAccount>) {

        if (task.isSuccessful) {
            var account: GoogleSignInAccount? = task.result
            if (account != null) {
                navigateToHome(account)
            }
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun navigateToHome(account: GoogleSignInAccount?) {
        //firebase authentication
        val user = GoogleAuthProvider.getCredential(account?.idToken, null)
        mAuth.signInWithCredential(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // PrefClass(this).insertRecode("${account.email}","${account.idToken}")
                Toast.makeText(applicationContext, "${account?.displayName}", Toast.LENGTH_SHORT)
                    .show()


                startActivity(Intent(applicationContext, HomeActivity::class.java))
            } else {

                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }

    }

    //facebook
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManger.onActivityResult(requestCode, resultCode, data)

    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        //firebase authentication
        val credential = FacebookAuthProvider.getCredential(token.token)
        Log.e(TAG, "handleFacebookAccessToken: ${token.token}")
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user!=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }else{
            Toast.makeText(this, "It is Fail", Toast.LENGTH_SHORT).show()
        }


    }

    private fun forgot() {
        //password formula
        val pattern: Pattern = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        )

        val bind = DialogBoxBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this).setView(bind.root)

        val dialog = builder.create()
        dialog.show()

        bind.btnDone.setOnClickListener {

            val email = bind.etEmail.text.toString().trim()
            val nPassword = bind.etNpass.text.toString().trim()
            val cPassword = bind.etCpass.text.toString().trim()
            val eData = db.userdaos().findByData(email)

            if (bind.etEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    applicationContext, getString(R.string.enter_valid_email), Toast.LENGTH_SHORT
                ).show()
            } else if (eData?.email != bind.etEmail.text.toString()) {
                Toast.makeText(
                    applicationContext, getString(R.string.enter_valid_password), Toast.LENGTH_SHORT
                ).show()
            } else if (bind.etNpass.text.toString().isEmpty()) {
                Toast.makeText(
                    applicationContext, getString(R.string.enter_new_password), Toast.LENGTH_SHORT
                ).show()
            } else if (!pattern.matcher(bind.etNpass.text.toString()).matches()) {
                Toast.makeText(
                    applicationContext, getString(R.string.enter_valid_password), Toast.LENGTH_SHORT
                ).show()
            } else if (nPassword != cPassword) {
                bind.etCpass.setText("")
                Toast.makeText(
                    applicationContext, getString(R.string.enter_same_password), Toast.LENGTH_SHORT
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





