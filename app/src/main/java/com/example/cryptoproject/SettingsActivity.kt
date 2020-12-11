@file:Suppress("DEPRECATION")

package com.example.cryptoproject

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.Сonstants.*


@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING"
)
class SettingsActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        sp = getDefaultSharedPreferences(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        TextHash = findViewById(R.id.hash_alg_text)
        TextCipher = findViewById(R.id.cipher_alg_text)
        TextBCM = findViewById(R.id.cipher_bcm_text)
        TextPadding = findViewById(R.id.cipher_padding_text)
        LinearLayoutCBC = findViewById(R.id.cipher_bcm)
        LinearLayoutPadding = findViewById(R.id.cipher_padding)
        TextFlagSalt = findViewById(R.id.salt_flag_text)
        TextSecondPassword = findViewById(R.id.second_password_text)
        TextDeleteFile = findViewById(R.id.delete_file_text)
        FlagSalt = findViewById(R.id.salt_flag)
        SecondPassword = findViewById(R.id.second_password)
        DeleteFileSwitch = findViewById(R.id.delete_file)
        HashCnt = findViewById(R.id.hash_count)
        HashCountValue = findViewById(R.id.hash_count_value)
        HashCountEdit = findViewById(R.id.hash_count_edit)
        HashButton = findViewById(R.id.hash_set_count)
        CipherCnt = findViewById(R.id.cipher_count)
        KeySizePicker = findViewById(R.id.key_size)
        KeySizeMin = findViewById(R.id.min_key_size)
        KeySizeMax = findViewById(R.id.max_key_size)
        PasswordFlagSwitch = findViewById(R.id.password_flag)
        TextPasswordFlag = findViewById(R.id.password_flag_text)
        SignatureAlgorithm = findViewById(R.id.sign_alg_text)
        CipherPasswordSwitch = findViewById(R.id.cipherPassword)
        TextCipherPassword = findViewById(R.id.cipherPassword_text)

        findViewById<View>(R.id.hash_alg).setOnClickListener {
            list = hashAlg
            AlertDialog.Builder(this).setTitle(AlgHash)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    HashAlg
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        HashCnt.min = ONE
        HashCnt.max = 16383
        HashCnt.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
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
                if (HashCountEdit.text.toString() == "") throw MyException(NotRequirements)
                val k = HashCountEdit.text.toString().toInt()
                if (k <= 0 || k > 16256) throw MyException(NotRequirements)
                else {
                    hash_count = k
                    HashCountValue.text = k.toString()
                    HashCnt.progress = k
                }
            } catch (e: MyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                HashCountEdit.text.clear()
            }
        }

        findViewById<View>(R.id.cipher_alg).setOnClickListener {
            list = cipherAlg
            AlertDialog.Builder(this).setTitle(AlgCipher)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
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

        findViewById<View>(R.id.cipher_bcm).setOnClickListener {
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
            AlertDialog.Builder(this).setTitle(PaddingMode)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    CipherBCM
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        findViewById<View>(R.id.cipher_padding).setOnClickListener {
            list = cipherPadding
            //if (BCM == "CTR") list = listOf("ECB", "CBC")
            if (bcm != ECB && bcm != CBC) {
                val l = list.toMutableList()
                l.remove(WithCTS)
                list = l.toList()
            }
            if (bcm in AEAD) list = listOf(NoPadding)
            AlertDialog.Builder(this).setTitle(FillingMode)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    CipherPadding
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        findViewById<View>(R.id.signature).setOnClickListener {
            list = sign
            AlertDialog.Builder(this).setTitle(Sign).setCancelable(false).setAdapter(
                ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                SignatureAlg
            )
                .setNegativeButton(Cansel) { dialog, _ ->
                    dialog.cancel()
                }.create().show()
        }



        FlagSalt.setOnCheckedChangeListener { _, isChecked ->
            salt_flag = isChecked
            TextFlagSalt.text = yesNo[salt_flag]
        }

        SecondPassword.setOnCheckedChangeListener { _, isChecked ->
            second_password = isChecked
            TextSecondPassword.text = yesNo[second_password]
        }

        DeleteFileSwitch.setOnCheckedChangeListener { _, isChecked ->
            delete_file = isChecked
            TextFlagSalt.text = yesNo[delete_file]
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

    }

    @SuppressLint("SetTextI18n")
    private fun setSettings() {
        hash_alg = sp.getString(HashAlgorithm, SHA256)!!
        hash_count = sp.getInt(HashCount, ONE)
        cipher_alg = sp.getString(CipherAlgorithm, AES)!!
        cipher_count = sp.getInt(CipherCount, 1)
        bcm = sp.getString(BCM, CBC)!!
        padding = sp.getString(Padding, PKCS5Padding)!!
        salt_flag = sp.getBoolean(Salt, NOT)
        second_password = sp.getBoolean(SecordPassword, NOT)
        delete_file = sp.getBoolean(DeleteFile, NOT)
        key_size = sp.getInt(KeySize, keySize.getValue(cipher_alg)[2])
        password_flag = sp.getBoolean(PasswordFlag, NOT)
        signature = sp.getString(Signature, NotUse)!!
        cipher_password = sp.getBoolean(CipherPassword, NOT)


        CipherPasswordSwitch.isChecked = cipher_password
        TextCipherPassword.text = yesNo[cipher_password]
        SignatureAlgorithm.text = signature
        FlagSalt.isChecked = salt_flag
        TextFlagSalt.text = yesNo[salt_flag]
        SecondPassword.isChecked = second_password
        TextSecondPassword.text = yesNo[second_password]
        DeleteFileSwitch.isChecked = delete_file
        TextDeleteFile.text = yesNo[delete_file]
        TextPasswordFlag.text = yesNo[password_flag]
        PasswordFlagSwitch.isChecked = password_flag

        TextHash.text = hash_alg
        HashCountValue.text = hash_count.toString()
        HashCnt.progress = hash_count

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
        KeySizeMax.text = "Max = ${(keySize.getValue(cipher_alg)[2].toString())} Байт"
        KeySizeMin.text = "Min = ${(keySize.getValue(cipher_alg)[0].toString())} Байт"

        if (cipher_alg in cipherStream) {
            LinearLayoutCBC.visibility = View.GONE
            LinearLayoutPadding.visibility = View.GONE
        }
    }


    private val HashAlg = DialogInterface.OnClickListener { _, which ->
        TextHash.text = list[which]
        hash_alg = list[which]
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
        KeySizeMax.text = "Max = ${(keySize.getValue(cipher_alg)[2].toString())} Байт"
        KeySizeMin.text = "Min = ${(keySize.getValue(cipher_alg)[0].toString())} Байт"
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

    private val SignatureAlg = DialogInterface.OnClickListener { _, which ->
        SignatureAlgorithm.text = list[which]
        signature = list[which]
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        val editor = sp.edit()
        editor.putString(HashAlgorithm, hash_alg)
        editor.putInt(HashCount, hash_count)
        editor.putString(CipherAlgorithm, cipher_alg)
        editor.putInt(CipherCount, cipher_count)
        editor.putString(bcm, bcm)
        editor.putString(padding, padding)
        editor.putBoolean(Salt, salt_flag)
        editor.putBoolean(SecordPassword, second_password)
        editor.putBoolean(DeleteFile, delete_file)
        editor.putInt(KeySize, key_size)
        editor.putBoolean(PasswordFlag, password_flag)
        editor.putString(Signature, signature)
        editor.putBoolean(CipherPassword, cipher_password)
        editor.apply()
    }

    companion object {
        private lateinit var sp: SharedPreferences
        private lateinit var hash_alg: String
        private var hash_count = 1
        private lateinit var cipher_alg: String
        private var cipher_count = 1
        private lateinit var bcm: String
        private lateinit var padding: String
        private var salt_flag = false
        private var second_password = false
        private var delete_file = false
        private var key_size = 32
        private var password_flag = false
        private lateinit var signature: String
        private var cipher_password = false

        private val yesNo = mapOf(Pair(true, "Да"), Pair(false, "Нет"))

        //private val LOG_TAG = "LOG"
        private lateinit var list: List<String>

        private lateinit var TextHash: TextView
        private lateinit var TextCipher: TextView
        private lateinit var TextBCM: TextView
        private lateinit var TextPadding: TextView
        private lateinit var LinearLayoutCBC: LinearLayout
        private lateinit var LinearLayoutPadding: LinearLayout
        private lateinit var TextFlagSalt: TextView
        private lateinit var TextSecondPassword: TextView
        private lateinit var TextDeleteFile: TextView

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var FlagSalt: Switch

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var SecondPassword: Switch

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var PasswordFlagSwitch: Switch
        private lateinit var TextPasswordFlag: TextView

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var DeleteFileSwitch: Switch
        private lateinit var HashCnt: SeekBar
        private lateinit var HashCountValue: TextView
        private lateinit var HashCountEdit: EditText
        private lateinit var HashButton: Button
        private lateinit var CipherCnt: NumberPicker
        private lateinit var KeySizePicker: NumberPicker
        private lateinit var KeySizeMin: TextView
        private lateinit var KeySizeMax: TextView
        private lateinit var SignatureAlgorithm: TextView

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var CipherPasswordSwitch: Switch
        private lateinit var TextCipherPassword: TextView
    }
}