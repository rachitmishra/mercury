package `in`.ceeq.mercury

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class PostRemoteDataSourceFactory() : DataSource.Factory<Int, Post>() {

	val sourceLiveData = MutableLiveData<PostRemoteDataSource>()

	override fun create(): DataSource<Int, Post> {
		val source = PostRemoteDataSource()
		sourceLiveData.postValue(source)
		return source
	}
}
