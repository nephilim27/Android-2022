package com.example.a3track.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.example.a3track.R
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentProfileBinding
import com.example.a3track.models.UserModel

import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var back: ImageView
    lateinit var email: TextView
    lateinit var phone: TextView
    lateinit var location: TextView
    lateinit var edit: ImageView
    lateinit var name: TextView
    lateinit var pic: ImageView
    lateinit var viewModel: ProfileViewModel
    lateinit var userViewModel: MyUserViewModel

    private lateinit var currentUser: UserModel
    private val globalViewModel: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        val factory = MyUserViewModelFactory(TrackerRepository())
        userViewModel = ViewModelProvider(this, factory)[MyUserViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems()
        registerListeners()

        globalViewModel.getCurrentUserFunc()
        globalViewModel.requestState.observe(viewLifecycleOwner) {
            if(it == RequestState.SUCCESS) {
                Log.d("userLoaded", globalViewModel.getCurrentUser().toString())
                currentUser = globalViewModel.getCurrentUser()!!


                email.text = currentUser.email
                if (currentUser.email.equals(null)){
                    email.text = "not set"
                }

                phone.text = currentUser.phone_number
                if (currentUser.phone_number.equals(null)){
                    phone.text = "not set"
                }

                location.text = currentUser.location
                if (currentUser.location.equals(null)){
                    location.text = "not set"
                }

                name.text = currentUser.first_name + " " + currentUser.last_name

                if(currentUser.image != null){
                    Glide.with(pic).load(currentUser.image).into(pic)
                }
            }
        }
    }

    private fun initViewItems() {
        back = binding.profileBack
        email = binding.email
        phone = binding.phoneNr
        location = binding.location
        edit = binding.editProfile
        name = binding.name
        pic = binding.userPic
    }

    private fun registerListeners(){
        back.setOnClickListener {
            replaceFragment(SettingsFragment())
        }

        edit.setOnClickListener {
            val parsedValue = Gson().toJson(globalViewModel.getCurrentUser()!!, object: TypeToken<UserModel>() {}.type)
            val bundle = Bundle()
            bundle.putString("loggedInUser", parsedValue)
            val fragment = EditProfileFragment()
            fragment.arguments = bundle
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}