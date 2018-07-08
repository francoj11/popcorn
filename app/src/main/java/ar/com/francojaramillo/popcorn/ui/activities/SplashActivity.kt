package ar.com.francojaramillo.popcorn.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ar.com.francojaramillo.popcorn.R
import ar.com.francojaramillo.popcorn.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val account = GoogleSignIn.getLastSignedInAccount(this)

            if (account != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, Constants.SPLASH_TIME)
    }
}
