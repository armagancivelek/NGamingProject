package com.schwagersoft.ngamingcase.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.schwagersoft.ngamingcase.BuildConfig
import com.schwagersoft.ngamingcase.R
import com.schwagersoft.ngamingcase.data.PostItem
import com.schwagersoft.ngamingcase.databinding.ItemPostBinding

class PostAdapter(
    private val onItemClick: (PostItem) -> Unit
) : ListAdapter<PostItem, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(post: PostItem) {
            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body
            binding.ivAvatar.load("${BuildConfig.IMG_BASE_URL}?random=${post.id}") {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                error(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<PostItem>() {
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem == newItem
        }
    }
}
