package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.a3track.R
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentEditProfileBinding
import com.example.a3track.model.UpdateProfile
import com.example.a3track.models.UserModel
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var save: Button
    lateinit var imageUrl: EditText
    lateinit var phone: EditText
    lateinit var location: EditText
    lateinit var back: ImageView
    lateinit var viewModel: ProfileViewModel
    lateinit var userListViewModel: UserListViewModel

    private lateinit var currentUser: UserModel


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
        val factory = UserListViewModelFactory(TrackerRepository())
        userListViewModel = ViewModelProvider(this, factory)[UserListViewModel::class.java]
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parsedValue = arguments?.getString("loggedInUser")
        currentUser = Gson().fromJson(parsedValue!!, object : TypeToken<UserModel>() {}.type)

        initViewItems()
        registerListeners()
    }

    private fun registerListeners(){
        save.setOnClickListener {
            val updateProfileRequest = UpdateProfile(
                location = if(location.text.toString() != "") location.text.toString() else currentUser.location!!,
                phone_number = if(phone.text.toString() != "") phone.text.toString() else currentUser.phone_number!!,
                image = if (imageUrl.text.toString() != "") imageUrl.text.toString() else currentUser.image!!
            )

            userListViewModel.updateProfile(updateProfileRequest)
            userListViewModel.updateProfileState.observe(viewLifecycleOwner) {
                if (it == RequestState.SUCCESS) {
                    Snackbar.make(
                        requireView(),
                        "Profile updated successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    replaceFragment(ProfileFragment())
                } else {
                    Snackbar.make(requireView(), "Profile update failed", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            imageUrl.text.clear()
            phone.text.clear()
            location.text.clear()
        }

        back.setOnClickListener {
            replaceFragment(ProfileFragment())
        }
    }

    private fun initViewItems(){
        save = binding.saveButton
        imageUrl = binding.editImage
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