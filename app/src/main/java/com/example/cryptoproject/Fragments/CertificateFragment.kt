@file:Suppress("PackageName")

package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.cryptoproject.Function.Certificate
import com.example.cryptoproject.R

@Suppress("DEPRECATION")
class CertificateFragment : Fragment() {

    @SuppressLint("NewApi", "HardwareIds", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_certificate, container, false)
        val Organization = view.findViewById<EditText>(R.id.organization)
        val Password = view.findViewById<EditText>(R.id.cerfpassword)
        val ButtonCreate = view.findViewById<Button>(R.id.createCertificate)
        ButtonCreate.setOnClickListener {
            val cerf = Certificate(
                Organization.text.toString(),
                Settings.Secure.getString(context!!.contentResolver, Settings.Secure.ANDROID_ID),
                Password.text.toString()
            )
            cerf.CreateCertificate()
        }
        return view
    }
}