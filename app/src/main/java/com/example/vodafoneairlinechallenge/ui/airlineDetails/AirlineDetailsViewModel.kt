package com.example.vodafoneairlinechallenge.ui.airlineDetails

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.URLUtil
import androidx.lifecycle.ViewModel
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem

class AirlineDetailsViewModel : ViewModel() {
    fun onVisitPressed(airlinesResponseItem: AirlinesResponseItem, view: View) {
        openWebPageChrome(airlinesResponseItem.website!!, view.context)
    }

    private fun openWebPageChrome(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLUtil.guessUrl(url)))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            context.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null)
            context.startActivity(intent)
        }
    }
}