package com.adamkis.pizza.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.OrderItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.cart_item.view.*


class CartAdapter(val cart: Cart, val context: Context) : RecyclerView.Adapter<CartAdapter.RecentsViewHolder>(){

    private val clickSubject = PublishSubject.create<Double>()
    val clickEvent: Observable<Double> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecentsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cart_item, parent, false)
        return RecentsViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecentsViewHolder?, position: Int) {
        holder?.bind(cart.orderItems[position])
    }

    override fun getItemCount(): Int = cart.orderItems.size

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
                cart.orderItems.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
                clickSubject.onNext(cart.getTotalPrice())
            }
        }

    }

}