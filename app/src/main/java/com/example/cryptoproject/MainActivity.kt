package com.example.cryptoproject

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoproject.Function.Permissions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.File


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val p = Permissions()
        p.requestMultiplePermissions(this, PERMISSION_REQUEST_CODE)
        val fl = create("RWork")!!
        val fl1 = create("RWork/Cipher")!!
        val fl2 = create("RWork/Clear_files")!!
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
        if (!sp.contains(getString(R.string.HashAlgorithm))) editor.putString(
            getString(R.string.HashAlgorithm),
            getString(R.string.SHA)
        )
        if (!sp.contains(getString(R.string.HashCount))) editor.putInt(
            getString(R.string.HashCount),
            1
        )
        if (!sp.contains(getString(R.string.CipherAlgorithm))) editor.putString(
            getString(R.string.CipherAlgorithm),
            getString(R.string.AES)
        )
        if (!sp.contains(getString(R.string.CipherCount))) editor.putInt(
            getString(R.string.CipherCount),
            1
        )
        if (!sp.contains(getString(R.string.BCM))) editor.putString(
            getString(R.string.BCM),
            getString(R.string.CBC)
        )
        if (!sp.contains(getString(R.string.Padding))) editor.putString(
            getString(R.string.Padding),
            getString(R.string.PKCS5Padding)
        )
        if (!sp.contains(getString(R.string.Salt))) editor.putBoolean(
            getString(R.string.Salt),
            false
        )
        if (!sp.contains(getString(R.string.SecordPassword))) editor.putBoolean(
            getString(R.string.SecordPassword),
            false
        )
        if (!sp.contains(getString(R.string.DeleteFile))) editor.putBoolean(
            getString(R.string.DeleteFile),
            false
        )
        if (!sp.contains(getString(R.string.keySize))) editor.putInt(
            getString(R.string.keySize),
            32
        )
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