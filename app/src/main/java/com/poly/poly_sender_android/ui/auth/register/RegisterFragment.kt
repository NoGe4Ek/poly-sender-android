package com.poly.poly_sender_android.ui.auth.register

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
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentRegisterBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.auth.login.LoginFragmentDirections
import com.poly.poly_sender_android.ui.auth.register.mvi.RegisterNews
import com.poly.poly_sender_android.ui.auth.register.mvi.RegisterState
import com.poly.poly_sender_android.ui.auth.register.mvi.RegisterWish
import com.poly.poly_sender_android.util.ErrorConstants.EMPTY_FILL_ERROR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment(), MviView<RegisterState, RegisterNews> {

    @Inject
    lateinit var logger: Logger

    private val registerViewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(registerViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@RegisterFragment)
        }

        binding.apply {
            buttonLogin.setOnClickListener {
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
            }
            buttonRegister.setOnClickListener {

                when {
                    textFieldFirstname.editText == null -> textFieldFirstname.error =
                        EMPTY_FILL_ERROR
                    textFieldLastname.editText == null -> textFieldLastname.error =
                        EMPTY_FILL_ERROR
                    textFieldPatronymic.editText == null -> textFieldPatronymic.error =
                        EMPTY_FILL_ERROR
                    textFieldEmail.editText == null -> textFieldEmail.error =
                        EMPTY_FILL_ERROR
                    menuDepartment.editText == null -> menuDepartment.error =
                        EMPTY_FILL_ERROR
                    menuHighSchool.editText == null -> menuHighSchool.error =
                        EMPTY_FILL_ERROR
                    else -> {
                        registerViewModel.obtainWish(
                            RegisterWish.GetAccess(
                                textFieldFirstname.editText!!.string(),
                                textFieldLastname.editText!!.string(),
                                textFieldPatronymic.editText!!.string(),
                                textFieldEmail.editText!!.string(),
                                menuDepartment.editText!!.string(),
                                menuHighSchool.editText!!.string()
                            )
                        )
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: RegisterState) {
        if (state.isLoading) {
            //TODO
        }
    }

    override fun renderNews(new: RegisterNews) {
        when (new) {
            is RegisterNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}