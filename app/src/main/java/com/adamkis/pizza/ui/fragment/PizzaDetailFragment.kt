package com.adamkis.pizza.ui.fragment

import android.content.Context
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
import com.adamkis.pizza.model.Cart
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.adapter.IngredientsAdapter
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader
import io.paperdb.Paper
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_pizza_detail.*
import kotlinx.android.synthetic.main.header_pizza_detail.*
import java.util.*
import kotlin.collections.ArrayList

class PizzaDetailFragment : Fragment() {

    private var pizza: Pizza? = null
    private var originalIngredientIds: ArrayList<Int>? = ArrayList()
    private var ingredientsHM: HashMap<Int?, Ingredient>? = null
    private var clickDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if( savedInstanceState != null ){
            pizza = savedInstanceState.getParcelable(ARG_PIZZA)
            ingredientsHM = savedInstanceState.getSerializable(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?
            originalIngredientIds = savedInstanceState.getSerializable(ARG_ORIGINAL_INGREDIENTS) as ArrayList<Int>?
        }
        else {
            arguments?.let {
                arguments ->
                    pizza = arguments.getParcelable(ARG_PIZZA)
                    pizza?.let { pizza -> originalIngredientIds = ArrayList(pizza?.getIngredientIds()) }
                    ingredientsHM = arguments.getSerializable(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pizza_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up RecyclerView and header
        val header: RecyclerViewHeader = view.findViewById<RecyclerViewHeader>(R.id.header) as RecyclerViewHeader
        val ingredientsRecyclerView: RecyclerView = view.findViewById(R.id.ingredients_recycler_view)
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(activity as Context, LinearLayout.VERTICAL, false)
        header.attachTo(ingredientsRecyclerView)

        if( pizza == null ){
            Paper.book().delete(FilePersistenceHelper.HEADER_IMAGE_KEY)
            pizza = Pizza("", null, ArrayList(), ArrayList())
        }
        else{
            header_image.setImageBitmap(Paper.book().read(FilePersistenceHelper.HEADER_IMAGE_KEY))
        }
        if( pizza?.name.isNullOrEmpty() ){
            header_image.setImageResource(R.drawable.custom)
        }

        setUpAdapter(ingredientsRecyclerView, pizza, ingredientsHM?.map { it.value })

        add_to_cart.setPriceText(pizza?.getItemPrice())
        add_to_cart.setOnClickListener {
            addPizzaToCart(pizza)
            add_to_cart.showAddedToCartFlash()
        }

    }

    private fun setUpAdapter(ingredientsRecyclerView: RecyclerView, pizza: Pizza?, ingredientsAvailable: List<Ingredient>?) {
        ingredientsRecyclerView.adapter = IngredientsAdapter(pizza, ingredientsAvailable, activity as Context)
        clickDisposable = (ingredientsRecyclerView.adapter as IngredientsAdapter).clickEvent
                .subscribe({ add_to_cart.setPriceText(it) })
    }

    private fun addPizzaToCart(pizza: Pizza?) {
        Collections.sort(pizza?.getIngredientIds())
        Collections.sort(originalIngredientIds)
        if( pizza?.name?.contains(activity?.getString(R.string.custom) ?: "") != true &&
                ( pizza?.getIngredientIds()?.equals(originalIngredientIds) != true ||
                        pizza?.getIngredientIds()?.isEmpty() == true  )
            ){
            pizza?.name = activity?.getString(R.string.custom_pizza_name, pizza?.name)
        }
        var cart: Cart = Paper.book().read(FilePersistenceHelper.PAPER_CART_KEY, Cart())
        cart.addOrderItem(pizza)
        Paper.book().write(FilePersistenceHelper.PAPER_CART_KEY, cart)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(ARG_PIZZA, pizza)
        outState.putSerializable(ARG_INGREDIENTS, ingredientsHM)
        outState.putSerializable(ARG_ORIGINAL_INGREDIENTS, originalIngredientIds)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        clickDisposable?.dispose()
        super.onDestroy()
    }

    companion object {

        private val ARG_PIZZA = "ARG_PIZZA"
        private val ARG_INGREDIENTS = "ARG_INGREDIENTS"
        private val ARG_ORIGINAL_INGREDIENTS = "ARG_ORIGINAL_INGREDIENTS"

        fun newInstance(pizza: Pizza?, ingredientsHM: HashMap<Int?, Ingredient>?): PizzaDetailFragment {
            val fragment = PizzaDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PIZZA, pizza)
            args.putSerializable(ARG_INGREDIENTS, ingredientsHM)
            fragment.arguments = args
            return fragment
        }

    }

}