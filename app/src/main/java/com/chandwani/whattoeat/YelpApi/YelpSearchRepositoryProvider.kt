package com.chandwani.whattoeat.YelpApi

/**
 * Created by Shawn on 1/1/2018.
 */
object YelpSearchRepositoryProvider {
    fun provideYelpSearchRepository(): YelpSearchRepository {
        return YelpSearchRepository(YelpHelper.Factory.create())
    }
}