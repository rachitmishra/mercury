package `in`.ceeq.mercury

import `in`.ceeq.mercury.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

	private lateinit var viewModel: MainViewModel
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		val postAdapter = PostAdapter()
		binding.recyclerView.adapter = postAdapter
		binding.recyclerView.layoutManager = LinearLayoutManager(this)
		binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

		viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

		viewModel.posts.observe(this, Observer {
			postAdapter.submitList(it)
		})

		viewModel.getPosts()
	}
}
