package com.anailies.userapp.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.anailies.userapp.R
import com.anailies.userapp.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private val userListViewModel: UserListViewModel by viewModels()
    private lateinit var adapter: UserListAdapter
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        adapter = UserListAdapter(::showDeleteDialog)

        binding = FragmentUserListBinding.inflate(inflater).apply {
            viewModel = userListViewModel
            lifecycleOwner = viewLifecycleOwner
            usersRv.adapter = adapter

            addUserFab.setOnClickListener {
                navigateToAddUserDialog()
            }
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        userListViewModel.usersList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        userListViewModel.deleteUserEvent.observe(viewLifecycleOwner, {
            dialog.dismiss()
        })
    }

    private fun navigateToAddUserDialog() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_userListFragment_to_addUserFragment)
    }

    private fun showDeleteDialog(id: Long) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.delete_user_dialog_title)
                .setPositiveButton(R.string.delete_user_dialog_action_accept) { _, _ ->
                    userListViewModel.deleteUser(id)
                }
                .setNegativeButton(R.string.delete_user_dialog_action_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            dialog = builder.create()
            dialog.show()
        }
    }
}