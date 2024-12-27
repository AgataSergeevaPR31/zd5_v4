package com.example.hateandroidstudio

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBooksFragment : Fragment() {
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

    private lateinit var titleTextView: TextView
    private lateinit var titleEditText: EditText
    private lateinit var sectionEditText: EditText
    private lateinit var daysEditText: EditText
    private lateinit var electronicCheckBox: CheckBox
    private lateinit var coverImageView: ImageView
    private lateinit var authorTextView: TextView
    private lateinit var saveButton: Button
    private lateinit var searchButton: Button
    private lateinit var database: AppDatabase
    private lateinit var api: OpenLibraryApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_books, container, false)

        titleTextView = view.findViewById(R.id.titleTextView)
        sectionEditText = view.findViewById(R.id.sectionEditText)
        titleEditText=view.findViewById(R.id.titleEditText)
        daysEditText = view.findViewById(R.id.daysEditText)
        electronicCheckBox = view.findViewById(R.id.electronicCheckBox)
        coverImageView = view.findViewById(R.id.coverImageView)
        authorTextView = view.findViewById(R.id.authorTextView)
        saveButton = view.findViewById(R.id.saveButton)
        searchButton = view.findViewById(R.id.searchButton)

        database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "books.db").build()
        api = Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryApi::class.java)

        // Set listeners for buttons
        saveButton.setOnClickListener { saveBook() }
        searchButton.setOnClickListener { fetchBookInfo() }

        return view
    }

    private fun saveBook() {
        val title = titleTextView.text.toString() // Corrected from titleEditText
        val author = authorTextView.text.toString().removePrefix("Author(s): ") // Extract author name
        val section = sectionEditText.text.toString()
        val maxDays = daysEditText.text.toString().toIntOrNull() ?: 0
        val isElectronic = electronicCheckBox.isChecked

        // Create a Book object
        val book = Books(title = title, author = author, coverImage = "", section = section, maxDays = maxDays, isElectronic = isElectronic)

        lifecycleScope.launch {
            val bookDao = database.bookDao()
            bookDao.insertBook(book)
        }
    }

    private fun fetchBookInfo() {
        if (titleEditText.text.toString().isNotEmpty()) {
            val title = titleEditText.text.toString()
            val url = "https://www.omdbapi.com/?apikey=8424b5c9&t=${title.replace(" ", "+")}"

            val queue = Volley.newRequestQueue(requireContext())
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val obj = JSONObject(response)
                    if (obj.getString("Response") == "True") {
                        val movieTitle = obj.getString("Title")
                        val authors = obj.getString("Director") // Using Director as the author
                        val coverUrl = obj.optString("Poster", null)

                        titleTextView.text = movieTitle
                        authorTextView.text = "Director: $authors"

                        // Load the cover image into the ImageView
                        if (coverUrl.isNotEmpty()) {
                            Glide.with(requireContext())
                                .load(coverUrl)
                                .into(coverImageView)
                        } else {
                            coverImageView.setImageResource(R.drawable.zxc)
                        }
                    } else {
                        titleTextView.text = "No movies found"
                        authorTextView.text = ""
                        coverImageView.setImageResource(R.drawable.zxc)
                    }
                },
                { error ->
                    titleTextView.text = "Error fetching data"
                    authorTextView.text = ""
                    coverImageView.setImageResource(R.drawable.zxc)
                }
            )

            queue.add(stringRequest)
        } else {
            Toast.makeText(
                requireContext(),
                "Please enter the title of the movie!",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}