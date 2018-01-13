package com.chandwani.whattoeat

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Arsalan on 1/8/2018.
 */
class arrayAdapter : ArrayAdapter<cards> {

    private var adaptercontext : Context
    constructor(context: Context, resourceId: Int, items: ArrayList<cards>?) : super(context, resourceId, items) {
        this.adaptercontext = context
    }

    private class ViewHolder{
        lateinit var imageView:ImageView
        lateinit var textView:TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var myConvertView = convertView
        //this.convertView = convertView
        var holder : ViewHolder? = null
        var rowItem : cards = getItem(position)

        var mInflater : LayoutInflater = context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if(convertView == null){
            myConvertView = mInflater.inflate(R.layout.card,null)
            holder = ViewHolder()
            holder.textView = myConvertView.findViewById(R.id.helloText)
            holder.imageView = myConvertView.findViewById(R.id.image)
            myConvertView.setTag(holder)
        }
        else {
            holder = myConvertView!!.getTag() as ViewHolder?
        }

        holder!!.textView.setText(rowItem.getName())
        holder!!.imageView.setImageResource(R.mipmap.ic_launcher)

        return myConvertView
//        var card_item: cards = getItem(position)
//        var convertView=convertView
//        if (convertView == null)
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, parent, false)
//
//
//        var name:TextView = convertView.findViewById<TextView>(R.id.helloText)
//        var image:ImageView = convertView.findViewById<ImageView>(R.id.image)
//
//        name.setText(card_item.getName())
//        image.setImageResource(R.mipmap.ic_launcher)
//
//        return convertView
    }
}