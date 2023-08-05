package com.example.cav

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(val activity:Activity) {
    private lateinit var isDialog:AlertDialog

    fun startLoading(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_layout, null)
        //set Dialog
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isDialog.show()
    }
    fun isDissmiss(){
        isDialog.dismiss()
    }
}