package com.example.motivateme

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var nameView : EditText;
    private lateinit var messageView : TextView;
    private lateinit var res : Resources;

    // Override lifecycle onCreate to initialize important items for the code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Layout inflation
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Define private references
        nameView = findViewById(R.id.mainEditTextName);
        messageView = findViewById(R.id.mainTextViewMessage);
        res = resources;

        // Button reference and click listener
        // Kotlin: val is a constant variable; var is a mutable variable
        val updateButton : Button = findViewById(R.id.mainBtnUpdate);

        updateButton.setOnClickListener {
            val name = getName();
            displayMessage(name);
        }
    }

    // Get name from EditText
    private fun getName(): String {
        val name = nameView.text.toString();

        // Return "User" as the default value if name is empty (returns name otherwise)
        return name.trim().ifEmpty {
            "User";
        }
    }

    // Display personalized message
    private fun displayMessage(name : String) {
        val message = getRandomMessage();
        messageView.text = String.format(res.getString(R.string.message), name, message);
        hideKeyboard();
    }

    // Select random motivational message
    private fun getRandomMessage() : String {
        val messages = listOf("You can do hard things.",
            "You are stronger than you think.",
            "Mistakes are proof you are trying.",
            "You are a brave lion.", "Be kind to yourself.",
            "Be silly. Be honest. Be kind.",
            "Keep going. You'll get it.",
            "You are cute and you deserve a break.",
            "Enjoy the little things.",
            "Life is too short to sit and be sad.",
            "Actually, you can.",
            "Be the person who decided to go for it.",
            "Stop being so hard on yourself.",
            "Throw kindness around like confetti.",
            "Be pretty – pretty kind, pretty confident, pretty smart, pretty awesome."
            )
        val messagesLength = messages.size;
        val randomIndex = (0..<messagesLength).random();
        return messages[randomIndex];
    }

    // Hide keyboard using an Input Method Manager
    private fun hideKeyboard() {
        // Get service from context and cast as InputMethodManager type
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        // All views have access to the current windowToken; here, we are using nameView, but any of the views would work
        inputManager.hideSoftInputFromWindow(nameView.windowToken, 0);
    }
}