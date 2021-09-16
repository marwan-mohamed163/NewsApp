package com.example.newsapi.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

 open class BaseActivity:AppCompatActivity (){

        var dialoge: AlertDialog?=null
        fun showMessage(title:String?=null,
                        message:String,
                        posActionName:String?=null,
                        posAction: DialogInterface.OnClickListener?=null,
                        negActionName:String?=null,
                        negAction: DialogInterface.OnClickListener?=null,
                        cancelable:Boolean=true
        ){
            dialoge= AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(posActionName,posAction)
                .setNegativeButton(negActionName,negAction)
                .setCancelable(cancelable)
                .show()

        }

        fun hideDialoge(){
            dialoge?.dismiss()
        }
        var loadingDialog: ProgressDialog?=null;
        fun showLoading(message: String){
            loadingDialog= ProgressDialog(this)
            loadingDialog?.setMessage(message)
            loadingDialog?.setCancelable(false)
            loadingDialog?.show()
        }
        fun hideLoading(){
            loadingDialog?.dismiss()
        }
    }


