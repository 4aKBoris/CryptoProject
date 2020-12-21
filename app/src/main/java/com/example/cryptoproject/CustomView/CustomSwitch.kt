@file:Suppress("PackageName")

package com.example.cryptoproject.CustomView

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.example.cryptoproject.R

class CustomSwitch : LinearLayout {

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
        inflater.inflate(R.layout.custom_switch, this)
    }

    lateinit var text: String

    @SuppressLint("CustomViewStyleable", "Recycle")
    private fun Title(context: Context, attrs: AttributeSet) {
        val atr = context.obtainStyledAttributes(attrs, R.styleable.CustomSwitchText)
        SwitchFlag = findViewById(R.id.switch_flag)
        SwitchFlag.text = atr.getString(R.styleable.CustomSwitchText_switch_text)
        Text = findViewById(R.id.text_flag)
        SwitchFlag.setOnCheckedChangeListener { _, isChecked ->
            flag = isChecked
            Text.text = yesNo[flag]
        }
    }

    fun setFlag(fl: Boolean) {
        flag = fl
        SwitchFlag.isChecked = flag
        Text.text = yesNo[flag]
    }

    fun getFlag(): Boolean {
        return flag
    }

    companion object {
        private var flag = false
        private lateinit var Text: TextView

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private lateinit var SwitchFlag: Switch
        private val yesNo = mapOf(Pair(true, "Да"), Pair(false, "Нет"))
    }

}