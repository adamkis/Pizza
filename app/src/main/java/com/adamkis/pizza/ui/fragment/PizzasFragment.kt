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
import com.adamkis.pizza.helper.whenAllNotNull
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
    private var ingredientsHM: HashMap<Int?, Ingredient>?  = null

    private val PIZZAS_RESPONSE_KEY = "PIZZAS_RESPONSE_KEY"
    private val INGREDIENTS_HM_KEY = "INGREDIENTS_HM_KEY"

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
        ingredientsHM = savedInstanceState?.getSerializable(INGREDIENTS_HM_KEY) as? HashMap<Int?, Ingredient>
        if(null != pizzasResponse && null != ingredientsHM) {
            setUpAdapter(pizzasRecyclerView, pizzasResponse, ingredientsHM)
            showLoading(false)
        }
        else{
            downloadData(pizzasRecyclerView)
        }
    }

    private fun downloadData(pizzasRecyclerView: RecyclerView){
        val pizzas = restApi.getPizzas().subscribeOn(Schedulers.io())
        val ingredients = restApi.getIngredients().subscribeOn(Schedulers.io())
        Observable.zip(ingredients, pizzas,
                BiFunction<Array<Ingredient>, PizzasResponse, Pair<Array<Ingredient>, PizzasResponse>> {
                    ingredients, pizzasResponse ->
                    Pair(ingredients, pizzasResponse)
                })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doAfterTerminate { showLoading(false) }
            .subscribe(
                {responsePair ->
                    this@PizzasFragment.ingredientsHM = HashMap(responsePair.first.associateBy { it.id })
                    this@PizzasFragment.pizzasResponse = responsePair.second
                    setUpAdapter(pizzasRecyclerView, responsePair.second, ingredientsHM)
                    // TODO remove logging
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
    }

    private fun setUpAdapter(pizzasRecyclerView: RecyclerView, pizzasResponse: PizzasResponse?, ingredientsHM: HashMap<Int?, Ingredient>?){
        pizzasRecyclerView.layoutManager = LinearLayoutManager(this@PizzasFragment.activity, LinearLayout.VERTICAL, false)
        pizzasRecyclerView.adapter = PizzasAdapter(pizzasResponse?.pizzas, ingredientsHM, activity as Context)
//        clickDisposable = (pizzasRecyclerView.adapter as PizzasAdapter).clickEvent
//                .subscribe({
//                    startDetailActivityWithTransition(activity as Activity, it.second.findViewById(R.id.recents_image), it.second.findViewById(R.id.recents_photo_id), it.first)
//                })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PIZZAS_RESPONSE_KEY, pizzasResponse)
        outState.putSerializable(INGREDIENTS_HM_KEY, ingredientsHM)
    }

    override fun onDestroy() {
        super.onDestroy()
        clickDisposable?.dispose()
        callDisposable?.dispose()
    }

}
