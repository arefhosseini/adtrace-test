package com.fearefull.adtracetest

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.adtrace.sdk.AdTrace


class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_service)

        val data = intent.data
        AdTrace.appWillOpenUrl(data, applicationContext)
    }

    fun onServiceClick(v: View?) {
        val intent = Intent(this, ServiceExample::class.java)
        startService(intent)
    }

    fun onReturnClick(v: View?) {
        finish()
    }
}