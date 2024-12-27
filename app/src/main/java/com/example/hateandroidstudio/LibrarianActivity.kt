package com.example.hateandroidstudio

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LibrarianActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_librarian)

        val buttonBooks = findViewById<ImageButton>(R.id.button_books)
        val buttonReaders = findViewById<ImageButton>(R.id.button_readers)
        val buttonTeachers = findViewById<ImageButton>(R.id.button_teachers)
        val buttonStudents = findViewById<ImageButton>(R.id.button_students)
        val buttonReservations = findViewById<ImageButton>(R.id.button_reservations)
        val buttonExit = findViewById<ImageButton>(R.id.button_exit)
        val buttonAdd = findViewById<ImageButton>(R.id.button_add)

        buttonBooks.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LBooksFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonReaders.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LReadersFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonTeachers.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LTeachersFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonStudents.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LStudentsFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonReservations.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LReservationFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonExit.setOnClickListener {
            // Переход на экран RegAuto
            startActivity(Intent(this, RegAuto::class.java))
        }

    }
}