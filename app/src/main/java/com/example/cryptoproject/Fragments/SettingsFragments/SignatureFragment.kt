@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Fragments.SettingsFragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoproject.CustomView.CustomSelectAlgrorithm
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.NotUse
import com.example.cryptoproject.Сonstants.Signature
import com.example.cryptoproject.Сonstants.sign

class SignatureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signature_settings, container, false)

        sp = getDefaultSharedPreferences(view.context)

        SignatureView = view.findViewById(R.id.signature)
        SignatureView.setOnClickListener {
            SignatureView.OnClick(view.context, sign, Sign)
        }

        setSettings()
        return view
    }

    override fun onPause() {
        super.onPause()
        val editor = sp.edit()
        editor.putString(Signature, SignatureView.text)
        editor.apply()
    }

    private fun setSettings() {
        SignatureView.setSelectText(sp.getString(Signature, NotUse)!!)
    }

    companion object {
        private const val Sign = "Цифровая подпись"

        private lateinit var sp: SharedPreferences
        private lateinit var SignatureView: CustomSelectAlgrorithm
    }
}