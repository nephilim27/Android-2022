package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a3track.R
import com.example.a3track.databinding.FragmentEditProfileBinding
import com.example.a3track.databinding.FragmentSettingsBinding
import com.example.a3track.viewModel.ProfileViewModel


class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var save: Button
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var location: EditText
    lateinit var back: ImageView
    lateinit var viewModel: ProfileViewModel


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems()
        registerListeners()
    }

    private fun registerListeners(){
        save.setOnClickListener {
            viewModel.email = email.text.toString()
            viewModel.phone = phone.text.toString()
            viewModel.location = location.text.toString()

            email.text.clear()
            phone.text.clear()
            location.text.clear()
        }

        back.setOnClickListener {
            replaceFragment(ProfileFragment())
        }
    }

    private fun initViewItems(){
        save = binding.saveButton
        email = binding.editEmail
        phone = binding.editPhone
        location = binding.editOffice
        back = binding.backToProfile
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}