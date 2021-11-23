package com.example.vodafoneairlinechallenge.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.vodafoneairlinechallenge.R


class SplashFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()
        object : Thread() {
            override fun run() {
                try {
                    sleep((3 * 1000).toLong())
                    Log.i("splash", "navigate to list")
                    findNavController().navigate(R.id.nav_airlineListFragment)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }.start()
    }

}