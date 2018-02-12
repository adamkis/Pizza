package com.adamkis.pizza.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.fragment.PizzaDetailFragment
import kotlinx.android.synthetic.main.activity_pizza_detail.*


/**
 * Created by adam on 2018. 01. 11..
 */
class PizzaDetailActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)

        val pizza: Pizza? = intent.getParcelableExtra(Pizza.TAG)
        val ingredientsHM: HashMap<Int?, Ingredient>? = intent.getSerializableExtra(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?

        setupToolbar(R.id.toolbar)
        setupBackButton()

        pizza_name.text = if (pizza?.name.isNullOrBlank()) getString(R.string.custom_pizza) else pizza?.name
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PizzaDetailFragment.newInstance(pizza, ingredientsHM)).commit()
        }
    }

    companion object {
        // TODO use Pizza.TAG
        private val ARG_INGREDIENTS = "ARG_INGREDIENTS"

        fun getStartIntent(context: Context, pizza: Pizza, ingredientsHM: HashMap<Int?, Ingredient>?): Intent {
            return Intent(context, PizzaDetailActivity::class.java)
                    .apply {
                        putExtra(Pizza.TAG, pizza)
                        putExtra(ARG_INGREDIENTS, ingredientsHM)
                    }
        }

        fun getStartIntentNewPizza(context: Context, ingredientsHM: HashMap<Int?, Ingredient>?): Intent {
            return Intent(context, PizzaDetailActivity::class.java)
                    .apply { putExtra(ARG_INGREDIENTS, ingredientsHM) }
        }

    }

}