package com.example.motivateme

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motivateme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Initialize binding object
    private lateinit var binding : ActivityMainBinding;
    private lateinit var imm : InputMethodManager;

    // Override lifecycle onCreate to initialize important items for the code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Layout inflation (with view binding)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // set root of layout as content view

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize imm
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;

        // Click listener
        binding.mainBtnUpdate.setOnClickListener {
            val name = getName();
            if (name == "") return@setOnClickListener
            else displayMessage(name);
        }

        // Check if a bundle is present
        if (savedInstanceState != null) {
            // Get data from bundle
            binding.mainTextViewMessage.text = savedInstanceState.getString("message");
        }
    }

    // Override lifecycle onSavedInstanceState to save data on screen rotation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Put data into bundle
        outState.putString("message", binding.mainTextViewMessage.text.toString());
    }

    // Get name from EditText
    private fun getName(): String {
        val name = binding.mainEditTextName.text.toString().trim();
        return name.ifEmpty {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return ""
        }
    }

    // Display personalized message
    private fun displayMessage(name : String) {
        val message = getRandomMessage();
        binding.mainTextViewMessage.text = String.format(resources.getString(R.string.message), name, message);
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
        imm.hideSoftInputFromWindow(binding.mainEditTextName.windowToken, 0);
    }
}