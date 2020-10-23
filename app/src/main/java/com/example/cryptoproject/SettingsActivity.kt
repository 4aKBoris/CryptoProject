package com.example.cryptoproject

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.Function.SetOfAlg


class SettingsActivity : AppCompatActivity() {

    private val set = SetOfAlg()
    private val LOG_TAG = "LOG"
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
    private lateinit var DeleteFile: Switch
    private lateinit var Provider: CheckBox
    private lateinit var HashCount: SeekBar
    private lateinit var HashCountValue: TextView
    private lateinit var HashCountEdit: EditText
    private lateinit var HashButton: Button
    private lateinit var CipherCount: NumberPicker


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
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
        CipherCount = findViewById<NumberPicker>(R.id.cipher_count)

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
                    "Введённое значение не соответствует требованиям!",
                    0
                )
                val k = HashCountEdit.text.toString().toInt()
                if (k <= 0 || k > 16256) throw MyException(
                    "Введённое значение не соответствует требованиям!",
                    0
                )
                else {
                    hash_count = k
                    HashCountValue.text = k.toString()
                    HashCount.progress = k
                }
            }
            catch (e: MyException) {
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
            if (BCM == "CTR") list = listOf("ECB", "CBC")
            if (BCM != "ECB" || BCM != "CBC") {
                val l = list.toMutableList()
                l.remove("withCTS")
                list = l.toList()
            }
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
            TextFlagSalt.text = yesNo[Salt]
        }

        DeleteFile.setOnCheckedChangeListener { _, isChecked ->
            deleteFile = isChecked
            TextFlagSalt.text = yesNo[Salt]
        }

        setSettings()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

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

        FlagSalt.isChecked = Salt
        TextFlagSalt.text = yesNo[Salt]
        SecondPassword.isChecked = secondPassword
        TextSecondPassword.text = yesNo[secondPassword]
        DeleteFile.isChecked = deleteFile
        TextDeleteFile.text = yesNo[deleteFile]

        TextHash.text = hash_alg
        HashCountValue.text = hash_count.toString()
        HashCount.progress = hash_count

        TextCipher.text = cipher_alg
        TextBCM.text = BCM
        TextPadding.text = Padding
        CipherCount.value = cipher_count
        Provider.isChecked = provider

        if (cipher_alg in set.cipherStream) {
            LinearLayoutCBC.visibility = View.GONE
            LinearLayoutPadding.visibility = View.GONE
        }
    }


    private val HashAlg = DialogInterface.OnClickListener { _, which ->
        TextHash.text = list[which]
        hash_alg = list[which]
    }

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
        if ((cipher_alg !in set.cipher64 && BCM == "GOFB") || (cipher_alg !in set.cipher128 && BCM in set.cbc128)) {
            BCM = getString(R.string.CBC)
            TextBCM.text = getString(R.string.CBC)
        }
    }

    private val CipherBCM = DialogInterface.OnClickListener { _, which ->
        TextBCM.text = list[which]
        BCM = list[which]
    }

    private val CipherPadding = DialogInterface.OnClickListener { _, which ->
        TextPadding.text = list[which]
        Padding = list[which]
    }

    private fun DefaultSettings() {
        val editor: Editor = sp.edit()
        editor.putBoolean(getString(R.string.Provider), false)
        editor.putString(getString(R.string.HashAlgorithm), "SHA-256")
        editor.putString(getString(R.string.CipherAlgorithm), "AES")
        editor.putString(getString(R.string.BCM), "CBC")
        editor.putString(getString(R.string.Padding), "PKCS5Padding")
        TextHash.text = getString(R.string.SHA)
        TextCipher.text = getString(R.string.AES)
        TextBCM.text = getString(R.string.BCM)
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

        private val yesNo = mapOf(Pair(true, "Да"), Pair(false, "Нет"))
    }
}