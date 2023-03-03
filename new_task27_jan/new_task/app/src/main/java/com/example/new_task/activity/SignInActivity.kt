package com.example.new_task.activity


import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    companion object {
        private const val READ_EXTERNAL_STORAGE = 104
        private const val WRITE_EXTERNAL_STORAGE = 105
    }

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

     //   checkPermissionRead()
        FirebaseApp.initializeApp(this)
        //Google signing
        //request to send Default signing id
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
        //phone
        binding.btnPhone.setOnClickListener {
            startActivity(Intent(applicationContext,PhoneActivity::class.java))
            finish()
        }


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

    private fun checkPermissionRead() {
        //Read Permission
        /*if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Read_External Data Permission Needed")
                    .setMessage("This add Needs the Read Permission ,Please access to use External functionality")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        requestReadData()
                    })
                    .create()
                    .show()
            } else {
                requestReadData()
            }
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(applicationContext, "permission Granted", Toast.LENGTH_SHORT).show()
            }
            else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // don't allow
                AlertDialog.Builder(this)
                    .setTitle("Read_External Data Permission Needed")
                    .setMessage("This add Needs the Read Permission ,Please access to use External functionality")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ -> requestReadData()
                    })
                    .create()
                    .show()
                Toast.makeText(applicationContext, "show Dialog Here", Toast.LENGTH_SHORT).show()
            }else{
                //permission Deny
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE)
            }

        }

    }

    private fun requestReadData() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ),
            READ_EXTERNAL_STORAGE

        )
    }

    /*private fun checkWritePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }
    }*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT)
                            .show()
                        // requestReadData()
                    }

                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null)
                            )
                        )
                    }
                }

            }
        }
    }


    //Google
    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        launcher.launch(intent)

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                Toast.makeText(
                    applicationContext,
                    "${account?.displayName}",
                    Toast.LENGTH_SHORT
                )
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
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
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





