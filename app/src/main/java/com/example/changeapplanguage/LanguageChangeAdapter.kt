package com.example.changeapplanguage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.changeapplanguage.databinding.ItemLanguageChangeBinding

class LanguageChangeAdapter(private var listener: ICountryPrefListener) :
    RecyclerView.Adapter<LanguageChangeAdapter.LanguageViewHolder>() {

    var countryNamesList = mutableListOf<CountryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemBinding =
            ItemLanguageChangeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return LanguageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {

        val item = countryNamesList[position]
        holder.bind(item, position)


    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return countryNamesList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class LanguageViewHolder(private val itemBinding: ItemLanguageChangeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(item: CountryModel, pos: Int) {


            if (Constants.languagePos == pos) {

                itemBinding.ivLanguageSelector.post {
                    itemBinding.ivLanguageSelector.setBackgroundResource(0)
                    itemBinding.ivLanguageSelector.setBackgroundResource(R.drawable.ic_lang_selected)
                }
            }

            itemBinding.tvLanguage.text = item.name


            if (item.selected) {
                itemBinding.ivLanguageSelector.setBackgroundResource(0)
                itemBinding.ivLanguageSelector.setBackgroundResource(R.drawable.ic_lang_selected)
            } else {
                itemBinding.ivLanguageSelector.setBackgroundResource(0)
                itemBinding.ivLanguageSelector.setBackgroundResource(R.drawable.ic_lang_not_selected)
            }

            itemBinding.ivLanguageSelector.setOnClickListener {
                listener.onItemSelected(itemBinding.ivLanguageSelector, adapterPosition)
            }

        }

    }

    fun setLanguagePreferences(country: List<CountryModel>) {
        this.countryNamesList = country.toMutableList()
    }
}