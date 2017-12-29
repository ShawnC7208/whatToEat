package com.chandwani.whattoeat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import butterknife.OnClick
import com.lorentzos.flingswipe.SwipeFlingAdapterView

class HomeActivity : AppCompatActivity() {

    private var al: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var intentExtras = intent

        var extrasBundle = intentExtras.extras

        var email = extrasBundle.getString("email")
        var givenName = extrasBundle.getString("givenName")
        var testToast = Toast.makeText(this,givenName, Toast.LENGTH_SHORT)
        testToast.show()

        var flingContainer: SwipeFlingAdapterView = findViewById(R.id.frame)

        al = ArrayList<String>()

        al!!.add("Card 1")
        al!!.add("Card 2")

        arrayAdapter = ArrayAdapter<String>(this, R.layout.card, R.id.helloText, al)

        flingContainer.setAdapter(arrayAdapter)
        flingContainer.adapter = arrayAdapter
        flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun onScroll(scrollProgressPercent: Float) {
                val view = flingContainer.selectedView
                view.findViewById<View>(R.id.item_swipe_right_indicator).setAlpha(if (scrollProgressPercent < 0) -scrollProgressPercent else 0f)
                view.findViewById<View>(R.id.item_swipe_left_indicator).setAlpha(if (scrollProgressPercent > 0) scrollProgressPercent else 0f)
            }

            override fun removeFirstObjectInAdapter() {
                // this is to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                al!!.removeAt(0)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                //Do something on the left!
                Toast.makeText(this@HomeActivity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                //Do something on the right!
                Toast.makeText(this@HomeActivity, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here
                al!!.add("XML " + i.toString())
                arrayAdapter!!.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i += 1
            }
        })

        // Add an OnItemClickListener
        flingContainer.setOnItemClickListener {
            itemPosition, dataObject -> Toast.makeText(this@HomeActivity, "Clicked!", Toast.LENGTH_SHORT)
        }

        @OnClick(R.id.item_swipe_right_indicator)
        fun right() {
            flingContainer.topCardListener.selectRight()
        }

        @OnClick(R.id.item_swipe_left_indicator)
        fun left() {
            flingContainer.topCardListener.selectLeft()
        }
    }
}
