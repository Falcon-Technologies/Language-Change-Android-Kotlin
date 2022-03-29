package com.example.changeapplanguage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.changeapplanguage.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityMainBinding
   lateinit var pref:SharePrefUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



        mBinding.tvHelloWorld.setOnClickListener {
           startActivity(Intent(this,ChangeLanguage::class.java))
            finishAffinity()
        }
    }


    override fun onResume() {
        super.onResume()

        pref = SharePrefUtil(this)
        val langIndex: Int = pref.appLanguage
        if (langIndex != -1) {
            val lang = Constants.appLanguages[langIndex]
            setLocale(this, lang.id)
        } else {
            for (i in Constants.appLanguages.indices) {
                if (Constants.appLanguages[i].id == Locale.getDefault().language) {
                    pref.appLanguage = i
                }
            }
        }
    }

    fun setLocale(context: Context, locale: String) {
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(locale))
        res.updateConfiguration(conf, dm)
    }

}