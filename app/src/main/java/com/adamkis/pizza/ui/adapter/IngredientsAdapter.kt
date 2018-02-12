package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsAdapter(val pizza: Pizza?, val ingredientsAvailable: List<Ingredient>?, val context: Context) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>(){

    private val clickSubject = PublishSubject.create<Double>()
    val clickEvent: Observable<Double> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientsViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder?, position: Int) {
        holder?.bind(ingredientsAvailable?.get(position))
    }

    override fun getItemCount(): Int = ingredientsAvailable?.size ?: 0

    inner class IngredientsViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view){

        fun bind(ingredient: Ingredient?){
            itemView.ingredient_name.text = ingredient?.name
            itemView.ingredient_price.text = context.getString(R.string.price_with_currency, ingredient?.price)
            itemView.ingredient_selected.isChecked = pizza?.getIngredientIds()?.contains(ingredient?.id) ?: false
            itemView.ingredient_selected.setOnClickListener {
                if( itemView.ingredient_selected.isChecked ){
                    pizza?.addIngredient(ingredient)
                }
                else{
                    pizza?.removeIngredient(ingredient)
                }
                clickSubject.onNext(pizza?.getItemPrice() ?: 0.0)
            }
        }

    }

}