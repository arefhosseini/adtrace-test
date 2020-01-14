package com.fearefull.adtracetest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fearefull.adtracetest.utils.AppConstants
import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceEvent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val data = intent.data
        AdTrace.appWillOpenUrl(data, applicationContext)
    }

    override fun onResume() {
        super.onResume()

        if (AdTrace.isEnabled()) {
            btnEnableDisableSDK!!.setText(R.string.txt_disable_sdk)
        } else {
            btnEnableDisableSDK!!.setText(R.string.txt_enable_sdk)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun onTrackSimpleEventClick(v: View) {
        val event = AdTraceEvent(AppConstants.SAMPLE_EVENT_TOKEN)

        // Assign custom identifier to event which will be reported in success/failure callbacks.
        event.setCallbackId("PrettyRandomIdentifier")

        AdTrace.trackEvent(event)
    }

    fun onTrackRevenueEventClick(v: View) {
        val event = AdTraceEvent(AppConstants.SAMPLE_REVENUE_TOKEN)

        // Add revenue 1 cent of an euro.
        event.setRevenue(1.0, "Rial")

        AdTrace.trackEvent(event)
    }

    fun onTrackCallbackEventClick(v: View) {
        val event = AdTraceEvent(AppConstants.SAMPLE_CALLBACK_TOKEN)

        // Add callback parameters to this parameter.
        event.addCallbackParameter("key", "value")

        AdTrace.trackEvent(event)
    }

    fun onTrackPartnerEventClick(v: View) {
        val event = AdTraceEvent(AppConstants.SAMPLE_PARTNER_TOKEN)

        // Add partner parameters to this parameter.
        event.addPartnerParameter("foo", "bar")

        AdTrace.trackEvent(event)
    }

    fun onEnableDisableOfflineModeClick(v: View) {
        if ((v as Button).text == applicationContext.resources.getString(R.string.txt_enable_offline_mode)) {
            AdTrace.setOfflineMode(true)
            v.setText(R.string.txt_disable_offline_mode)
        } else {
            AdTrace.setOfflineMode(false)
            v.setText(R.string.txt_enable_offline_mode)
        }
    }

    fun onEnableDisableSDKClick(v: View) {
        if (AdTrace.isEnabled()) {
            AdTrace.setEnabled(false)
            (v as Button).setText(R.string.txt_enable_sdk)
        } else {
            AdTrace.setEnabled(true)
            (v as Button).setText(R.string.txt_disable_sdk)
        }
    }

    fun onIsSDKEnabledClick(v: View) {
        if (AdTrace.isEnabled()) {
            Toast.makeText(applicationContext, R.string.txt_sdk_is_enabled,
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, R.string.txt_sdk_is_disabled,
                Toast.LENGTH_SHORT).show()
        }
    }

    fun onFireIntentClick(v: View) {
        val intent = Intent("com.android.vending.INSTALL_REFERRER")
        intent.setPackage("com.fearefull.adtracetest")
        intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test")
        sendBroadcast(intent)
    }

    fun onServiceActivityClick(v: View) {
        val intent = Intent(this, ServiceActivity::class.java)
        startActivity(intent)
    }
}
