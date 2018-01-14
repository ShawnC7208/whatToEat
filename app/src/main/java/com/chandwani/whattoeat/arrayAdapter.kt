package com.chandwani.whattoeat

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide



/**
 * Created by Arsalan on 1/8/2018.
 */
class arrayAdapter : ArrayAdapter<cards> {

    private var adaptercontext : Context
    constructor(context: Context, resourceId: Int, items: ArrayList<cards>?) : super(context, resourceId, items) {
        this.adaptercontext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var card_item: cards = getItem(position)
        var convertView = convertView
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, parent, false)

        var name:TextView = convertView!!.findViewById<TextView>(R.id.helloText)
        var image:ImageView = convertView!!.findViewById<ImageView>(R.id.image)
        var rating:TextView = convertView!!.findViewById<TextView>(R.id.rating)
        var phone:TextView = convertView!!.findViewById<TextView>(R.id.phone)

        name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        name.setText(card_item.getName())
        rating.setText(card_item.getRating())
        phone.setText(card_item.getPhone())

        when (card_item.geturl()) {
            "default" -> Glide.with(convertView.context).load(R.mipmap.ic_launcher).into(image)
            else -> {
                Glide.with(convertView.context).load(card_item.geturl()).into(image)
            }
        }

        return convertView
    }
}