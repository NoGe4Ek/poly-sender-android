package com.poly.poly_sender_android.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentLoginBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginNews
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginState
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), MviView<LoginState, LoginNews> {

    @Inject
    lateinit var logger: Logger

    private val loginViewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(loginViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@LoginFragment)
        }

        binding.buttonLogin.setOnClickListener {
            when {
                binding.textFieldEmail.editText == null -> binding.textFieldEmail.error =
                    "This field can't be empty" //TODO export to resource and make constants error
                binding.textFieldPassword.editText == null -> binding.textFieldPassword.error =
                    "This field can't be empty"
                else -> {
                    loginViewModel.obtainWish(
                        LoginWish.SignIn(
                            binding.textFieldEmail.editText!!.string(),
                            binding.textFieldPassword.editText!!.string()
                        )
                    )
                }
            }
        }
        binding.buttonRegister.setOnClickListener {
            //TODO
        }
        binding.buttonRestore.setOnClickListener {
            //TODO
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: LoginState) {
        if (state.isLoading) {
            //TODO
        }

        binding.textFieldPassword.editText?.setText(state.password)
    }

    override fun renderNews(new: LoginNews) {
        when (new) {
            is LoginNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}