package `in`.ceeq.mercury

import `in`.ceeq.mercury.databinding.LayoutPostItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PostAdapter() : PagedListAdapter<Post, RecyclerView.ViewHolder>(PostItemViewHolder.DIFF_CALLBACK) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return PostItemViewHolder.create(parent, R.layout.layout_post_item)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val post = getItem(position)
		post?.let {
			(holder as? PostItemViewHolder)?.bindTo(post)
		}
	}
}


class PostItemViewHolder(
		private val binding: LayoutPostItemBinding) : RecyclerView.ViewHolder(binding.root) {

	fun bindTo(post: Post, onItemClick: ((Post) -> Unit)? = null) {
		binding.title.text = post.title
		binding.id.text = post.id
		binding.body.text = post.body
		binding.executePendingBindings()
	}


	companion object {

		fun create(parent: ViewGroup, layoutId: Int) =
				PostItemViewHolder(
						DataBindingUtil.inflate(
								LayoutInflater.from(parent.context),
								layoutId, parent, false
						)
				)

		val DIFF_CALLBACK: DiffUtil.ItemCallback<Post> =
				object : DiffUtil.ItemCallback<Post>() {

					override fun areItemsTheSame(oldSection: Post, newSection: Post) =
							oldSection == newSection

					override fun areContentsTheSame(oldSection: Post, newSection: Post) =
							oldSection == newSection
				}
	}
}
