package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.OrderItem
import com.adamkis.pizza.model.Pizza
import com.bumptech.glide.RequestManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.cart_item.view.*
import javax.inject.Inject


class CartAdapter(val orderItems: MutableList<OrderItem>?, val context: Context) : RecyclerView.Adapter<CartAdapter.RecentsViewHolder>(){

//    private val clickSubject = PublishSubject.create<Pair<Pizza, View>>()
//    val clickEvent: Observable<Pair<Pizza, View>> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecentsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cart_item, parent, false)
        return RecentsViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecentsViewHolder?, position: Int) {
        holder?.bind(orderItems?.get(position))
    }

    override fun getItemCount(): Int = orderItems?.size ?: 0

    inner class RecentsViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
//            itemView.setOnClickListener {
//                // TODO: nullcheck
//                clickSubject.onNext(Pair<Pizza, View>(pizzas!![layoutPosition], view))
//            }
        }

        fun bind(orderItem: OrderItem?){
            itemView.order_item_name.text = orderItem?.getItemName()
            itemView.order_item_price.text = orderItem?.getItemPrice().toString()
            itemView.remove_item.setOnClickListener {
                orderItems?.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }
        }

    }

}