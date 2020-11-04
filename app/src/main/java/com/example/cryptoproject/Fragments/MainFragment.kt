@file:Suppress("PackageName")

package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.cryptoproject.R


@Suppress("DEPRECATION")
class MainFragment : Fragment() {

    //private val LOG_TAG = "LOG"
    private val FILE_OPEN_CODE = 0
    private lateinit var FILENAME : String

    @SuppressLint("SetTextI18n", "UseRequireInsteadOfGet", "SdCardPath")
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
            val runnable = Runnable {
                /*val url = URL("")
                val input: InputStream = url.openStream()
                val buffer: ByteArray = input.readBytes()
                FileReadWrite().writeFile("", buffer)*/
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "file/*"
                startActivityForResult(intent, FILE_OPEN_CODE)
            }
            val thread = Thread(runnable)
            thread.start()
        }

        val ButtonCertificate = view.findViewById<Button>(R.id.Certificate)
        ButtonCertificate.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_mainFragment_to_certificateFragment)
        }

        val Memory = view.findViewById<TextView>(R.id.memory)
        val statFs = StatFs(Environment.getExternalStorageDirectory().absolutePath)
        val freeSpace = String.format("%.3f", statFs.freeBytes.toDouble() / 1000000000)
        val fullSpace = String.format("%.3f", statFs.totalBytes.toDouble() / 1000000000)
        Memory.text = "Свободно $freeSpace / $fullSpace ГБ"
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FILE_OPEN_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    FILENAME =
                        data?.data?.path!!
                    println(FILENAME)
                }//.replace("/external_files", "/storage/emulated/0")
            }

        }
    }

}