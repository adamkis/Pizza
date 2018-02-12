package com.adamkis.pizza.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.adamkis.pizza.R
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.fragment.PizzaDetailFragment
import kotlinx.android.synthetic.main.activity_pizza_detail.*

class PizzaDetailActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)

        val pizza: Pizza? = intent.getParcelableExtra(ARG_PIZZA)
        val ingredientsHM: HashMap<Int?, Ingredient>? = intent.getSerializableExtra(ARG_INGREDIENTS) as HashMap<Int?, Ingredient>?

        setupToolbar(R.id.toolbar)
        setupBackButton()

        pizza_name.text = if (pizza?.name.isNullOrBlank()) getString(R.string.custom_pizza) else pizza?.name
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PizzaDetailFragment.newInstance(pizza, ingredientsHM)).commit()
        }
    }

    companion object {

        private val ARG_INGREDIENTS = "ARG_INGREDIENTS"
        private val ARG_PIZZA = "ARG_PIZZA"

        fun getStartIntent(context: Context, pizza: Pizza, ingredientsHM: HashMap<Int?, Ingredient>?): Intent {
            return Intent(context, PizzaDetailActivity::class.java)
                    .apply {
                        putExtra(ARG_PIZZA, pizza)
                        putExtra(ARG_INGREDIENTS, ingredientsHM)
                    }
        }

        fun getStartIntentNewPizza(context: Context, ingredientsHM: HashMap<Int?, Ingredient>?): Intent {
            return Intent(context, PizzaDetailActivity::class.java)
                    .apply { putExtra(ARG_INGREDIENTS, ingredientsHM) }
        }

    }

}