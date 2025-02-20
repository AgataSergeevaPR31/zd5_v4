package com.example.hateandroidstudio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LBooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private lateinit var recyclerView: RecyclerView
    private lateinit var booksAdapter: BooksAdapter
    private var booksList: List<Books> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Handle any arguments if needed
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_l_books, container, false)


        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadBooks()

        val buttonAdd = view.findViewById<ImageButton>(R.id.button_add)
        buttonAdd.setOnClickListener {
            val addBooksFragment = AddBooksFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, addBooksFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun loadBooks() {
        lifecycleScope.launch {
            val bookDao = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "books.db").build().bookDao()
            booksList = bookDao.getAllBooks()
            booksAdapter = BooksAdapter(booksList)
            recyclerView.adapter = booksAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}