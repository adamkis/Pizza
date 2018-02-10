package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Drink
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(val pizza: Pizza?, val ingredientsAvailable: List<Ingredient>?, val context: Context) : RecyclerView.Adapter<IngredientsAdapter.RecentsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecentsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.ingredient_item, parent, false)
        return RecentsViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecentsViewHolder?, position: Int) {
        holder?.bind(ingredientsAvailable?.get(position))
    }

    override fun getItemCount(): Int = ingredientsAvailable?.size ?: 0

    inner class RecentsViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
//            itemView.setOnClickListener {
//                // TODO: nullcheck
//                clickSubject.onNext(Pair<Pizza, View>(pizzas!![layoutPosition], view))
//            }
        }

        fun bind(ingredient: Ingredient?){
            itemView.ingredient_name.text = ingredient?.name
            itemView.ingredient_price.text = ingredient?.price.toString()
            itemView.ingredient_selected.isChecked = pizza?.getIngredientIds()?.contains(ingredient?.id) ?: false
            itemView.ingredient_selected.setOnClickListener {
                if( itemView.ingredient_selected.isChecked ){
                    pizza?.addIngredient(ingredient)
                    pizza?.addIngredientId(ingredient?.id)
                }
                else{
                    pizza?.removeIngredient(ingredient)
                    pizza?.removeIngredientId(ingredient?.id)
                }
            }
        }

    }

}