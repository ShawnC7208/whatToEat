import com.yelp.fusion.client.connection.YelpFusionApi
import com.yelp.fusion.client.connection.YelpFusionApiFactory

/**
 * Created by Arsalan on 12/30/2017.
 */
class YelpHelper{
    private lateinit var apiFactory : YelpFusionApiFactory
    private lateinit var yelpFusionApi : YelpFusionApi
    constructor(){
        apiFactory= YelpFusionApiFactory()
        yelpFusionApi=apiFactory.createAPI("fPm6_W_1h4Svblimj7GJAw", "sXo8RejGu9sQbh8NK8APAF8v3NbwveR0ycScLkve1YGNKuAGhbITGe81cJSrvNq5")
    }
}