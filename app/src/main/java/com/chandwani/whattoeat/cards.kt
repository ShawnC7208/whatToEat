package com.chandwani.whattoeat

/**
 * Created by Arsalan on 1/8/2018.
 */
class cards {
    private lateinit var url: String
    private lateinit var name: String
    private lateinit var rating: String
    private lateinit var phone: String
    constructor(url:String, name:String, rating:String, phone:String){
        this.name=name
        this.url=url
        this.rating=rating
        this.phone=phone
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

    fun getRating():String{
        return rating
    }

    fun setRating(){
        this.rating=rating
    }

    fun getPhone():String{
        return phone
    }

    fun setPhone(){
        this.phone=phone
    }

}