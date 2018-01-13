package com.chandwani.whattoeat

/**
 * Created by Arsalan on 1/8/2018.
 */
class cards {
    private lateinit var url: String
    private lateinit var name: String
    constructor(url:String, name:String){
        this.name=name
        this.url=url
    }

    fun geturl():String{
        return url
    }

    fun setUserId(){
        this.url=url
    }

    fun getName():String{
        return name
    }

    fun setName(){
        this.name=name
    }

}