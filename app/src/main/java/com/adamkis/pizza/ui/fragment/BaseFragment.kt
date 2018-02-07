package com.adamkis.pizza.ui.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
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
import timber.log.Timber
import android.support.design.widget.Snackbar
import com.adamkis.pizza.model.Pizza
import com.adamkis.pizza.ui.activity.PizzaDetailActivity


/**
 * Created by adam on 2018. 01. 16..
 */
abstract class BaseFragment : Fragment(){

    private var loadingView: View? = null
    private var coordinatorLayout: CoordinatorLayout? = null

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