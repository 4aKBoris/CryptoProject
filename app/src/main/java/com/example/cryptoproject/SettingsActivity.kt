@file:Suppress("DEPRECATION")

package com.example.cryptoproject

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoproject.Fragments.SettingsFragments.CipherFragment
import com.example.cryptoproject.Fragments.SettingsFragments.HashFragment
import com.example.cryptoproject.Fragments.SettingsFragments.OtherFragment
import com.example.cryptoproject.Fragments.SettingsFragments.SignatureFragment
import com.example.cryptoproject.Сonstants.NumberPage


@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING"
)
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        Adapter = MyAdapter(supportFragmentManager, lifecycle)
        ListPager = findViewById(R.id.viewPager2)
        ListPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        ListPager.adapter = Adapter
        sp = getDefaultSharedPreferences(this)
    }

    override fun onResume() {
        super.onResume()
        ListPager.currentItem = sp.getInt(NumberPage, 0)
    }

    override fun onPause() {
        super.onPause()
        val editor = sp.edit()
        editor.putInt(NumberPage, ListPager.currentItem)
        editor.apply()
    }

    companion object {
        private lateinit var sp: SharedPreferences
        private lateinit var Adapter: MyAdapter
        private lateinit var ListPager: ViewPager2

        class MyAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
            FragmentStateAdapter(fragmentManager, lifecycle) {

            override fun getItemCount(): Int = 4

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HashFragment()
                    1 -> CipherFragment()
                    2 -> SignatureFragment()
                    3 -> OtherFragment()
                    else -> HashFragment()
                }
            }

        }
    }


}
