package com.fearefull.adtracetest

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.fearefull.adtracetest.utils.AppConstants
import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceConfig
import io.adtrace.sdk.LogLevel

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val environment = AdTraceConfig.ENVIRONMENT_SANDBOX
        val config = AdTraceConfig(this, AppConstants.APP_TOKEN, environment)
        config.setLogLevel(LogLevel.VERBOSE)
        config.enableSendInstalledApps(true)

        config.setOnAttributionChangedListener { attribution ->
            Log.d("example", "Attribution callback called!")
            Log.d("example", "Attribution: $attribution")
        }

        // Set event success tracking delegate.
        // Set event success tracking delegate.
        config.setOnEventTrackingSucceededListener { eventSuccessResponseData ->
            Log.d("example", "Event success callback called!")
            Log.d(
                "example",
                "Event success data: $eventSuccessResponseData"
            )
        }

        // Set event failure tracking delegate.
        // Set event failure tracking delegate.
        config.setOnEventTrackingFailedListener { eventFailureResponseData ->
            Log.d("example", "Event failure callback called!")
            Log.d(
                "example",
                "Event failure data: $eventFailureResponseData"
            )
        }

        // Set session success tracking delegate.
        // Set session success tracking delegate.
        config.setOnSessionTrackingSucceededListener { sessionSuccessResponseData ->
            Log.d("example", "Session success callback called!")
            Log.d(
                "example",
                "Session success data: $sessionSuccessResponseData"
            )
        }

        // Set session failure tracking delegate.
        // Set session failure tracking delegate.
        config.setOnSessionTrackingFailedListener { sessionFailureResponseData ->
            Log.d("example", "Session failure callback called!")
            Log.d(
                "example",
                "Session failure data: $sessionFailureResponseData"
            )
        }

        // Evaluate deferred deep link to be launched.
        config.setOnDeeplinkResponseListener { deeplink ->
            Log.d("example", "Deferred deep link callback called!")
            Log.d("example", "Deep link URL: $deeplink")
            true
        }

        // Allow to send in the background.
        config.setSendInBackground(true)

        AdTrace.addSessionCallbackParameter("sc_foo", "sc_bar")
        AdTrace.addSessionCallbackParameter("sc_key", "sc_value")

        // Add session partner parameters.
        AdTrace.addSessionPartnerParameter("sp_foo", "sp_bar")
        AdTrace.addSessionPartnerParameter("sp_key", "sp_value")

        // Remove session callback parameters.
        AdTrace.removeSessionCallbackParameter("sc_foo")

        // Remove session partner parameters.
        AdTrace.removeSessionPartnerParameter("sp_key")

        // Remove all session callback parameters.
        AdTrace.resetSessionCallbackParameters()

        // Remove all session partner parameters.
        AdTrace.resetSessionPartnerParameters()

        AdTrace.onCreate(config)

        registerActivityLifecycleCallbacks(AdTraceLifecycleCallbacks())
    }

    private class AdTraceLifecycleCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {
            AdTrace.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            AdTrace.onPause()
        }

        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(
            activity: Activity,
            bundle: Bundle
        ) {
        }

        override fun onActivityDestroyed(activity: Activity) {}
    }
}