@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Fragments.SettingsFragments

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.preference.PreferenceManager.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.cryptoproject.R
import com.example.cryptoproject.SettingsActivity
import com.example.cryptoproject.Сonstants.*

class SignatureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signature_settings, container, false)

        sp = getDefaultSharedPreferences(view.context)

        SignatureAlgorithm = view.findViewById(R.id.sign_alg_text)

        view.findViewById<View>(R.id.signature).setOnClickListener {
            AlertDialog.Builder(view.context).setTitle(Sign).setCancelable(false).setAdapter(
                ArrayAdapter(view.context, android.R.layout.simple_list_item_1, sign),
                SignatureAlg
            )
                .setNegativeButton(Cansel) { dialog, _ ->
                    dialog.cancel()
                }.create().show()
        }

        setSettings()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        val editor = sp.edit()
        editor.putString(Signature, signature)
        editor.apply()
    }

    private fun setSettings() {
        signature = sp.getString(Signature, NotUse)!!
        SignatureAlgorithm.text = signature
    }

    private val SignatureAlg = DialogInterface.OnClickListener { _, which ->
        SignatureAlgorithm.text = sign[which]
        signature = sign[which]
    }

    companion object {
        private lateinit var sp: SharedPreferences
        private lateinit var signature: String
        private lateinit var SignatureAlgorithm: TextView
    }
}