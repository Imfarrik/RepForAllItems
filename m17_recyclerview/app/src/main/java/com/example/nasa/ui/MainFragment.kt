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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa.R
import com.example.nasa.databinding.FragmentMainBinding
import com.example.nasa.domain.Helper
import com.example.nasa.domain.Navigation
import com.example.nasa.network.State
import com.example.nasa.ui.adapters.PhotoAdapter
import com.example.nasa.ui.adapters.PhotoAdapterPaging
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(binding.root)

        initView()
    }

    private fun initView() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect {
                    when (it) {
                        is State.ServerError -> {
                            progress.isVisible = false
                            Helper.setToast(requireContext(), it.message)
                        }
                        is State.Success -> {
                            progress.isVisible = false
                            rvPhoto.isVisible = true

                            mAdapter = PhotoAdapter(it.onSuccess.photos) { url ->
                                Navigation.actionMainFragmentToFullPhotoFragment(
                                    findNavController(),
                                    url
                                )
                            }

                            rvPhoto.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )

                            rvPhoto.adapter = mAdapter
                        }
                        State.Loading -> {
                            rvPhoto.isVisible = false
                            progress.isVisible = true
                        }
                    }
                }

            }

        }


    }

}