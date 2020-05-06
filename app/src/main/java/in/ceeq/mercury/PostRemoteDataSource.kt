package `in`.ceeq.mercury

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PostRemoteDataSource() : PageKeyedDataSource<Int, Post>() {

	private var retry: (() -> Any)? = null

	val networkState = MutableLiveData<Boolean>()

	fun retryAllFailed() {
		val prevRetry = retry
		retry = null
		prevRetry?.let {
			Executors.newSingleThreadExecutor().execute {
				it.invoke()
			}
		}
	}

	override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Post>) {
		val response = getPosts(1)
		val responseBody = response.body()
		if (!response.isSuccessful || responseBody == null) {
			retry = {
				loadInitial(params, callback)
			}
			networkState.postValue(false)
			return
		}


		callback.onResult(responseBody, 1, 2)
	}

	override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
		val response = getPosts(params.key)
		val responseBody = response.body()

		if (!response.isSuccessful || responseBody == null) {
			retry = {
				loadAfter(params, callback)
			}
			networkState.postValue(false)
			return
		}

		callback.onResult(responseBody, params.key + 1)
	}

	override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
		// Ignore
	}

	private fun getPosts(pageNumber: Int): Response<List<Post>?> {
		val retrofit = Retrofit.Builder()
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(OkHttpClient.Builder()
						.connectTimeout(10, TimeUnit.SECONDS)
						.readTimeout(10, TimeUnit.SECONDS).build())
				.build()

		return retrofit.create(PostService::class.java).getPosts(pageNumber).execute()
	}
}
