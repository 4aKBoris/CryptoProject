@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.CustomView

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.Cansel


class CustomSelectAlgrorithm : LinearLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeViews(context)
        Title(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context,
        attrs,
        defStyle) {
        initializeViews(context)
        Title(context, attrs)
    }

    private fun initializeViews(context: Context) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_select_algorithm, this)
    }

    lateinit var text: String

    @SuppressLint("CustomViewStyleable", "Recycle")
    private fun Title(context: Context, attrs: AttributeSet) {
        val atr = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)
        TitleText = findViewById(R.id.title_text)
        TitleText.text = atr.getString(R.styleable.CustomFontTextView_title_text)
        Text = findViewById(R.id.text_view)
    }

    fun setSelectText(select_text: String) {
        Text.text = select_text
        text = select_text
        println(select_text)
    }

    fun OnClick(context: Context, list: List<String>, title: String) {

        val Click = DialogInterface.OnClickListener { _, which ->
            text = list[which]
            Text.text = text
            Text.text.toString()
        }

        AlertDialog.Builder(context).setTitle(title)
            .setCancelable(false)
            .setAdapter(
                ArrayAdapter(context, android.R.layout.simple_list_item_1, list),
                Click
            )
            .setNegativeButton(
                Cansel
            ) { dialog, _ -> dialog.cancel() }.create()
            .show()
    }


    companion object {
        private lateinit var Text: TextView
        private lateinit var TitleText: TextView
    }

}