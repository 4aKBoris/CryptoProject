package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.app.Activity
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
import javax.crypto.BadPaddingException

class DecryptFragment : Fragment() {

    private val FILE_OPEN_CODE = 0
    private var FILENAME: String = ""
    private var passwordFlag = false
    private lateinit var ProgresBar: ProgressBar
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

        val view = inflater.inflate(R.layout.fragment_decrypt, container, false)
        val ButtonOpenFile =
            view.findViewById<View>(R.id.buttonopenfile) as Button
        ButtonOpenFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, FILE_OPEN_CODE)
        }
        ProgresBar = view.findViewById<View>(R.id.progresbar) as ProgressBar
        val PasswordEdit1 =
            view.findViewById<View>(R.id.password1) as EditText
        val PasswordEdit2 =
            view.findViewById<View>(R.id.password2) as EditText
        val ButtonDecript =
            view.findViewById<View>(R.id.buttondecrypt) as Button
        ButtonDecript.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val password1 = PasswordEdit1.text.toString()
            val password2 = PasswordEdit2.text.toString()
            try {
                if (FILENAME == "") throw MyException("Откройте файл!", 0)
                if (password1 == "") throw MyException("Введите пароль!", 0)
                if (!PasswordCorrect(PasswordEdit1.text.toString()).PassCorrekt()) throw MyException(
                    "Пароль не соответствует требованиям!",
                    0
                )
                if (spSecond(sp)) if (password1 != password2) throw MyException(
                    "Введённые пароли не совпадают!",
                    0
                )
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

    private fun spSecond(sp: SharedPreferences): Boolean {
        return sp.getBoolean(getString(R.string.SecordPassword), false)
    }

    private fun Body(password: String) {
        val runnable = Runnable {
            try {
                if (!File(FILENAME).exists()) throw MyException("Файла не существует!", 0)
                if (!File(FILENAME).isFile) throw MyException("Вы выбрали не файл!", 0)
                if (File(FILENAME).usableSpace <= 1024 + File(FILENAME).length()) throw MyException(
                    "Слишклм мало памяти!",
                    0
                )
                var flag = true
                val list =
                    Cipher(FileReadWrite().readFile(FILENAME), password).Decrypt().toMutableList()
                for (i in 1..5) if (list.removeAt(0) != i.toByte()) flag = false
                if (!flag) throw MyException("Неверный пароль!", 0);
                FileReadWrite().writeFile(
                    "/storage/emulated/0/RWork/Clear_files/" + File(FILENAME).name.replace(
                        "Crypto",
                        ""
                    ), list.toByteArray()
                )
                MessageExeption("Файл расшифрован!")
            } catch (e: NullPointerException) {
                MessageExeption("Выбран не зашифрованный файл!")
            } catch (e: MyException) {
                MessageExeption(e.message!!)
                Log.d(LOG_TAG, e.message)
            } catch (e: BadPaddingException) {
                MessageExeption("Введён неверный пароль!")
            }
        }

        val thread = Thread(runnable)
        thread.start()
    }

    private fun MessageExeption(text: String) {
        val bundle = Bundle()
        bundle.putString("Exception", text)
        val msg = Message.obtain(handler, 0)
        msg.data = bundle
        handler.sendMessage(msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FILE_OPEN_CODE ->
            {
                if (resultCode == Activity.RESULT_OK) FILENAME =
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