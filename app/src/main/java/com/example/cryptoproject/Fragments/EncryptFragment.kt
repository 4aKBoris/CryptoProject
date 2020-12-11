@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.Function.*
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.*
import java.io.File


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNREACHABLE_CODE")
class EncryptFragment : Fragment() {

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            ProgresBar.visibility = View.INVISIBLE
            Toast.makeText(context, msg.data.getString(Exc, Mistake), Toast.LENGTH_SHORT)
                .show()
            Log.d(LOG_TAG, msg.data.getString(Exc, Mistake))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_encrypt, container, false)
        val SecondPassword = view.findViewById<View>(R.id.secondpassword) as LinearLayout
        val sp = getDefaultSharedPreferences(context)
        if (spSecond(sp)) SecondPassword.visibility = View.VISIBLE
        else SecondPassword.visibility = View.GONE
        val ButtonOpenFile =
            view.findViewById<Button>(R.id.buttonopenfile)
        ButtonOpenFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, FILE_OPEN_CODE)
        }
        ProgresBar = view.findViewById(R.id.progresbar)
        val PasswordEdit1 =
            view.findViewById<EditText>(R.id.password1)
        val PasswordEdit2 =
            view.findViewById<EditText>(R.id.password2)
        val ButtonEncript =
            view.findViewById<Button>(R.id.buttonencrypt)
        PasswordSelect = view.findViewById(R.id.cipher_password)
        if (!spCipherPassword(sp)) PasswordSelect.visibility = View.GONE
        PasswordSelect.setOnClickListener {
            list = File(CertificatesPath).listFiles().map { it.name }
            AlertDialog.Builder(view.context).setTitle(CertSelect)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list),
                    CertificateSelect
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        PasswordKeyStore = view.findViewById(R.id.password_signature)
        if (spSignature(sp) == NotUse) PasswordKeyStore.visibility = View.GONE

        PasswordSelectText = view.findViewById(R.id.cipher_password_text)
        ButtonEncript.setOnClickListener {
            val password1 = PasswordEdit1.text.toString()
            val password2 = PasswordEdit2.text.toString()
            try {
                if ("" == FILENAME) throw MyException(OpenFile)
                if (password1 == "") throw MyException(EnterPassword)
                if (!PasswordCorrect(password1).PassCorrekt() && spPasswordFlag(sp)) throw MyException(
                    RequirementsPassword)
                if (spSecond(sp) && password1 != password2) throw MyException(CoincidePassword)
                if (spCipherPassword(sp) && certificate_path == "") throw MyException(CertificSelect)
                if (spSignature(sp) != NotUse && PasswordKeyStore.text.toString() == "") throw MyException(
                    EnterPasswordKeyStore)
                ProgresBar.visibility = View.VISIBLE
                Body(sp, password1)

            } catch (e: MyException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        FileSize = view.findViewById(R.id.sizefile)

        val ButtonPassword =
            view.findViewById<Button>(R.id.buttonpassword)
        ButtonPassword.setOnClickListener {
            if (passwordFlag) {
                PasswordEdit1.transformationMethod = PasswordTransformationMethod.getInstance()
                PasswordEdit2.transformationMethod = PasswordTransformationMethod.getInstance()
                PasswordKeyStore.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                PasswordEdit1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                PasswordEdit2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                PasswordKeyStore.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            passwordFlag = !passwordFlag
        }


        return view
    }

    private val CertificateSelect = DialogInterface.OnClickListener { _, which ->
        PasswordSelectText.text = list[which]
        certificate_path = list[which]
    }

    @SuppressLint("UsableSpace")
    private fun Body(sp: SharedPreferences, password1: String) {
        val runnable = Runnable {
            try {
                if (!File(FILENAME).exists()) throw MyException(NotExist)
                if (!File(FILENAME).isFile) throw MyException(SelectNotFile)
                if (File(FILENAME).usableSpace <= 1024 + File(FILENAME).length()) throw MyException(LowMemory)
                val arr = ByteArray(BlockSize).createIndex().plus(readFile(FILENAME))
                val cipher = Cipher(arr, password1, sp)
                if (spSignature(sp) != NotUse) cipher.setPasswordKeyStore(
                    PasswordKeyStore.text.toString())
                if (spCipherPassword(sp)) cipher.setCertificatePath(certificate_path)
                writeFile(CipherPath + File(FILENAME).name,
                    cipher.Encrypt())
                if (spDeleteFile(sp)) File(FILENAME).delete()
                MessageExeption(FileEncrypted)
            } catch (e: MyException) {
                MessageExeption(e.message!!)
            }
        }
        val thread = Thread(runnable)
        thread.start()
    }

    private fun MessageExeption(text: String) {
        val bundle = Bundle()
        bundle.putString(Exc, text)
        val msg = Message.obtain(handler)
        msg.data = bundle
        handler.sendMessage(msg)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FILE_OPEN_CODE -> {
                if (resultCode == RESULT_OK) FILENAME =
                    data?.data?.path!!.replace("/external_files", Environment.getExternalStorageDirectory().path)
                        .replace("/root",
                            "")
                FileSize.text = FILESIZE + FileSize()
            }

        }
    }

    private fun FileSize(): String {
        return when (val size = File(FILENAME).length()) {
            in 0..999 -> "$size Байт"
            in 1000..999999 -> String.format(Rule, size.toDouble() / 1000).plus(" КБ")
            in 1000000..999999999 -> String.format(Rule, size.toDouble() / 1000000).plus(" МБ")
            in 1000000000..999999999999 -> String.format(Rule, size.toDouble() / 1000000000).plus(
                " ГБ")
            else -> String.format(Rule, size.toDouble() / 1000000000000).plus(" ТБ")
        }
    }

    companion object {
        private const val FILE_OPEN_CODE = 0
        private var FILENAME: String = ""
        private var passwordFlag = false
        private lateinit var ProgresBar: ProgressBar
        private lateinit var FileSize: TextView
        private lateinit var PasswordSelect: View
        private lateinit var PasswordSelectText: TextView
        private lateinit var PasswordKeyStore: EditText

        var list = listOf<String>()
        private var certificate_path = ""
    }
}