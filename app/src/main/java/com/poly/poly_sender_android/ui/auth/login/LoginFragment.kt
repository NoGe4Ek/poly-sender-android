package com.poly.poly_sender_android.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentLoginBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginNews
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginState
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginWish
import com.poly.poly_sender_android.util.MessageConstants.ERROR_EMPTY_FILL
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

        loginViewModel.obtainWish(LoginWish.TryAutoSignIn)

        binding.buttonLogin.setOnClickListener {
            val login: String = "sargsyan.ee@edu.spbstu.ru"//binding.editTextEmail.string()
            val password: String = "fDq%&?pkk#wt"//binding.editTextPassword.string() //TODO delete test
            when {
                login == "" -> binding.textFieldEmail.error =
                    ERROR_EMPTY_FILL
                password == "" -> binding.textFieldPassword.error =
                    ERROR_EMPTY_FILL
                else -> {
                    loginViewModel.obtainWish(
                        LoginWish.SignIn(
                            login,
                            password
                        )
                    )
                }
            }
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        binding.buttonRestore.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRestoreFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: LoginState) {
        if (state.isLoading) {
            // TODO loading
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