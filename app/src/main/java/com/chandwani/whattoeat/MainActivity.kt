package com.chandwani.whattoeat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity(),
        View.OnClickListener {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        var signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(1)
        signInButton.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI(account)
        }
    }

    override fun onClick(V: View?) {
        when(V!!.getId()) {
            R.id.sign_in_button -> signIn()
        }
    }

    private fun signIn() {
        var signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {

            val account = completedTask.getResult(ApiException::class.java)

            updateUI(account)
        }
        catch (e : ApiException) {
            //TODO: catch exception
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        var intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", account!!.email.toString())
        intent.putExtra("givenName", account.givenName.toString())
        startActivity(intent)
    }
}
