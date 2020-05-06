package `in`.ceeq.mercury

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import java.util.concurrent.Executors

class MainRepository {

	fun getPosts(): PagedResponse<Post?, Post> {
		val sourceFactory = PostRemoteDataSourceFactory()

		val livePagedList = LivePagedListBuilder(
				sourceFactory,
				PagedList.Config.Builder()
						.setInitialLoadSizeHint(10)
						.setPageSize(10)
						.build()
		).setFetchExecutor(Executors.newFixedThreadPool(5)).build()

		val networkState =
				Transformations.switchMap(sourceFactory.sourceLiveData) { it.networkState }

		return PagedResponse(
				response = null,
				pagedList = livePagedList,
				networkState = networkState,
				retry =
				{
					sourceFactory.sourceLiveData.value?.retryAllFailed()
				}
		)
	}
}
