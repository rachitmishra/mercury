package `in`.ceeq.mercury

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("posts")
	fun getPosts(@Query("_page") pageNumber: Int): Call<List<Post>>

}
