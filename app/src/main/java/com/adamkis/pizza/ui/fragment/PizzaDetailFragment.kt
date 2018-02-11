package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.adapter.IngredientsAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_pizza_detail.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by adam on 2018. 01. 11..
 */
class PizzaDetailFragment : Fragment() {

    private var pizza: Pizza? = null
    private var originalIngredientIds: TreeSet<Int>? = null
    private var ingredientsHM: HashMap<Int?, Ingredient>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if( savedInstanceState != null ){
            pizza = savedInstanceState.getParcelable(ARG_PIZZA)
            ingredientsHM = savedInstanceState.getSerializable(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?
            originalIngredientIds = savedInstanceState.getSerializable(ARG_ORIGINAL_INGREDIENTS) as TreeSet<Int>?
        }
        else {
            arguments?.let {
                pizza = it.getParcelable(ARG_PIZZA)
                originalIngredientIds = TreeSet(pizza?.getIngredientIds())
                ingredientsHM = it.getSerializable(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pizza_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ingredientsRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.ingredients_recycler_view)
        val bitmap: Bitmap? = FilePersistenceHelper.loadBitmapFromFile(activity as Context)
        header_image.setImageBitmap(bitmap)
        setUpAdapter(ingredientsRecyclerView, pizza, ingredientsHM?.map { it.value })
        add_to_cart.setOnClickListener {
            addPizzaToCart(pizza)
        }
    }

    private fun setUpAdapter(ingredientsRecyclerView: RecyclerView, pizza: Pizza?, ingredientsAvailable: List<Ingredient>?) {
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(activity as Context, LinearLayout.VERTICAL, false)
        ingredientsRecyclerView.adapter = IngredientsAdapter(pizza, ingredientsAvailable, activity as Context)
    }

    private fun addPizzaToCart(pizza: Pizza?) {
        if(pizza?.getIngredientIds()?.equals(originalIngredientIds) != true){
            pizza?.name = activity?.getString(R.string.custom_pizza_name, pizza?.name)
        }
        var cart: Cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart.addOrderItem(pizza)
        Paper.book().write(FilePersistenceHelper.PAPER_CART_KEY, cart)
        logDebug("Cart updated: " + cart)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(ARG_PIZZA, pizza)
        outState.putSerializable(ARG_INGREDIENTS, ingredientsHM)
        outState.putSerializable(ARG_ORIGINAL_INGREDIENTS, originalIngredientIds)
        super.onSaveInstanceState(outState)
    }

    companion object {

        // TODO use Pizza.TAG
        private val ARG_PIZZA = "ARG_PIZZA"
        private val ARG_INGREDIENTS = "ARG_INGREDIENTS"
        private val ARG_ORIGINAL_INGREDIENTS = "ARG_ORIGINAL_INGREDIENTS"

        fun newInstance(pizza: Pizza, ingredientsHM: HashMap<Int?, Ingredient>?): PizzaDetailFragment {
            val fragment = PizzaDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PIZZA, pizza)
            args.putSerializable(ARG_INGREDIENTS, ingredientsHM)
            fragment.arguments = args
            return fragment
        }

    }

}