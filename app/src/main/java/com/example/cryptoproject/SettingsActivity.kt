@file:Suppress("DEPRECATION")

package com.example.cryptoproject

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
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
import com.example.cryptoproject.Function.SetOfAlg


@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING"
)
class SettingsActivity : AppCompatActivity() {

    private val set = SetOfAlg()
    //private val LOG_TAG = "LOG"
    private lateinit var sp: SharedPreferences
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
    private lateinit var PasswordFlag: Switch
    private lateinit var TextPasswordFlag: TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var DeleteFile: Switch
    private lateinit var Provider: CheckBox
    private lateinit var HashCount: SeekBar
    private lateinit var HashCountValue: TextView
    private lateinit var HashCountEdit: EditText
    private lateinit var HashButton: Button
    private lateinit var CipherCount: NumberPicker
    private lateinit var KeySize: NumberPicker
    private lateinit var KeySizeMin: TextView
    private lateinit var KeySizeMax: TextView


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
        DeleteFile = findViewById(R.id.delete_file)
        Provider = findViewById(R.id.checkbox1)
        HashCount = findViewById(R.id.hash_count)
        HashCountValue = findViewById(R.id.hash_count_value)
        HashCountEdit = findViewById(R.id.hash_count_edit)
        HashButton = findViewById(R.id.hash_set_count)
        CipherCount = findViewById(R.id.cipher_count)
        KeySize = findViewById(R.id.key_size)
        KeySizeMin = findViewById(R.id.min_key_size)
        KeySizeMax = findViewById(R.id.max_key_size)
        PasswordFlag = findViewById(R.id.password_flag)
        TextPasswordFlag = findViewById(R.id.password_flag_text)

        findViewById<View>(R.id.hash_alg).setOnClickListener {
            list = if (provider) set.hash_alg_bc else set.hash_alg_default
            AlertDialog.Builder(this).setTitle("Алгоритм хэширования")
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    HashAlg
                )
                .setNegativeButton(
                    "Отмена"
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        HashCount.min = 1
        HashCount.max = 16383
        HashCount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                hash_count = progress
                HashCountValue.text = progress.toString()
            }
        })

        HashButton.setOnClickListener {
            try {
                if (HashCountEdit.text.toString() == "") throw MyException(
                    "Введённое значение не соответствует требованиям!"
                )
                val k = HashCountEdit.text.toString().toInt()
                if (k <= 0 || k > 16256) throw MyException(
                    "Введённое значение не соответствует требованиям!"
                )
                else {
                    hash_count = k
                    HashCountValue.text = k.toString()
                    HashCount.progress = k
                }
            } catch (e: MyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                HashCountEdit.text.clear()
            }
        }


        Provider.setOnCheckedChangeListener { _, isChecked ->
            provider = isChecked
            if (!provider) DefaultSettings()
        }



        findViewById<View>(R.id.cipher_alg).setOnClickListener {
            list = if (provider) set.cipher_alg_bc else set.cipher_alg_default
            AlertDialog.Builder(this).setTitle("Алгоритм шифрования")
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    CipherAlg
                )
                .setNegativeButton(
                    "Отмена"
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        CipherCount.minValue = 1
        CipherCount.maxValue = 127

        CipherCount.setOnValueChangedListener { _, _, newVal ->
            cipher_count = newVal
        }

        KeySize.setOnValueChangedListener { _, _, newVal ->
            val step = set.keySize[cipher_alg]
            val values = mutableListOf<Int>()
            for (i in step!![0]..step[2] step (step[1])) values.add(i)
            keysize = values[newVal]
        }

        findViewById<View>(R.id.cipher_bcm).setOnClickListener {
            list = if (provider) set.cipher_bcm_bc else set.cipher_bcm_default
            if (cipher_alg !in set.cipher64) {
                val l = list.toMutableList()
                l.remove("GOFB")
                list = l.toList()
            }
            if (cipher_alg !in set.cipher128) {
                val l = list.toMutableList()
                l.removeAll(set.cbc128)
                list = l.toList()
            }
            if (cipher_alg == "GOST3412-2015") {
                val l = list.toMutableList()
                l.remove("CTR")
                list = l.toList()
            }
            AlertDialog.Builder(this).setTitle("Режим сцепления блоков")
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    CipherBCM
                )
                .setNegativeButton(
                    "Отмена"
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        findViewById<View>(R.id.cipher_padding).setOnClickListener {
            list = if (provider) set.cipher_padding_bc else set.cipher_padding_default
            //if (BCM == "CTR") list = listOf("ECB", "CBC")
            if (BCM != "ECB" && BCM != "CBC") {
                val l = list.toMutableList()
                l.remove("withCTS")
                list = l.toList()
            }
            if (BCM in set.AEAD) list = listOf("NoPadding")
            AlertDialog.Builder(this).setTitle("Режим наполнения")
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, list),
                    CipherPadding
                )
                .setNegativeButton(
                    "Отмена"
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        FlagSalt.setOnCheckedChangeListener { _, isChecked ->
            Salt = isChecked
            TextFlagSalt.text = yesNo[Salt]
        }

        SecondPassword.setOnCheckedChangeListener { _, isChecked ->
            secondPassword = isChecked
            TextFlagSalt.text = yesNo[secondPassword]
        }

        DeleteFile.setOnCheckedChangeListener { _, isChecked ->
            deleteFile = isChecked
            TextFlagSalt.text = yesNo[deleteFile]
        }

        PasswordFlag.setOnCheckedChangeListener { _, isChecked ->
            passwordFlag = isChecked
            TextPasswordFlag.text = yesNo[passwordFlag]
        }

        setSettings()

    }

    @SuppressLint("SetTextI18n")
    private fun setSettings() {
        hash_alg = sp.getString(getString(R.string.HashAlgorithm), getString(R.string.SHA))!!
        hash_count = sp.getInt(getString(R.string.HashCount), 1)
        cipher_alg = sp.getString(getString(R.string.CipherAlgorithm), getString(R.string.AES))!!
        cipher_count = sp.getInt(getString(R.string.CipherCount), 1)
        BCM = sp.getString(getString(R.string.BCM), getString(R.string.CBC))!!
        Padding = sp.getString(getString(R.string.Padding), getString(R.string.PKCS5Padding))!!
        Salt = sp.getBoolean(getString(R.string.Salt), false)
        secondPassword = sp.getBoolean(getString(R.string.SecordPassword), false)
        deleteFile = sp.getBoolean(getString(R.string.DeleteFile), false)
        provider = sp.getBoolean(getString(R.string.Provider), false)
        keysize = sp.getInt(getString(R.string.keySize), set.keySize.getValue(cipher_alg)[2])
        passwordFlag = sp.getBoolean(getString(R.string.PasswordFlag), false)

        FlagSalt.isChecked = Salt
        TextFlagSalt.text = yesNo[Salt]
        SecondPassword.isChecked = secondPassword
        TextSecondPassword.text = yesNo[secondPassword]
        DeleteFile.isChecked = deleteFile
        TextDeleteFile.text = yesNo[deleteFile]
        TextPasswordFlag.text = yesNo[passwordFlag]
        PasswordFlag.isChecked = passwordFlag

        TextHash.text = hash_alg
        HashCountValue.text = hash_count.toString()
        HashCount.progress = hash_count

        TextCipher.text = cipher_alg
        TextBCM.text = BCM
        TextPadding.text = Padding
        CipherCount.value = cipher_count
        Provider.isChecked = provider

        val step = set.keySize[cipher_alg]
        val values = mutableListOf<String>()
        for (i in step!![0]..step[2] step (step[1])) values.add(i.toString())
        KeySize.minValue = 0
        KeySize.maxValue = values.size - 1
        KeySize.displayedValues = values.toTypedArray()
        KeySize.value = values.indexOf(keysize.toString())
        KeySizeMax.text = "Max = ${(set.keySize.getValue(cipher_alg)[2].toString())} Байт"
        KeySizeMin.text = "Min = ${(set.keySize.getValue(cipher_alg)[0].toString())} Байт"

        if (cipher_alg in set.cipherStream) {
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
        if (cipher_alg in set.cipherStream) {
            LinearLayoutCBC.visibility = View.GONE
            LinearLayoutPadding.visibility = View.GONE
        } else {
            LinearLayoutCBC.visibility = View.VISIBLE
            LinearLayoutPadding.visibility = View.VISIBLE
        }
        if ((cipher_alg !in set.cipher64 && BCM == "GOFB") || (cipher_alg !in set.cipher128 && BCM in set.cbc128) || cipher_alg == "GOST3412-2015" && BCM == "CTR") {
            BCM = getString(R.string.CBC)
            TextBCM.text = getString(R.string.CBC)
        }

        val step = set.keySize[cipher_alg]
        val values = mutableListOf<String>()
        for (i in step!![0]..step[2] step (step[1])) values.add(i.toString())
        if (KeySize.maxValue < values.size) {
            KeySize.displayedValues = values.toTypedArray()
            KeySize.maxValue = values.size - 1
        } else {
            KeySize.maxValue = values.size - 1
            KeySize.displayedValues = values.toTypedArray()
        }
        KeySize.minValue = 0
        KeySize.value = values.size - 1
        keysize = step[2]
        KeySizeMax.text = "Max = ${(set.keySize.getValue(cipher_alg)[2].toString())} Байт"
        KeySizeMin.text = "Min = ${(set.keySize.getValue(cipher_alg)[0].toString())} Байт"
    }

    @SuppressLint("SetTextI18n")
    private val CipherBCM = DialogInterface.OnClickListener { _, which ->
        TextBCM.text = list[which]
        BCM = list[which]
        if (BCM in set.AEAD) {
            TextPadding.text = "NoPadding"
            Padding = "NoPadding"
        }
        if (BCM != "ECB" && BCM != "CBC" && Padding == "withCTS") {
            TextPadding.text = getString(R.string.CBC)
            Padding = getString(R.string.CBC)
        }
    }

    private val CipherPadding = DialogInterface.OnClickListener { _, which ->
        TextPadding.text = list[which]
        Padding = list[which]
    }

    @SuppressLint("SetTextI18n")
    private fun DefaultSettings() {
        val editor: Editor = sp.edit()
        provider = false
        cipher_alg = getString(R.string.AES)
        hash_alg = getString(R.string.SHA)
        BCM = getString(R.string.CBC)
        Padding = getString(R.string.PKCS5Padding)
        keysize = 32
        KeySize.minValue = 0
        if (KeySize.maxValue > 2) {
            KeySize.maxValue = 2
            KeySize.displayedValues = arrayOf("16", "24", "32")
        } else {
            KeySize.displayedValues = arrayOf("16", "24", "32")
            KeySize.maxValue = 2
        }
        KeySize.value = 2
        KeySizeMin.text = "Min = ${16} Байт"
        KeySizeMax.text = "Max = ${32} Байт"
        TextHash.text = getString(R.string.SHA)
        TextCipher.text = getString(R.string.AES)
        TextBCM.text = getString(R.string.CBC)
        TextPadding.text = getString(R.string.PKCS5Padding)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        val editor = sp.edit()
        editor.putString(getString(R.string.HashAlgorithm), hash_alg)
        editor.putInt(getString(R.string.HashCount), hash_count)
        editor.putString(getString(R.string.CipherAlgorithm), cipher_alg)
        editor.putInt(getString(R.string.CipherCount), cipher_count)
        editor.putString(getString(R.string.BCM), BCM)
        editor.putString(getString(R.string.Padding), Padding)
        editor.putString(getString(R.string.BCM), BCM)
        editor.putString(getString(R.string.Padding), Padding)
        editor.putBoolean(getString(R.string.Salt), Salt)
        editor.putBoolean(getString(R.string.SecordPassword), secondPassword)
        editor.putBoolean(getString(R.string.DeleteFile), deleteFile)
        editor.putBoolean(getString(R.string.Provider), provider)
        editor.putInt(getString(R.string.keySize), keysize)
        editor.putBoolean(getString(R.string.PasswordFlag), passwordFlag)
        editor.apply()
    }

    companion object {
        private lateinit var hash_alg: String
        private var hash_count = 1
        private lateinit var cipher_alg: String
        private var cipher_count = 1
        private lateinit var BCM: String
        private lateinit var Padding: String
        private var Salt = false
        private var secondPassword = false
        private var deleteFile = false
        private var provider = false
        private var keysize = 32
        private var passwordFlag = false

        private val yesNo = mapOf(Pair(true, "Да"), Pair(false, "Нет"))
    }
}