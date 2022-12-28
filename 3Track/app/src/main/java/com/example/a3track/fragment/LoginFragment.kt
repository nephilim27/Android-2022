package com.example.a3track.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3track.MyApplication
import com.example.a3track.R
import com.example.a3track.model.LoginRequest
import com.example.a3track.model.LoginResult
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.LoginViewModel
import com.example.a3track.viewModel.LoginViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editText1: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(TrackerRepository())
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText1 = view.findViewById(R.id.editTextTextEmailAddress)
        val editText2: EditText = view.findViewById(R.id.editTextTextPassword)
        val button: Button = view.findViewById(R.id.signInButton)

        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (!prefs.getString("email", "").equals("")) {
            editText1.setText(prefs.getString("email", ""))
        }

        button.setOnClickListener {
            val email = editText1.text.toString().trim()
            val password = editText2.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this.requireContext(),
                    "Please, enter your email and password",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                loginViewModel.login(LoginRequest(email, password))
            }
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            // Save data to preferences
            if( it == LoginResult.INVALID_CREDENTIALS){
                Toast.makeText(
                    this.requireContext(),
                    "Invalid credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
            if ( it == LoginResult.SUCCESS ) {
                val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val edit = prefs.edit()
                edit.putString("token", MyApplication.token)
                edit.putLong("deadline", MyApplication.deadline)
                edit.putString("email", editText1.text.toString())
                edit.apply()
                Log.d("login", "Login")
                replaceFragment(ActivitiesFragment())
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}