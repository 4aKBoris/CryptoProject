@file:Suppress("DEPRECATION", "PackageName")

package com.example.cryptoproject.Fragments.SettingsFragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cryptoproject.CustomView.CustomSelectAlgrorithm
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.R
import com.example.cryptoproject.SettingsActivity
import com.example.cryptoproject.Сonstants.*

class HashFragment : Fragment() {

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hash_settings, container, false)

        sp = PreferenceManager.getDefaultSharedPreferences(view.context)

        HashView = view.findViewById(R.id.hash_view)
        HashCnt = view.findViewById(R.id.hash_count)
        HashCountValue = view.findViewById(R.id.hash_count_value)
        HashCountEdit = view.findViewById(R.id.hash_count_edit)
        HashButton = view.findViewById(R.id.hash_set_count)

        HashView.setOnClickListener {
            HashView.OnClick(view.context, hashAlg, AlgHash)
        }


        HashCnt.min = ONE
        HashCnt.max = 16383
        HashCnt.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                hash_count = progress
                HashCountValue.text = progress.toString()
            }
        })

        HashButton.setOnClickListener {
            try {
                if (HashCountEdit.text.toString() == "") throw MyException(
                    NotRequirements)
                val k = HashCountEdit.text.toString().toInt()
                if (k <= 0 || k > 16256) throw MyException(NotRequirements)
                else {
                    hash_count = k
                    HashCountValue.text = k.toString()
                    HashCnt.progress = k
                }
            } catch (e: MyException) {
                Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
                HashCountEdit.text.clear()
            }
        }
        setSettings()

        return view
    }

    private fun setSettings() {
        hash_count = sp.getInt(HashCount, ONE)
        HashView.setSelectText(sp.getString(HashAlgorithm, SHA256)!!)
        HashCountValue.text = hash_count.toString()
        HashCnt.progress = hash_count
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        val editor = sp.edit()
        editor.putString(HashAlgorithm, HashView.text)
        editor.putInt(HashCount, hash_count)
        editor.apply()
    }

    companion object {
        private lateinit var sp: SharedPreferences
        private var hash_count = 1

        private lateinit var HashView: CustomSelectAlgrorithm
        private lateinit var HashCnt: SeekBar
        private lateinit var HashCountValue: TextView
        private lateinit var HashCountEdit: EditText
        private lateinit var HashButton: Button

    }
}