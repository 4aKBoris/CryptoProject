package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cryptoproject.R

class TestFragment : Fragment() {

    @SuppressLint("CutPasteId", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test, container, false)
        val Text1 = TextView(view.context)
        val Text2 = TextView(view.context)
        Text1.id = 1
        Text2.id = 2
        val Linear = view.findViewById<LinearLayout>(R.id.layout)
        Linear.addView(Text1)
        Linear.addView(Text2)
        Text1.text = "Привет"
        Text2.text = "Привет"
        Button1 = view.findViewById(R.id.test_button)

        Button1.setOnClickListener {
            println(Text1.id)
            Text2.id = 3
            println(Text2.id)
            Text1.text = "Нет"
            Text2.text = "НетНет"
        }

        return view
    }

    companion object {
        //private lateinit var Text1: TextView
        //private lateinit var Text2: TextView
        private lateinit var Button1: Button
    }
}