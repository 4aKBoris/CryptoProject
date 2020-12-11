@file:Suppress("DEPRECATION")

package com.example.cryptoproject

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoproject.Function.Permissions
import com.example.cryptoproject.Сonstants.*
import java.io.File


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val p = Permissions()
        p.requestMultiplePermissions(this, PERMISSION_REQUEST_CODE)
        create(RWork)!!
        create(RCipher)!!
        create(RClear)!!
        create(RCertificates)!!
        CreateSP()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun CreateSP() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sp.edit()
        if (!sp.contains(HashAlgorithm)) editor.putString(HashAlgorithm, SHA256)
        if (!sp.contains(HashCount)) editor.putInt(HashCount, ONE)
        if (!sp.contains(CipherAlgorithm)) editor.putString(CipherAlgorithm, AES)
        if (!sp.contains(CipherCount)) editor.putInt(CipherCount, ONE)
        if (!sp.contains(BCM)) editor.putString(BCM, CBC)
        if (!sp.contains(Padding)) editor.putString(Padding, PKCS5Padding)
        if (!sp.contains(Salt)) editor.putBoolean(Salt, NOT)
        if (!sp.contains(SecordPassword)) editor.putBoolean(SecordPassword, NOT)
        if (!sp.contains(DeleteFile)) editor.putBoolean(DeleteFile, NOT)
        if (!sp.contains(KeySize)) editor.putInt(KeySize, KEYSIZE)
        if (!sp.contains(PasswordFlag)) editor.putBoolean(PasswordFlag, NOT)
        if (!sp.contains(Signature)) editor.putString(Signature, NotUse)
        if (!sp.contains(CipherPassword)) editor.putBoolean(CipherPassword, NOT)
        editor.apply()
    }

    private fun create(name: String): File? {
        val baseDir: File = Environment.getExternalStorageDirectory()
            ?: return Environment.getExternalStorageDirectory()
        val folder = File(baseDir, name)
        if (folder.exists()) {
            return folder
        }
        if (folder.isFile) {
            folder.delete()
        }
        return if (folder.mkdirs()) {
            folder
        } else Environment.getExternalStorageDirectory()
    }
}