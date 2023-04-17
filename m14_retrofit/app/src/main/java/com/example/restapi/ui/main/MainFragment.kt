package com.example.restapi.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.restapi.R
import com.example.restapi.databinding.FragmentMainBinding
import com.example.restapi.ui.main.domain.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    val vm: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()


    }

    private fun initView() = with(binding) {

        refresh.setOnClickListener {
            vm.getUser()
        }

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                vm.state.collect {
                    when (it) {
                        State.Success -> {
                            progressCircular.isVisible = false
                            container.isVisible = true
                            getUserInfo()
                        }
                        State.Loading -> {
                            container.isVisible = false
                            progressCircular.isVisible = true
                        }
                        is State.Error -> {
                            container.isVisible = false
                            progressCircular.isVisible = true
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        }

    }

    private fun getUserInfo() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getUser.collect {

                    val info = it?.results?.first()

                    fio.text =
                        "${info?.name?.title ?: ""} ${info?.name?.first ?: ""} ${info?.name?.last ?: ""}"

                    email.text = info?.email ?: ""

                    location.text =
                        "${info?.location?.street?.number ?: ""}, ${info?.location?.street?.name ?: ""}, ${info?.location?.city ?: ""}, ${info?.location?.state ?: ""}, ${info?.location?.country ?: ""}, ${info?.location?.postcode ?: ""}"

                    dob.text = formatDate(info?.dob?.date)

                    registered.text = formatDate(info?.registered?.date)

                    phone.text = info?.phone ?: ""

                    cell.text = info?.cell ?: ""

                    gender.text = info?.gender ?: ""

                    if (info?.picture?.medium != null) {

                        Glide.with(requireContext())
                            .load(info.picture.medium)
                            .into(imageView)
                    }

                }
            }
        }
    }

    private fun formatDate(date: String?): String {
        return if (date != null) {
            val getDate = date.split("T")
            val list = getDate[0].split("-")
            "${list[2]}.${list[1]}.${list[0]}"
        } else {
            ""
        }
    }


}