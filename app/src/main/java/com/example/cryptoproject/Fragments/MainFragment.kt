package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.cryptoproject.R


class MainFragment : Fragment() {

    private val LOG_TAG = "LOG"

    @SuppressLint("SetTextI18n", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val ButtonEncrypt =
            view.findViewById<Button>(R.id.encrypt)
        ButtonEncrypt.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_mainFragment_to_encryptFragment)
        }

        val ButtonDecrypt =
            view.findViewById<Button>(R.id.decrypt)
        ButtonDecrypt.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_mainFragment_to_decryptFragment)
        }

        val ButtonTest = view.findViewById<Button>(R.id.test)
        ButtonTest.setOnClickListener {

        }

        val Memory = view.findViewById<TextView>(R.id.memory)
        val statFs = StatFs(Environment.getExternalStorageDirectory().absolutePath)
        val freeSpace = String.format("%.3f", statFs.freeBytes.toDouble() / 1000000000)
        val fullSpace = String.format("%.3f", statFs.totalBytes.toDouble() / 1000000000)
        Memory.text = "Свободно $freeSpace / $fullSpace ГБ"
        return view
    }
}