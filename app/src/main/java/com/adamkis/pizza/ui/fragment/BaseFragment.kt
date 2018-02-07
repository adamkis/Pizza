package com.adamkis.pizza.ui.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.helper.TransitionHelper
import com.adamkis.pizza.network.getStackTrace
import com.adamkis.pizza.ui.activity.PizzaDetailActivity
import timber.log.Timber
import android.support.design.widget.Snackbar
import com.adamkis.pizza.model.Pizza


/**
 * Created by adam on 2018. 01. 16..
 */
abstract class BaseFragment : Fragment(){

    private var loadingView: View? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun startDetailActivityWithTransition(activity: Activity, firstViewToAnimate: View, secondViewToAnimate: View, pizza: Pizza) {
        val animationBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                *TransitionHelper.createSafeTransitionParticipants(activity,
                        false,
                        Pair(firstViewToAnimate, activity.getString(R.string.transition_pizza_image)),
                        Pair(secondViewToAnimate, activity.getString(R.string.transition_pizza_name))
                ))
                .toBundle()
        try {
            FilePersistenceHelper.writeBitmapToFile(activity, ((firstViewToAnimate as ImageView).drawable as BitmapDrawable).bitmap)
        } catch (e: TypeCastException) {
            Timber.d(getStackTrace(e)) // This happens when the image hasn't loaded yet, not saving is enough
        }
        // TODO make more generic
        val startIntent = PizzaDetailActivity.getStartIntent(activity, pizza)
        startActivity(startIntent, animationBundle)
    }

    protected fun setUpLoadingAndError(loadingView: View, rootView: CoordinatorLayout){
        this.loadingView = loadingView
        coordinatorLayout = rootView
    }

    protected fun showLoading(show: Boolean){
        loadingView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    protected fun showError(message: String){
        coordinatorLayout?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            snackbar.setAction(getString(R.string.dismiss)) { snackbar.dismiss() }
            snackbar.show()
        }
    }

}