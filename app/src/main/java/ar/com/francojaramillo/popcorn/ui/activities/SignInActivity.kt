package ar.com.francojaramillo.popcorn.ui.activities

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import ar.com.francojaramillo.popcorn.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

/**
 * Activity that manages the process of Signing up with Google account
 */
class SignInActivity : AppCompatActivity() {

    // Constants to use in activity result
    private val GOOGLE_SIGN_IN = 1001

    // Google client and signinBtn
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var loginBtn: SignInButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupGoogleSignIn()

        loginBtn = findViewById(R.id.sign_in_button)
        loginBtn?.setOnClickListener{ startSignInProcess() }
    }

    /**
     * Setups the GoogleClient for signing in
     */
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    /**
     * Opens the intent to sign in with google Account
     */
    private fun startSignInProcess() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    /**
     * Manages the Activities results
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    /**
     * Manages the result of the process of signing in
     */
    fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d("POPCORN_TAG", "EMAIL: " + account.email)

            var intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            showMessage("Oops!", e.message)
        }
    }

    fun showMessage(title: String?, message: String?) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", {dialog: DialogInterface?, which: Int -> })
                .show()
    }
}
