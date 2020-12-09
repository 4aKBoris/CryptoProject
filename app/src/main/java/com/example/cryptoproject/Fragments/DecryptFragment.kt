@file:Suppress("DEPRECATION", "PackageName")

package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.cryptoproject.Expeptions.MyException
import com.example.cryptoproject.Function.*
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.*
import java.io.File
import java.io.FileInputStream
import java.security.cert.CertificateFactory
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

        sp = PreferenceManager.getDefaultSharedPreferences(context)
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
        PasswordView = view.findViewById(R.id.firstpassword)
        val ButtonDecript =
            view.findViewById<View>(R.id.buttondecrypt) as Button
        ButtonDecript.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val password1 = PasswordEdit1.text.toString()
            password_key_store = PasswordKeyStore.text.toString()
            try {
                if (FILENAME == "") throw MyException(OpenFile)
                if (password1 == "" && !flag_cipher_password) throw MyException(EnterPassword)
                if (!PasswordCorrect(password1).PassCorrekt() && spPasswordFlag(sp) && !flag_cipher_password) throw MyException(
                    RequirementsPassword)
                if (flag_cipher_password && password_key_store == "") throw MyException(
                    EnterPasswordKeyStore)
                if (flag_signature && cerf_path == "") throw MyException(ChouseCerf)
                ProgresBar.visibility = View.VISIBLE
                Body(password1)

            } catch (e: MyException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Log.d(LOG_TAG, e.message)
            }
        }

        CerfSignature = view.findViewById(R.id.cerf_signature)
        CerfSignatureText = view.findViewById(R.id.cerf_signature_text)
        CerfSignature.setOnClickListener {
            list = File(CertificatesPath).listFiles().map { it.name }
            AlertDialog.Builder(view.context).setTitle(CertSelect)
                .setCancelable(false)
                .setAdapter(
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list),
                    CertificateSelect)
                .setNegativeButton(
                    Cansel
                ) { dialog, _ -> dialog.cancel() }.create()
                .show()
        }

        FileSize = view.findViewById(R.id.sizefile)
        val ButtonPassword =
            view.findViewById<View>(R.id.buttonpassword) as Button
        ButtonPassword.setOnClickListener {
            if (passwordFlag) {
                PasswordEdit1.transformationMethod = PasswordTransformationMethod.getInstance()
                PasswordKeyStore.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                PasswordEdit1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                PasswordKeyStore.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            passwordFlag = !passwordFlag
        }
        return view
    }

    private val CertificateSelect = DialogInterface.OnClickListener { _, which ->
        cerf_path = CertificatesPath + list[which]
        val k = FileReadWrite().readFileOne(FILENAME)
        val sign_alg = sign[k]
        val certificateFactory = CertificateFactory.getInstance(X509)
        val certificateInputStream = FileInputStream(cerf_path)
        val certificate = certificateFactory.generateCertificate(certificateInputStream)
        if (certificate.publicKey.algorithm == Alg(sign_alg)) {
            CerfSignatureText.text = list[which]
        } else {
            Toast.makeText(context, NotSignAlg, Toast.LENGTH_SHORT).show()
            cerf_path = ""
        }
    }

    @SuppressLint("UsableSpace")
    private fun Body(password: String) {
        val runnable = Runnable {
            try {
                if (!File(FILENAME).exists()) throw MyException(NotExist)
                if (!File(FILENAME).isFile) throw MyException(SelectNotFile)
                if (File(FILENAME).usableSpace <= 1024 + File(FILENAME).length()) throw MyException(
                    LowMemory)
                var flag = true
                var arr = FileReadWrite().readFile(FILENAME)
                if (arr[0] != zero) {
                    if (!Signature(arr, sign[arr[0].toInt()], cerf_path).SignDecrypt()
                    ) throw MyException(NotSignature)
                    else arr = arr.copyOfRange(arr[1].toInt() + 131, arr.size)
                }
                val cipher = Cipher(arr)
                if (!flag_cipher_password) cipher.setPassword(password)
                else cipher.setPasswordKeyStore(password_key_store)
                val list = cipher.Decrypt().toMutableList()
                interval.forEach { if (list.removeFirst() != it.toByte()) flag = false }
                if (!flag) throw MyException(WrongPassword)
                FileReadWrite().writeFile(ClearPath + File(FILENAME).name,
                    list.toByteArray()
                )
                MessageExeption(FileDecrypted)
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
        val arr = FileReadWrite().readFileN(FILENAME, 259)
        if (((arr[0] == zero && arr[1] % 2 != 0) || (arr[0] != zero) && arr[arr[1].toInt() + 131] % 2 != 0) && spCipherPassword(
                sp)
        ) {
            PasswordKeyStore.visibility = View.VISIBLE
            flag_cipher_password = true
            PasswordView.visibility = View.GONE
        } else {
            PasswordKeyStore.visibility = View.GONE
            flag_cipher_password = false
            PasswordView.visibility = View.VISIBLE
        }
        if (arr[0] != zero) {
            CerfSignature.visibility = View.VISIBLE
            flag_signature = true
        } else {
            CerfSignature.visibility = View.GONE
            flag_signature = false
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

    private fun Alg(sign_alg: String): String {
        return when {
            sign_alg.indexOf("EC") != -1 -> "EC"
            sign_alg.indexOf("with") == -1 -> sign_alg
            else -> sign_alg.substring(sign_alg.indexOf("with") + 4)
        }
    }

    companion object {

        private const val zero = 0.toByte()
        private val interval = 0..127

        private lateinit var sp: SharedPreferences
        private var FILENAME: String = ""
        private var password_key_store = ""
        private var flag_cipher_password = false
        private var flag_signature = false
        private var passwordFlag = false
        private var cerf_path = ""

        private lateinit var list: List<String>
        private lateinit var PasswordKeyStore: EditText
        private lateinit var PasswordEdit1: EditText
        private lateinit var CerfSignature: View
        private lateinit var CerfSignatureText: TextView
        private lateinit var PasswordView: View
        private lateinit var ProgresBar: ProgressBar
        private lateinit var FileSize: TextView
    }
}