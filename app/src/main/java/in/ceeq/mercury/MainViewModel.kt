package `in`.ceeq.mercury

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

	private val repository: MainRepository = MainRepository()

	private val pagedResponse = MutableLiveData<PagedResponse<Post?, Post>>()

	val posts: LiveData<PagedList<Post>> = switchMap(pagedResponse) {
		it.pagedList
	}

	fun getPosts() {
		val posts = repository.getPosts()
		pagedResponse.postValue(posts)
	}
}
