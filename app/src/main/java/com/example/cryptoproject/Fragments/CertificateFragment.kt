@file:Suppress("PackageName")

package com.example.cryptoproject.Fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyStore

class CertificateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_certificate, container, false)

        ButtonCertificate = view.findViewById(R.id.Certificate)
        ButtonCertificate.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_certificateFragment_to_certificateCreateFragment)
        }

        PasswordSee = view.findViewById(R.id.password_see)
        PasswordSee.setOnClickListener {
            if (flag_see) PasswordCertificateExport.transformationMethod =
                PasswordTransformationMethod.getInstance()
            else PasswordCertificateExport.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            flag_see = !flag_see
        }

        CertExport = view.findViewById(R.id.cert_export)
        CertExportText = view.findViewById(R.id.cert_export_text)

        CertExport.setOnClickListener {
            list = sign.filter { it != NotUse }
            AlertDialog.Builder(view.context).setTitle(CertSelect)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list),
                    CertificateExport
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        PasswordCertificateExport = view.findViewById(R.id.password_certificate_export)
        ButtonCertificateExport = view.findViewById(R.id.certificate_export)
        ButtonCertificateExport.setOnClickListener {
            try {
                val password = PasswordCertificateExport.text.toString()
                if (password == "") throw MyException(EnterPasswordKeyStore)
                if (sign_alg == "") throw MyException(SelectAlg)
                val certificateOutputStream =
                    FileOutputStream(CertificatesPath + "my_cert_$sign_alg.cer")
                val keyStoreData =
                    FileInputStream(PATH_KEY_STORE)
                val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
                keyStore.load(keyStoreData, password.toCharArray())
                val certificate = keyStore.getCertificate(sign_alg)
                certificateOutputStream.write(certificate.encoded)
                certificateOutputStream.close()
                Toast.makeText(context, CerfExport, Toast.LENGTH_SHORT).show()
            } catch (e: MyException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private val CertificateExport = DialogInterface.OnClickListener { _, which ->
        sign_alg = list[which]
    }

    companion object {
        private var sign_alg = ""
        private var flag_see = false

        private lateinit var list: List<String>
        private lateinit var ButtonCertificate: Button
        private lateinit var ButtonCertificateExport: Button
        private lateinit var PasswordCertificateExport: EditText
        private lateinit var CertExport: View
        private lateinit var CertExportText: TextView
        private lateinit var PasswordSee: Button
    }
}