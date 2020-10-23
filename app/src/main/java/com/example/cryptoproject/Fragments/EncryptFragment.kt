package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.fragment.app.Fragment
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.Function.Cipher
import com.example.cryptoproject.Function.FileReadWrite
import com.example.cryptoproject.Function.PasswordCorrect
import com.example.cryptoproject.R
import java.io.File


class EncryptFragment : Fragment() {

    private val FILE_OPEN_CODE = 0
    private var FILENAME: String = ""
    private var passwordFlag = false
    private lateinit var ProgresBar: ProgressBar
    private var flag = false
    private val LOG_TAG = "LOG"
    private lateinit var FileSize : TextView

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            ProgresBar.visibility = View.INVISIBLE
            Toast.makeText(context, msg.data.getString("Exception", "Ошибка!"), Toast.LENGTH_SHORT)
                .show()
            Log.d(LOG_TAG, msg.data.getString("Exception", "Ошибка!"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_encrypt, container, false)
        val SecondPassword = view.findViewById<View>(R.id.secondpassword) as LinearLayout
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        flag = sp.getBoolean(getString(R.string.Provider), false)
        if (spSecond(sp)) SecondPassword.visibility = View.VISIBLE
        else SecondPassword.visibility = View.GONE
        val ButtonOpenFile =
            view.findViewById<Button>(R.id.buttonopenfile)
        ButtonOpenFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, FILE_OPEN_CODE)
        }
        ProgresBar = view.findViewById<ProgressBar>(R.id.progresbar)
        val PasswordEdit1 =
            view.findViewById<EditText>(R.id.password1)
        val PasswordEdit2 =
            view.findViewById<EditText>(R.id.password2)
        val ButtonEncript =
            view.findViewById<Button>(R.id.buttonencrypt)
        ButtonEncript.setOnClickListener {
            val password1 = PasswordEdit1.text.toString()
            val password2 = PasswordEdit2.text.toString()
            try {
                if (FILENAME == "") throw MyException("Откройте файл!", 0)
                if (password1 == "") throw MyException("Введите пароль!", 0)
                if (!PasswordCorrect(PasswordEdit1.text.toString()).PassCorrekt()) throw MyException(
                    "Пароль не соответствует требованиям!",
                    0
                )
                if (spSecond(sp) && password1 != password2) throw MyException(
                    "Введённые пароли не совпадают!",
                    0
                )
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
                passwordFlag = !passwordFlag
            } else {
                PasswordEdit1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                PasswordEdit2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordFlag = !passwordFlag
            }
        }


        return view
    }

    private fun spSecond(sp: SharedPreferences): Boolean {
        return sp.getBoolean(getString(R.string.SecordPassword), false)
    }

    @SuppressLint("UsableSpace")
    private fun Body(sp: SharedPreferences, password1: String) {
        val runnable = Runnable {
            try {
                if (!File(FILENAME).exists()) throw MyException("Файла не существует!", 0)
                if (!File(FILENAME).isFile) throw MyException("Вы выбрали не файл!", 0)
                if (File(FILENAME).usableSpace <= 1024 + File(FILENAME).length()) throw MyException(
                    "Слишклм мало памяти!",
                    0
                )
                FileReadWrite().writeFile(
                    "/storage/emulated/0/RWork/Cipher/" + File(FILENAME).name + "Crypto",
                    Cipher(
                        (byteArrayOf(1, 2, 3, 4, 5).plus(FileReadWrite().readFile(FILENAME))),
                        password1,
                        sp
                    ).Encrypt()
                )
                MessageExeption("Файл зашифрован!")
            } catch (e: MyException) {
                MessageExeption(e.message!!)
            }
        }
        val thread = Thread(runnable)
        thread.start()
    }

    private fun MessageExeption(text: String) {
        val bundle = Bundle()
        bundle.putString("Exception", text)
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
                    data?.data?.path!!.replace("/external_files", "/storage/emulated/0")
                FileSize.text = "Размер файла ${FileSize()}"
            }

        }
    }

    private fun FileSize() : String {
        return when (val size = File(FILENAME).length()) {
            in 0..999 -> "$size Байт"
            in 1000..999999 -> String.format("%.2f", size.toDouble()/1000).plus(" КБ")
            in 1000000..999999999 -> String.format("%.2f", size.toDouble()/1000000).plus(" МБ")
            in 1000000000..999999999999 -> String.format("%.2f", size.toDouble()/1000000000).plus(" ГБ")
            else -> String.format("%.2f", size.toDouble()/1000000000000).plus(" ТБ")
        }
    }
}