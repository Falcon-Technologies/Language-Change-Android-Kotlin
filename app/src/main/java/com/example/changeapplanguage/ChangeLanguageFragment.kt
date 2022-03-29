package com.example.changeapplanguage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.changeapplanguage.databinding.LanguageChangeBinding
import java.util.*


class ChangeLanguage : AppCompatActivity(), ICountryPrefListener {

    lateinit var pref: SharePrefUtil
    private lateinit var item: CountryModel
    private var langPos: Int = -1
    private lateinit var adapter: LanguageChangeAdapter
    private lateinit var countryLanguageRecyclerView: RecyclerView
    private lateinit var binding: LanguageChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LanguageChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)




        pref = SharePrefUtil(this)
        val langIndex: Int = pref.appLanguage
        if (langIndex != -1) {
            val lang = Constants.appLanguages[langIndex]
            setLocale(lang.id)
        } else {
            for (i in Constants.appLanguages.indices) {
                if (Constants.appLanguages[i].id == Locale.getDefault().language) {
                    pref.appLanguage = i
                }
            }
        }


        Constants.languagePos = pref.appLanguage

        setUpLanguageRecycler()
        loadAllLanguages()
    }


    private fun setUpLanguageRecycler() {
        countryLanguageRecyclerView = binding.recyclerLanguageChange
        val favoritesLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        countryLanguageRecyclerView.setHasFixedSize(true)
        countryLanguageRecyclerView.layoutManager = favoritesLayoutManager

    }

    private fun loadAllLanguages() {
        adapter = LanguageChangeAdapter(this)
        adapter.setLanguagePreferences(Constants.appLanguages)
        countryLanguageRecyclerView.adapter = adapter
    }

    override fun onItemSelected(view: View, position: Int) {

        item = adapter.countryNamesList[position]
        if (!adapter.countryNamesList[position].selected) {
            langPos = position
            showLanguageDialog()
            val holder = countryLanguageRecyclerView.findViewHolderForAdapterPosition(position)
            holder?.itemView?.findViewById<View>(R.id.ivLanguageSelector)
                ?.setBackgroundResource(R.drawable.ic_lang_selected)
            adapter.countryNamesList[position].selected = true
            Constants.languagePos = langPos

            try {
                adapter.countryNamesList.forEachIndexed { index, _ ->
                    if (position != RecyclerView.NO_POSITION && index != position) {
                        val holder1 =
                            countryLanguageRecyclerView.findViewHolderForAdapterPosition(index)
                        holder1?.itemView?.findViewById<View>(R.id.ivLanguageSelector)
                            ?.setBackgroundResource(R.drawable.ic_lang_not_selected)
                        adapter.countryNamesList[index].selected = false
                    }
                }
            } catch (exception: IndexOutOfBoundsException) {
                exception.printStackTrace()
            }


        }

    }

    private fun showLanguageDialog() {
        val builder = AlertDialog.Builder(this, R.style.alertDialogDay)
        builder.setTitle(resources.getString(R.string.change_language))
        builder.setMessage(resources.getString(R.string.change_language_desc))
        builder.setCancelable(false)

        builder.setPositiveButton(resources.getString(R.string.restart)) { dialog, _ ->
            pref.appLanguage = langPos
            dialog.dismiss()
            this.finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }

        builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
            Constants.languagePos = pref.appLanguage
            adapter.countryNamesList.forEachIndexed { index, _ ->
                val holder = countryLanguageRecyclerView.findViewHolderForAdapterPosition(index)
                holder?.itemView?.findViewById<View>(R.id.ivLanguageSelector)
                    ?.setBackgroundResource(R.drawable.ic_lang_not_selected)
                adapter.countryNamesList[index].selected = false
            }

            val holder =
                countryLanguageRecyclerView.findViewHolderForAdapterPosition(pref.appLanguage)
            holder?.itemView?.findViewById<View>(R.id.ivLanguageSelector)
                ?.setBackgroundResource(R.drawable.ic_lang_selected)
            adapter.countryNamesList[pref.appLanguage].selected = true
        }
        builder.show()
    }


    private fun setLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }
}