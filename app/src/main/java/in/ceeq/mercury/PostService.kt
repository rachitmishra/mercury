package `in`.ceeq.mercury

import io.reactivex.Observable
import retrofit2.http.GET

interface PostService {

    @get:GET("posts")
    val post: Observable<Post>
}
