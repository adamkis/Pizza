package com.adamkis.pizza

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.adamkis.pizza.dagger.glide.DaggerGlideComponent
import com.adamkis.pizza.dagger.glide.GlideComponent
import com.adamkis.pizza.dagger.glide.GlideModule
import com.adamkis.pizza.dagger.network.*
import com.adamkis.pizza.helper.logDebug
import com.adamkis.pizza.network.RestApi.Companion.PIZZA_URL_BASE
import com.squareup.leakcanary.LeakCanary
import io.paperdb.Paper
import timber.log.Timber


/**
 * Created by adam on 2018. 01. 05..
 */
class App : Application() {

    @VisibleForTesting
    companion object {
        lateinit var netComponent: NetComponent
        lateinit var glideComponent: GlideComponent
    }

    fun setNetComponent(netComponent: NetComponent){
        App.netComponent = netComponent
    }

    fun createNetComponent(baseUrl: String): NetComponent {
        return DaggerNetComponent.builder()
                .okHttpModule(OkHttpModule())
                .gsonConverterFactoryModule(GsonConverterFactoryModule())
                .loggingInterceptorModule(LoggingInterceptorModule())
                .restApiModule(RestApiModule())
                .retrofitModule(RetrofitModule(baseUrl))
                .build()
    }

    fun createGlideComponent(context: Context): GlideComponent {
        return DaggerGlideComponent.builder()
                .glideModule(GlideModule(context))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        netComponent = createNetComponent(PIZZA_URL_BASE)
        glideComponent = createGlideComponent(applicationContext)
        // Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.tag("Pizza")
        // LeakCanary
        if (LeakCanary.isInAnalyzerProcess(applicationContext)) {
            return
        }
        LeakCanary.install(this)
        // Paper
//        RxPaperBook.init(applicationContext);
        Paper.init(applicationContext);

        // TODO remove
        val displayMetrics = this.getResources().getDisplayMetrics()
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        logDebug("dpHeight = " + dpHeight)
        logDebug("dpWidth = " + dpWidth)
    }

}