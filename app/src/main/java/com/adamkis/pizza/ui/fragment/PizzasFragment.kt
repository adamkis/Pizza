package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adamkis.pizza.App
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.model.Ingredient
import com.adamkis.pizza.model.PizzasResponse
import com.adamkis.pizza.network.RestApi
import com.adamkis.pizza.ui.adapter.PizzasAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject


class PizzasFragment : BaseFragment() {

    @Inject lateinit var restApi: RestApi
    private var clickDisposable: Disposable? = null
    private var callDisposable: Disposable? = null
    private var pizzasResponse: PizzasResponse? = null
    private val PIZZAS_RESPONSE_KEY = "PIZZAS_RESPONSE_KEY"

    companion object {
        fun newInstance(): PizzasFragment {
            val fragment = PizzasFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_recents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)
        val pizzasRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.pizzas_recycler_view)
        pizzasResponse = savedInstanceState?.getParcelable(PIZZAS_RESPONSE_KEY)
        if(pizzasResponse != null){
            setUpAdapter(pizzasRecyclerView, pizzasResponse!!)
            showLoading(false)
        }
        else{
//            downloadIngredients()
            downloadData(pizzasRecyclerView)
        }
    }

    private fun downloadIngredients(){
        callDisposable = restApi.getIngredients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {ingredients ->
                    logDebug("Ingredients")
                    for (ingredient in ingredients){
                        logDebug(ingredient.name)
                    }
                },
                {t ->
                    when(t){
                        is UnknownHostException -> {
                            showError(getString(R.string.network_error))
                        }
                        is NullPointerException -> {
                            showError(getString(R.string.could_not_load_data))
                        }
                        else -> {
                            showError(getString(R.string.error))
                        }
                    }
                }
            )
    }

    private fun downloadData(pizzasRecyclerView: RecyclerView){

//        val search1 = search("RxJava")
//        val search2 = search("Reactive Extensions")
//        val search3 = search("Eric Meijer")
//        Observable.zip(searc1, search2, search3,
//                object : Func3<Elements, Elements, Elements, Elements>() {
//                    fun call(result1: Elements, result2: Elements, result3: Elements): Elements {
//                        // Add all the results together...
//                        return results
//                    }
//                }
//        ).subscribeOn(Schedulers.io()).subscribe(
//                { links ->
//                    links.forEach { link -> out.println(currentThreadName() + "\t" + link.text()) }
//                    latch.countDown()
//                },
//                { e ->
//                    out.println(currentThreadName() + "\t" + "Failed: " + e.message)
//                    latch.countDown()
//                }
//        )

        val pizzas = restApi.getPizzas()
        val ingredients = restApi.getIngredients()
        Observable.zip(ingredients, pizzas,
                object : BiFunction<Array<Ingredient>, PizzasResponse, Pair<Array<Ingredient>, PizzasResponse>> {
                    override fun apply(t1: Array<Ingredient>, t2: PizzasResponse): Pair<Array<Ingredient>, PizzasResponse> {
                        // TODO rename parameters
                        return Pair(t1, t2)
                    }
                })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doAfterTerminate { showLoading(false) }
            .subscribe(
                    {responsePair ->
//                        this@PizzasFragment.pizzasResponse = pizzasResponse
//                        setUpAdapter(pizzasRecyclerView, pizzasResponse)
                        // TODO remove
                        logDebug("Logging responses")
                        logDebug(responsePair.first.map { it.name }.toString())
                        logDebug(responsePair.second.pizzas?.map { it.name }.toString())
                    },
                    {t ->
                        when(t){
                            is UnknownHostException -> {
                                showError(getString(R.string.network_error))
                            }
                            is NullPointerException -> {
                                showError(getString(R.string.could_not_load_data))
                            }
                            else -> {
                                showError(getString(R.string.error))
                            }
                        }
                    }
            )

//        val ingredients = restApi.getIngredients()
//        val pizzas = restApi.getPizzas()
//        Observable.zip(ingredients, pizzas, BiFunction {
//            ingredientsResponse, pizzasResponse ->
//            sum / BigDecimal.valueOf(count)
//        })

//        callDisposable = restApi.getPizzas()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { showLoading(true) }
//            .doAfterTerminate { showLoading(false) }
//            .subscribe(
//                {pizzasResponse ->
//                    this@PizzasFragment.pizzasResponse = pizzasResponse
//                    setUpAdapter(pizzasRecyclerView, pizzasResponse)
//                    // TODO remove
//                    logDebug("FIRST RESPONSE")
//                    logDebug(pizzasResponse.basePrice.toString())
//                },
//                {t ->
//                    when(t){
//                        is UnknownHostException -> {
//                            showError(getString(R.string.network_error))
//                        }
//                        is NullPointerException -> {
//                            showError(getString(R.string.could_not_load_data))
//                        }
//                        else -> {
//                            showError(getString(R.string.error))
//                        }
//                    }
//                }
//            )
    }

    private fun setUpAdapter(pizzasRecyclerView: RecyclerView, pizzasResponse: PizzasResponse){
        pizzasRecyclerView.layoutManager = LinearLayoutManager(this@PizzasFragment.activity, LinearLayout.VERTICAL, false)
        pizzasRecyclerView.adapter = PizzasAdapter(pizzasResponse.pizzas, activity as Context)
//        clickDisposable = (pizzasRecyclerView.adapter as PizzasAdapter).clickEvent
//                .subscribe({
//                    startDetailActivityWithTransition(activity as Activity, it.second.findViewById(R.id.recents_image), it.second.findViewById(R.id.recents_photo_id), it.first)
//                })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PIZZAS_RESPONSE_KEY, pizzasResponse)
    }

    override fun onDestroy() {
        super.onDestroy()
        clickDisposable?.dispose()
        callDisposable?.dispose()
    }

}
