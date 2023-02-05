package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.a3track.MyApplication
import com.example.a3track.R
import com.example.a3track.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewProfile: Button
    private lateinit var signOutButton: Button


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
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems(view)
        registerListeners()
    }

    private fun initViewItems(view: View){
        viewProfile = binding.viewProfile
        signOutButton = binding.signoutButton
    }

    private fun registerListeners(){
        viewProfile.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

        signOutButton.setOnClickListener {
            MyApplication.token = ""
            MyApplication.deadline = 0L
            MyApplication.email = ""
            MyApplication.id = null
            replaceFragment(LoginFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}