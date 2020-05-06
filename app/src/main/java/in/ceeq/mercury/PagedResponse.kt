package `in`.ceeq.mercury

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class PagedResponse<C, T>(
		val response: LiveData<C>? = null,
		// the LiveData of paged lists for the UI to observe
		val pagedList: LiveData<PagedList<T>>,
		// represents the network request status to show to the user
		val networkState: LiveData<Boolean>? = null,
		// represents the refresh status to show to the user. Separate from listNetworkState, this
		val retry: (() -> Unit)? = null
)
