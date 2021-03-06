package com.adamkis.pizza.dagger.glide

import android.support.v7.widget.RecyclerView
import com.adamkis.pizza.ui.adapter.PizzasAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        GlideModule::class))
interface GlideComponent {
    fun inject(viewHolder: RecyclerView.ViewHolder)
    fun inject(pizzasAdapter: PizzasAdapter)
}