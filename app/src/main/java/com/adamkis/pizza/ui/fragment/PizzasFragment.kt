package com.adamkis.pizza.ui.fragment

import android.app.Activity
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
import com.adamkis.pizza.model.PizzasResponse
import com.adamkis.pizza.network.RestApi
import com.adamkis.pizza.ui.adapter.RecentsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class PizzasFragment : BaseFragment() {

    @Inject lateinit var restApi: RestApi
    private var clickDisposable: Disposable? = null
    private var callDisposable: Disposable? = null
    private var pizzasResponse: PizzasResponse? = null
    private val PHOTOS_RESPONSE_KEY = "PHOTOS_RESPONSE_KEY"

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
        val recentsRecyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recents_recycler_view)
        pizzasResponse = savedInstanceState?.getParcelable(PHOTOS_RESPONSE_KEY)
        if(pizzasResponse != null){
            setUpAdapter(recentsRecyclerView, pizzasResponse!!)
            showLoading(false)
        }
        else{
            downloadData(recentsRecyclerView)
        }
    }

    private fun downloadData(recentsRecyclerView: RecyclerView){
        callDisposable = restApi.getPizzas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doAfterTerminate { showLoading(false) }
            .subscribe(
                {pizzasResponse ->
                    this@PizzasFragment.pizzasResponse = pizzasResponse
//                    setUpAdapter(recentsRecyclerView, photosResponse)
                    logDebug("FIRST RESPONSE")
                    logDebug(pizzasResponse.basePrice.toString())
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

    private fun setUpAdapter(recentsRecyclerView: RecyclerView, pizzasResponse: PizzasResponse){
//        recentsRecyclerView.layoutManager = LinearLayoutManager(this@PizzasFragment.activity, LinearLayout.VERTICAL, false)
//        recentsRecyclerView.adapter = RecentsAdapter(pizzasResponse.photos!!, activity as Context)
//        clickDisposable = (recentsRecyclerView.adapter as RecentsAdapter).clickEvent
//                .subscribe({
//                    startDetailActivityWithTransition(activity as Activity, it.second.findViewById(R.id.recents_image), it.second.findViewById(R.id.recents_photo_id), it.first)
//                })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PHOTOS_RESPONSE_KEY, pizzasResponse)
    }

    override fun onDestroy() {
        super.onDestroy()
        clickDisposable?.dispose()
        callDisposable?.dispose()
    }

}
