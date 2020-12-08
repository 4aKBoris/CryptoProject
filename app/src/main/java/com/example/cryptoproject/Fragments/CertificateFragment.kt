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
import android.widget.Toast
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
        val CommonName = view.findViewById<EditText>(R.id.common_name)
        val OrganizationalUnit = view.findViewById<EditText>(R.id.organizational_unit)
        val OrganizationName = view.findViewById<EditText>(R.id.organization_name)
        val Locality = view.findViewById<EditText>(R.id.locality)
        val State = view.findViewById<EditText>(R.id.state)
        val Country = view.findViewById<EditText>(R.id.country)
        val Password = view.findViewById<EditText>(R.id.cerfpassword)
        val ButtonCreate = view.findViewById<Button>(R.id.createCertificate)
        ButtonCreate.setOnClickListener {
            val reg = Regex("[A-Z]{2}")
            if (reg.containsMatchIn(Country.text.toString())) {
                val cerf = Certificate(
                    CommonName.text.toString(),
                    OrganizationalUnit.text.toString(),
                    OrganizationName.text.toString(),
                    Locality.text.toString(),
                    State.text.toString(),
                    Country.text.toString(),
                    Settings.Secure.getString(context!!.contentResolver,
                        Settings.Secure.ANDROID_ID),
                    Password.text.toString()
                )
                cerf.CreateCertificate()
            }
            else Toast.makeText(context, "Введите код страны правильно!", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }
}