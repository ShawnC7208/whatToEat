package com.chandwani.whattoeat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var intentExtras = intent

        var extrasBundle = intentExtras.extras

        var email = extrasBundle.getString("email")
        var givenName = extrasBundle.getString("givenName")
        var testToast = Toast.makeText(this,givenName, Toast.LENGTH_SHORT)
        testToast.show()
    }
}
