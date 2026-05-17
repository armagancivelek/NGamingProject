package com.schwagersoft.ngamingcase.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.schwagersoft.ngamingcase.BuildConfig
import com.schwagersoft.ngamingcase.R
import com.schwagersoft.ngamingcase.databinding.FragmentDetailBinding
import com.schwagersoft.ngamingcase.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by activityViewModels()

    private var postId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postId = arguments?.getInt("postId") ?: -1

        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val post = viewModel.getPostById(postId)
        post?.let {
            binding.etTitle.setText(it.title)
            binding.etBody.setText(it.body)
            binding.ivDetailAvatar.load("${BuildConfig.IMG_BASE_URL}?random=${it.id}") {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                error(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())
            }
        }

        binding.btnSave.setOnClickListener {
            val newTitle = binding.etTitle.text.toString().trim()
            val newBody = binding.etBody.text.toString().trim()

            if (newTitle.isNotEmpty() && newBody.isNotEmpty()) {
                viewModel.updatePost(postId, newTitle, newBody)
                Snackbar.make(binding.root, R.string.post_updated, Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
