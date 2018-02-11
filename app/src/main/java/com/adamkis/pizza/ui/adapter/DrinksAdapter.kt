package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Drink
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.drink_item.view.*

class DrinksAdapter(val cart: Cart, val drinksResponse: Array<Drink>, val context: Context) : RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder>(){

    private val clickSubject = PublishSubject.create<Drink>()
    val clickEvent: Observable<Drink> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DrinksViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.drink_item, parent, false)
        return DrinksViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder?, position: Int) {
        holder?.bind(drinksResponse[position])
    }

    override fun getItemCount(): Int = drinksResponse.size

    inner class DrinksViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view){

        fun bind(drink: Drink){
            itemView.drink_name.text = drink?.name
            itemView.drink_price.text = drink?.price.toString()
            itemView.add_item.setOnClickListener {
                cart.addOrderItem(drink)
                clickSubject.onNext(drink)
            }
        }

    }

}