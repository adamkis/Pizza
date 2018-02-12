package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Pizza
import com.bumptech.glide.RequestManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_pizza.view.*
import javax.inject.Inject

/**
 * Created by adam on 2018. 01. 09..
 */
class PizzasAdapter(val pizzas: Array<Pizza>?, val context: Context) : RecyclerView.Adapter<PizzasAdapter.PizzasViewHolder>(){

    @Inject lateinit var glideReqManager: RequestManager
    private val clickSubject = PublishSubject.create<Pair<Pizza, View>>()
    val clickEvent: Observable<Pair<Pizza, View>> = clickSubject

    init {
        App.glideComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PizzasViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_pizza, parent, false)
        return PizzasViewHolder(glideReqManager, view, context)
    }

    override fun onBindViewHolder(holder: PizzasViewHolder?, position: Int) {
        holder?.bind(pizzas?.get(position))
    }

    override fun getItemCount(): Int = pizzas?.size ?: 0

    inner class PizzasViewHolder(val glideReqManager: RequestManager, view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
            itemView.setOnClickListener {
                pizzas?.let { clickSubject.onNext(Pair<Pizza, View>(it[layoutPosition], view)) }
            }
        }

        fun bind(pizza: Pizza?){
            itemView.pizza_name.text = pizza?.name
            itemView.pizza_ingredients.text = pizza?.getIngredients()?.
                    map { it.name }?.
                    joinToString(", ")
            itemView.price.text = context.getString(R.string.price_with_currency, pizza?.getItemPrice())
            glideReqManager.load(pizza?.imageUrl).into(itemView.findViewById(R.id.pizza_image))
        }

    }

}