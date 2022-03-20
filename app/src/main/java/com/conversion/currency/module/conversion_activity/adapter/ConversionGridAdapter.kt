package com.androiddevs.mvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.conversion.currency.R
import com.conversion.currency.module.conversion_activity.model.CurrencyLookup
import kotlinx.android.synthetic.main.item_currency_grid.view.*

class ConversionGridAdapter(
    var selectRate: Double = 1.0,
    var targetPrice: Double = 1.0
) : RecyclerView.Adapter<ConversionGridAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<CurrencyLookup>() {
        override fun areItemsTheSame(oldItem: CurrencyLookup, newItem: CurrencyLookup): Boolean {
            return oldItem.rate == newItem.rate
        }

        override fun areContentsTheSame(oldItem: CurrencyLookup, newItem: CurrencyLookup): Boolean {
            return oldItem != newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_currency_grid,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currencyInfo = differ.currentList[position]
        holder.itemView.apply {
            tvCurrency.text = currencyInfo.code
            tvRate.text = String.format("%.2f", calculatePrice(currencyInfo))
        }
    }

    private fun calculatePrice(data: CurrencyLookup): Double {
        return data.rate * targetPrice / selectRate
    }

    fun updateList(targetPrice: Double, selectRate: Double) {
        this.selectRate = selectRate
        this.targetPrice = targetPrice
        notifyDataSetChanged()
    }
}













