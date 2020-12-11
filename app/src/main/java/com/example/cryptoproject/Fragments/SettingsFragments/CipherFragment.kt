@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Fragments.SettingsFragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.*

class CipherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cipher_settings, container, false)

        sp = getDefaultSharedPreferences(view.context)

        TextCipher = view.findViewById(R.id.cipher_alg_text)
        TextBCM = view.findViewById(R.id.cipher_bcm_text)
        TextPadding = view.findViewById(R.id.cipher_padding_text)
        LinearLayoutCBC = view.findViewById(R.id.cipher_bcm)
        LinearLayoutPadding = view.findViewById(R.id.cipher_padding)
        CipherCnt = view.findViewById(R.id.cipher_count)
        KeySizePicker = view.findViewById(R.id.key_size)
        KeySizeMin = view.findViewById(R.id.min_key_size)
        KeySizeMax = view.findViewById(R.id.max_key_size)

        view.findViewById<View>(R.id.cipher_alg).setOnClickListener {
            list = cipherAlg
            AlertDialog.Builder(view.context).setTitle(AlgCipher)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list),
                    CipherAlg
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        CipherCnt.minValue = ONE
        CipherCnt.maxValue = 127

        CipherCnt.setOnValueChangedListener { _, _, newVal ->
            cipher_count = newVal
        }

        KeySizePicker.setOnValueChangedListener { _, _, newVal ->
            val step = keySize[cipher_alg]
            val values = mutableListOf<Int>()
            for (i in step!![0]..step[2] step (step[1])) values.add(i)
            key_size = values[newVal]
        }

        view.findViewById<View>(R.id.cipher_bcm).setOnClickListener {
            list = cipherBcm
            if (cipher_alg !in cipher64) {
                val l = list.toMutableList()
                l.remove(GOFB)
                list = l.toList()
            }
            if (cipher_alg !in cipher128) {
                val l = list.toMutableList()
                l.removeAll(cbc128)
                list = l.toList()
            }
            if (cipher_alg == GOST34122015) {
                val l = list.toMutableList()
                l.remove(CTR)
                list = l.toList()
            }
            AlertDialog.Builder(view.context).setTitle(PaddingMode)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list),
                    CipherBCM
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        view.findViewById<View>(R.id.cipher_padding).setOnClickListener {
            list = cipherPadding
            //if (BCM == "CTR") list = listOf("ECB", "CBC")
            if (bcm != ECB && bcm != CBC) {
                val l = list.toMutableList()
                l.remove(WithCTS)
                list = l.toList()
            }
            if (bcm in AEAD) list = listOf(NoPadding)
            AlertDialog.Builder(view.context).setTitle(FillingMode)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list),
                    CipherPadding
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        setSettings()

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setSettings() {
        cipher_alg = sp.getString(CipherAlgorithm, AES)!!
        cipher_count = sp.getInt(CipherCount, 1)
        bcm = sp.getString(BCM, CBC)!!
        padding = sp.getString(Padding, PKCS5Padding)!!
        key_size = sp.getInt(KeySize, keySize.getValue(cipher_alg)[2])

        TextCipher.text = cipher_alg
        TextBCM.text = bcm
        TextPadding.text = padding
        CipherCnt.value = cipher_count
        val step = keySize[cipher_alg]
        val values = mutableListOf<String>()
        for (i in step!![0]..step[2] step (step[1])) values.add(i.toString())
        KeySizePicker.minValue = 0
        KeySizePicker.maxValue = values.size - 1
        KeySizePicker.displayedValues = values.toTypedArray()
        KeySizePicker.value = values.indexOf(key_size.toString())
        KeySizeMax.text =
            "Max = ${(keySize.getValue(cipher_alg)[2].toString())} Байт"
        KeySizeMin.text =
            "Min = ${(keySize.getValue(cipher_alg)[0].toString())} Байт"

        if (cipher_alg in cipherStream) {
            LinearLayoutCBC.visibility = View.GONE
            LinearLayoutPadding.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private val CipherAlg = DialogInterface.OnClickListener { _, which ->
        TextCipher.text = list[which]
        cipher_alg = list[which]
        if (cipher_alg in cipherStream) {
            LinearLayoutCBC.visibility = View.GONE
            LinearLayoutPadding.visibility = View.GONE
        } else {
            LinearLayoutCBC.visibility = View.VISIBLE
            LinearLayoutPadding.visibility = View.VISIBLE
        }
        if ((cipher_alg !in cipher64 && bcm == GOFB) || (cipher_alg !in cipher128 && bcm in cbc128) || cipher_alg == GOST34122015 && bcm == CTR) {
            bcm = CBC
            TextBCM.text = CBC
        }

        val step = keySize[cipher_alg]
        val values = mutableListOf<String>()
        for (i in step!![0]..step[2] step (step[1])) values.add(i.toString())
        if (KeySizePicker.maxValue < values.size) {
            KeySizePicker.displayedValues = values.toTypedArray()
            KeySizePicker.maxValue = values.size - 1
        } else {
            KeySizePicker.maxValue = values.size - 1
            KeySizePicker.displayedValues = values.toTypedArray()
        }
        KeySizePicker.minValue = 0
        KeySizePicker.value = values.size - 1
        key_size = step[2]
        KeySizeMax.text =
            "Max = ${(keySize.getValue(cipher_alg)[2].toString())} Байт"
        KeySizeMin.text =
            "Min = ${(keySize.getValue(cipher_alg)[0].toString())} Байт"
    }

    @SuppressLint("SetTextI18n")
    private val CipherBCM = DialogInterface.OnClickListener { _, which ->
        TextBCM.text = list[which]
        bcm = list[which]
        if (bcm in AEAD) {
            TextPadding.text = NoPadding
            padding = NoPadding
        }
        if (bcm != ECB && bcm != CBC && padding == WithCTS) {
            TextPadding.text = CBC
            padding = CBC
        }
    }

    private val CipherPadding = DialogInterface.OnClickListener { _, which ->
        TextPadding.text = list[which]
        padding = list[which]
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        val editor = sp.edit()
        editor.putString(CipherAlgorithm, cipher_alg)
        editor.putInt(CipherCount, cipher_count)
        editor.putString(bcm, bcm)
        editor.putString(padding, padding)
        editor.apply()
    }


    companion object {
        private lateinit var sp: SharedPreferences
        private lateinit var cipher_alg: String
        private var cipher_count = 1
        private lateinit var bcm: String
        private lateinit var padding: String
        private var key_size = 32
        private lateinit var list: List<String>

        private lateinit var TextCipher: TextView
        private lateinit var TextBCM: TextView
        private lateinit var TextPadding: TextView
        private lateinit var LinearLayoutCBC: LinearLayout
        private lateinit var LinearLayoutPadding: LinearLayout
        private lateinit var CipherCnt: NumberPicker
        private lateinit var KeySizePicker: NumberPicker
        private lateinit var KeySizeMin: TextView
        private lateinit var KeySizeMax: TextView
    }
}