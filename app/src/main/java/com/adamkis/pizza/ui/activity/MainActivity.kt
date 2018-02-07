package com.adamkis.pizza.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.model.Basket
import com.adamkis.pizza.ui.fragment.PizzasFragment
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var activeFragment: Fragment? = null
    private val ACTIVE_FRAGMENT_KEY = "ACTIVE_FRAGMENT_KEY"

    private var basket: Basket?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(
            if(savedInstanceState != null) supportFragmentManager.getFragment(savedInstanceState, ACTIVE_FRAGMENT_KEY)
            else PizzasFragment.newInstance()
        )
    }

    override fun onResume() {
        super.onResume()
        loadBasket()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, ACTIVE_FRAGMENT_KEY, activeFragment);
    }

    private fun replaceFragment(fragment: Fragment){
        activeFragment = fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, activeFragment).commit()
    }

    private fun loadBasket() {
        val basketBook: RxPaperBook = RxPaperBook.with(FilePersistenceHelper.PAPER_BASKET_KEY, Schedulers.newThread())
        val emptyBasket = Basket()
        val readOrDefault = basketBook.read(FilePersistenceHelper.PAPER_BASKET_KEY, emptyBasket)
                .observeOn(AndroidSchedulers.mainThread())
        readOrDefault.subscribe(object : SingleObserver<Basket> {
            override fun onSubscribe(d: Disposable) {

            }
            override fun onSuccess(basketFromData: Basket) {
                basket = basketFromData
                updateBasket(basketFromData)

            }

            override fun onError(error: Throwable) {
                // Operation failed
            }
        })
    }

    fun updateBasket(basket: Basket){
        basket_item_count.text = basket.itemCount.toString()
    }


}

// TODO modify created by...
// TODO No pizza image  - handle it
