package com.example.hateandroidstudio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var db: AppDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "user-db").build()

        val roleSpinner: Spinner = view.findViewById(R.id.roleSpinner)
        val roles = arrayOf("Библиотекарь", "Преподаватель", "Студент")
        roleSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roles)

        view.findViewById<Button>(R.id.registerButton).setOnClickListener {
            val lastName = view.findViewById<EditText>(R.id.lastNameEditText).text.toString()
            val firstName = view.findViewById<EditText>(R.id.firstNameEditText).text.toString()
            val email = view.findViewById<EditText>(R.id.emailEditText).text.toString()
            val login = view.findViewById<EditText>(R.id.loginEditText).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordEditText).text.toString()
            val role = roleSpinner.selectedItem.toString()

            val user = User(lastName = lastName, firstName = firstName, email = email, login = login, password = password, role = role)
            lifecycleScope.launch {
                db.userDao().insert(user)
                Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}