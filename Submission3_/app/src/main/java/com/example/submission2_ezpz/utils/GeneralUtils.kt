package com.example.submission2_ezpz.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object GeneralUtils {


 fun printMessage(context : Context, message : String )
 {
  Toast.makeText(context,message,Toast.LENGTH_LONG).show()

 }
 fun onGithubClick(activity : Activity ,uri : String) {

  val intent = Intent(Intent.ACTION_VIEW)
  intent.data = Uri.parse(uri)
  activity.startActivity(intent)
 }

}
