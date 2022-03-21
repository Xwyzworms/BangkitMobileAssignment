package com.example.submission2_ezpz.utils

import android.content.Context
import android.widget.Toast

object GeneralUtils {


 fun printMessage(context : Context, message : String )
 {
  Toast.makeText(context,message,Toast.LENGTH_LONG).show()

 }

}
