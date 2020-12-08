@file:Suppress("DEPRECATION", "PackageName")

package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager
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
import javax.crypto.BadPaddingException

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DecryptFragment : Fragment() {

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
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_decrypt, container, false)
        view.findViewById<Button>(R.id.buttonopenfile).setOnClickListener {
            AlertDialog.Builder(view.context).setTitle(ChooseFile)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(
                        view.context,
                        android.R.layout.simple_list_item_1,
                        listFile()
                    ), SelectFile
                )
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        PasswordKeyStore = view.findViewById(R.id.password_key_store)

        ProgresBar = view.findViewById<View>(R.id.progresbar) as ProgressBar
        PasswordEdit1 = view.findViewById(R.id.password1)
        PasswordEdit2 = view.findViewById(R.id.password2)
        val ButtonDecript =
            view.findViewById<View>(R.id.buttondecrypt) as Button
        ButtonDecript.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val password1 = PasswordEdit1.text.toString()
            val password2 = PasswordEdit2.text.toString()
            password_key_store = PasswordKeyStore.text.toString()
            try {
                if (FILENAME == "") throw MyException(OpenFile)
                if (password1 == "") throw MyException(EnterPassword)
                if (!PasswordCorrect(password1).PassCorrekt() && spPasswordFlag(sp)) throw MyException(
                    RequirementsPassword)
                if (spSecond(sp)) if (password1 != password2) throw MyException(CoincidePassword)
                if (PasswordKeyStore.visibility == View.VISIBLE && password_key_store == "") throw MyException(
                    EnterPasswordKeyStore)
                ProgresBar.visibility = View.VISIBLE
                Body(password1)

            } catch (e: MyException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Log.d(LOG_TAG, e.message)
            }
        }

        FileSize = view.findViewById(R.id.sizefile)
        val ButtonPassword =
            view.findViewById<View>(R.id.buttonpassword) as Button
        ButtonPassword.setOnClickListener {
            if (passwordFlag) {
                PasswordEdit1.transformationMethod = PasswordTransformationMethod.getInstance()
                PasswordEdit2.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordFlag = !passwordFlag
            } else {
                PasswordEdit1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                PasswordEdit2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordFlag = !passwordFlag
            }
        }
        return view
    }

    @SuppressLint("UsableSpace")
    private fun Body(password: String) {
        val runnable = Runnable {
            try {
                if (!File(FILENAME).exists()) throw MyException(NotExist)
                if (!File(FILENAME).isFile) throw MyException(SelectNotFile)
                if (File(FILENAME).usableSpace <= 1024 + File(FILENAME).length()) throw MyException(LowMemory)
                var flag = true
                var arr = FileReadWrite().readFile(FILENAME)
                if (arr[0] != zero) {
                    if (!Signature(arr,
                            SetOfAlg().sign[arr[0].toInt()],
                            password_key_store).SignDecrypt()
                    ) throw MyException(NotSignature)
                    else arr = arr.copyOfRange(257, arr.size)
                }
                val cipher = Cipher(arr)
                if (flag_cipher_password) cipher.setPasswordKeyStore(password_key_store)
                else cipher.setPassword(password)
                val list = cipher.Decrypt().toMutableList()
                interval.forEach { if (list.removeFirst() != it.toByte()) flag = false }
                if (!flag) throw MyException(WrongPassword)
                FileReadWrite().writeFile(ClearPath + File(FILENAME).name,
                    list.toByteArray()
                )
                MessageExeption(FileEncrypted)
            } catch (e: NullPointerException) {
                MessageExeption(NotEncryptFile)
            } catch (e: MyException) {
                MessageExeption(e.message!!)
                Log.d(LOG_TAG, e.message)
            } catch (e: BadPaddingException) {
                MessageExeption(EnterWrongPassword)
            }
        }

        val thread = Thread(runnable)
        thread.start()
    }

    private fun MessageExeption(text: String) {
        val bundle = Bundle()
        bundle.putString(Exc, text)
        val msg = Message.obtain(handler, 0)
        msg.data = bundle
        handler.sendMessage(msg)
    }

    @SuppressLint("SetTextI18n")
    private val SelectFile = DialogInterface.OnClickListener { _, which ->
        FILENAME = CipherPath + listFile()[which]
        FileSize.text = FILESIZE + FileSize()
        val arr = FileReadWrite().readFile(FILENAME)
        if (arr[0] != zero || arr[0] == zero && arr[1] % 2 != 0) {
            PasswordKeyStore.visibility = View.VISIBLE
            flag_cipher_password = true
            PasswordEdit1.visibility = View.GONE
            PasswordEdit2.visibility = View.GONE
        }
        else {
            PasswordKeyStore.visibility = View.GONE
            flag_cipher_password = false
            PasswordEdit1.visibility = View.VISIBLE
            PasswordEdit2.visibility = View.VISIBLE
        }
    }

    private fun FileSize(): String {
        return when (val size = File(FILENAME).length()) {
            in 0..999 -> "$size Байт"
            in 1000..999999 -> String.format(Rule, size.toDouble() / 1000).plus(" КБ")
            in 1000000..999999999 -> String.format(Rule, size.toDouble() / 1000000).plus(" МБ")
            in 1000000000..999999999999 -> String.format(Rule, size.toDouble() / 1000000000).plus(
                " ГБ"
            )
            else -> String.format(Rule, size.toDouble() / 1000000000000).plus(" ТБ")
        }
    }

    private fun listFile(): List<String> {
        val files =
            File(CipherPath).listFiles()
        val names = mutableListOf<String>()
        for (file in files) names.add(file.name)
        return names
    }

    companion object {

        private const val zero = 0.toByte()
        private val interval = 0..127

        private var FILENAME: String = ""
        private var password_key_store = ""
        private var flag_cipher_password = false
        private var passwordFlag = false

        private lateinit var PasswordKeyStore: EditText
        private lateinit var PasswordEdit1: EditText
        private lateinit var PasswordEdit2: EditText
        private lateinit var ProgresBar: ProgressBar
        private lateinit var FileSize: TextView
    }
}