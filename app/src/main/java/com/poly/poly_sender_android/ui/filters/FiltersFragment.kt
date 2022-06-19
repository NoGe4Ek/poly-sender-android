package com.poly.poly_sender_android.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.poly.poly_sender_android.common.Logger
import com.poly.testwaveaccess.databinding.FragmentUserListBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.UserListAdapter
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FiltersFragment : Fragment(), MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val userListViewModel: FiltersViewModel by viewModels()

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    lateinit var userListRecycler: RecyclerView
    lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        userListRecycler = binding.userList
        userListAdapter = UserListAdapter { user ->
            if (user.isActive == "true") {
                val action = UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(
                    userId = user.id
                )
                view.findNavController().navigate(action)
            } else {
                Snackbar.make((binding.snack as View), "User is inactive at this moment", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        userListRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        userListRecycler.adapter = userListAdapter

        with(userListViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@FiltersFragment)
        }

        userListViewModel.obtainWish(CreationAttributeWish.SmartRefresh)

        binding.refreshUserList.setOnRefreshListener {
            userListViewModel.obtainWish(CreationAttributeWish.RefreshFromNetwork)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationAttributeState) {
        binding.refreshUserList.isRefreshing = state.isLoading

        if (state.users.isNotEmpty()) {
            userListAdapter.submitList(state.users)
        }
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}