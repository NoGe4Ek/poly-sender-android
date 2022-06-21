package com.poly.poly_sender_android.ui.auth.register

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
import com.poly.poly_sender_android.databinding.FragmentRegisterBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.auth.register.mvi.RegisterNews
import com.poly.poly_sender_android.ui.auth.register.mvi.RegisterState
import com.poly.poly_sender_android.ui.auth.register.mvi.RegisterWish
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

        binding.buttonLogin.setOnClickListener {
            //TODO navigate to loginFragment
        }
        binding.buttonRegister.setOnClickListener {
            when {
                binding.textFieldFirstname.editText == null -> binding.textFieldFirstname.error =
                    "This field can't be empty" //TODO export to resource and make constants error
                binding.textFieldLastname.editText == null -> binding.textFieldLastname.error =
                    "This field can't be empty"
                binding.textFieldPatronymic.editText == null -> binding.textFieldPatronymic.error =
                    "This field can't be empty"
                binding.textFieldEmail.editText == null -> binding.textFieldEmail.error =
                    "This field can't be empty"
                binding.menuDepartment.editText == null -> binding.menuDepartment.error =
                    "This field can't be empty"
                binding.menuHighSchool.editText == null -> binding.menuHighSchool.error =
                    "This field can't be empty"
                else -> {
                    registerViewModel.obtainWish(
                        RegisterWish.GetAccess(
                            binding.textFieldFirstname.editText!!.string(),
                            binding.textFieldLastname.editText!!.string(),
                            binding.textFieldPatronymic.editText!!.string(),
                            binding.textFieldEmail.editText!!.string(),
                            binding.menuDepartment.editText!!.string(),
                            binding.menuHighSchool.editText!!.string()
                        )
                    )
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