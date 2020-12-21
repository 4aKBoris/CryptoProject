@file:Suppress("DEPRECATION", "PackageName")

package com.example.cryptoproject.Fragments.SettingsFragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cryptoproject.CustomView.CustomSwitch
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.*

class OtherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_other_settings, container, false)

        sp = getDefaultSharedPreferences(view.context)

        FlagSalt = view.findViewById(R.id.salt)
        TextSecondPassword = view.findViewById(R.id.second_password_text)
        TextDeleteFile = view.findViewById(R.id.delete_file_text)
        SecondPassword = view.findViewById(R.id.second_password)
        DeleteFileSwitch = view.findViewById(R.id.delete_file)
        PasswordFlagSwitch = view.findViewById(R.id.password_flag)
        TextPasswordFlag = view.findViewById(R.id.password_flag_text)
        CipherPasswordSwitch = view.findViewById(R.id.cipherPassword)
        TextCipherPassword = view.findViewById(R.id.cipherPassword_text)


        SecondPassword.setOnCheckedChangeListener { _, isChecked ->
            second_password = isChecked
            TextSecondPassword.text = yesNo[second_password]
        }

        DeleteFileSwitch.setOnCheckedChangeListener { _, isChecked ->
            delete_file = isChecked
            TextDeleteFile.text = yesNo[delete_file]
        }

        PasswordFlagSwitch.setOnCheckedChangeListener { _, isChecked ->
            password_flag = isChecked
            TextPasswordFlag.text = yesNo[password_flag]
        }

        CipherPasswordSwitch.setOnCheckedChangeListener { _, isChecked ->
            cipher_password = isChecked
            TextCipherPassword.text = yesNo[cipher_password]
        }

        setSettings()

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setSettings() {
        second_password = sp.getBoolean(SecordPassword, NOT)
        delete_file = sp.getBoolean(DeleteFile, NOT)
        password_flag = sp.getBoolean(PasswordFlag, NOT)
        cipher_password = sp.getBoolean(CipherPassword, NOT)


        CipherPasswordSwitch.isChecked = cipher_password
        TextCipherPassword.text = yesNo[cipher_password]
        FlagSalt.setFlag(sp.getBoolean(Salt, NOT))
        SecondPassword.isChecked = second_password
        TextSecondPassword.text = yesNo[second_password]
        DeleteFileSwitch.isChecked = delete_file
        TextDeleteFile.text = yesNo[delete_file]
        TextPasswordFlag.text = yesNo[password_flag]
        PasswordFlagSwitch.isChecked = password_flag
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        val editor = sp.edit()
        editor.putBoolean(Salt, FlagSalt.getFlag())
        editor.putBoolean(SecordPassword, second_password)
        editor.putBoolean(DeleteFile, delete_file)
        editor.putBoolean(PasswordFlag, password_flag)
        editor.putBoolean(CipherPassword, cipher_password)
        editor.apply()
    }

    companion object {
        private lateinit var sp: SharedPreferences
        private var second_password = false
        private var delete_file = false
        private var password_flag = false
        private var cipher_password = false

        private val yesNo = mapOf(Pair(true, "Да"), Pair(false, "Нет"))

        private lateinit var TextSecondPassword: TextView
        private lateinit var TextDeleteFile: TextView
        private lateinit var FlagSalt: CustomSwitch

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var SecondPassword: Switch

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var PasswordFlagSwitch: Switch
        private lateinit var TextPasswordFlag: TextView

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var DeleteFileSwitch: Switch

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var CipherPasswordSwitch: Switch
        private lateinit var TextCipherPassword: TextView
    }
}