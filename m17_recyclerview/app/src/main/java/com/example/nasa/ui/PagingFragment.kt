package com.example.nasa.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa.R
import com.example.nasa.databinding.FragmentPagingBinding
import com.example.nasa.domain.Helper
import com.example.nasa.domain.Navigation
import com.example.nasa.ui.adapters.PhotoAdapterPaging
import com.example.nasa.ui.adapters.TasksLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingFragment : Fragment() {

    private lateinit var binding: FragmentPagingBinding
    private val viewModel: PagingViewModel by viewModels()
    private lateinit var mAdapterPaging: PhotoAdapterPaging

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(binding.root)

        initView()


    }

    private fun initView() = with(binding) {

        mAdapterPaging = PhotoAdapterPaging { url ->
            Navigation.actionPagingFragment2ToFullPhotoFragment2(
                findNavController(),
                url
            )
        }

        rvPhoto.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        rvPhoto.adapter = mAdapterPaging.withLoadStateHeaderAndFooter(
            header = TasksLoadStateAdapter(), footer = TasksLoadStateAdapter()
        )

        mAdapterPaging.addLoadStateListener { state ->
            rvPhoto.isVisible = state.refresh != LoadState.Loading
            progress.isVisible = state.refresh == LoadState.Loading
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.data.collectLatest(mAdapterPaging::submitData)

            }
        }

    }


}
