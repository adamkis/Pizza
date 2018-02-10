package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.view.IngredientChooser
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_pizza_detail.*


/**
 * Created by adam on 2018. 01. 11..
 */
class PizzaDetailFragment : Fragment() {

    private var pizza: Pizza? = null
    private var ingredientsHM: HashMap<Int?, Ingredient>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pizza = it.getParcelable(ARG_PIZZA)
            ingredientsHM = it.getSerializable(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pizza_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap: Bitmap? = FilePersistenceHelper.loadBitmapFromFile(activity as Context)
        header_image.setImageBitmap(bitmap)
        showIngredients(container, pizza, ingredientsHM)
        add_to_cart.setOnClickListener {
            addPizzaToCart(pizza)
        }

    }

    private fun addPizzaToCart(pizza: Pizza?) {
        var cart: Cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart.addOrderItem(pizza)
        Paper.book().write(FilePersistenceHelper.PAPER_CART_KEY, cart)
        logDebug("Cart updated: " + cart)
    }

    private fun showIngredients(container: ViewGroup, pizza: Pizza? , ingredientsHM: HashMap<Int?, Ingredient>?){
        if (pizza == null) return
        ingredientsHM?.let {
            for ( ingredient in ingredientsHM.values){
                val ingredientChooser = IngredientChooser(context!!)
                ingredientChooser.setName(ingredient.name)
                ingredientChooser.setPrice(ingredient.price)
                ingredientChooser.setIngredientSelected(pizza.ingredientIds?.contains(ingredient.id ?: Int.MAX_VALUE))
                container.addView(ingredientChooser)
            }
        }

    }

    companion object {

        // TODO use Pizza.TAG
        private val ARG_PIZZA = "ARG_PIZZA"
        private val ARG_INGREDIENTS = "ARG_INGREDIENTS"

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