package com.anailies.userapp.ui.adduser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.anailies.userapp.R
import com.anailies.userapp.databinding.FragmentAddUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragment : DialogFragment() {
    private lateinit var binding: FragmentAddUserBinding
    private val addUserViewModel: AddUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentAddUserBinding.inflate(inflater).apply {
            viewModel = addUserViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        observeViewModel()

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogFragment
    }

    private fun observeViewModel() {
        addUserViewModel.addUserSuccess.observe(viewLifecycleOwner, {
            navigateToUserList()
        })
    }

    private fun navigateToUserList() {
        NavHostFragment.findNavController(this).navigate(R.id.action_addUserFragment_to_userListFragment)
    }
}