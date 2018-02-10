package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Drink
import com.adamkis.pizza.model.OrderItem
import kotlinx.android.synthetic.main.drink_item.view.*

class DrinksAdapter(val cart: Cart, val drinksResponse: Array<Drink>, val context: Context) : RecyclerView.Adapter<DrinksAdapter.RecentsViewHolder>(){

//    private val clickSubject = PublishSubject.create<Pair<Pizza, View>>()
//    val clickEvent: Observable<Pair<Pizza, View>> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecentsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.drink_item, parent, false)
        return RecentsViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecentsViewHolder?, position: Int) {
        holder?.bind(drinksResponse?.get(position))
    }

    override fun getItemCount(): Int = drinksResponse?.size

    inner class RecentsViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
//            itemView.setOnClickListener {
//                // TODO: nullcheck
//                clickSubject.onNext(Pair<Pizza, View>(pizzas!![layoutPosition], view))
//            }
        }

        fun bind(drink: Drink?){
            itemView.drink_name.text = drink?.name
            itemView.drink_price.text = drink?.price.toString()
            itemView.add_item.setOnClickListener {
                cart.addOrderItem(drink)
                // TODO show notification on the bottom
            }
        }

    }

}