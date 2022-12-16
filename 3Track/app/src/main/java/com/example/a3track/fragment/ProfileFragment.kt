package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.a3track.MyApplication
import com.example.a3track.MyApplication.Companion.email
import com.example.a3track.R
import com.example.a3track.api.RetrofitInstance
import com.example.a3track.databinding.FragmentProfileBinding

import com.example.a3track.model.User
import com.example.a3track.viewModel.ProfileViewModel

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var back: ImageView
    lateinit var email: TextView
    lateinit var phone: TextView
    lateinit var location: TextView
    lateinit var edit: ImageView
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems()
        registerListeners()
        setData()

    }

    private fun setData(){
        email.text = viewModel.email
        phone.text = viewModel.phone
        location.text = viewModel.location
    }

    private fun initViewItems() {
        back = binding.profileBack
        email = binding.email
        phone = binding.phoneNr
        location = binding.location
        edit = binding.editProfile
    }

    private fun registerListeners(){
        back.setOnClickListener {
            replaceFragment(SettingsFragment())
        }

        edit.setOnClickListener {
            replaceFragment(EditProfileFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}