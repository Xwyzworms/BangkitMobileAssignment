package com.example.submission1_githubuser.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.submission1_githubuser.R

class SearchableEditText : AppCompatEditText, View.OnTouchListener {

    internal lateinit var mClearButton : Drawable

    constructor(context : Context) : super(context) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int ) : super(context,attrs,defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint="Username"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun showClearButton () {
        setCompoundDrawablesWithIntrinsicBounds(null,null,mClearButton,null)
    }
    private fun hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
    }


    private fun init () {
        mClearButton = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {

        if (compoundDrawables[2] != null) {
           val clearButtonStart : Float
           val clearButtonEnd : Float

           var isButtonClicked = false

            when (layoutDirection) {
                View.LAYOUT_DIRECTION_RTL -> {
                    clearButtonEnd = (mClearButton.intrinsicWidth + paddingStart).toFloat()
                    if (event.x < clearButtonEnd) isButtonClicked = true
                }
                else -> {
                    clearButtonStart = (width - paddingEnd - mClearButton.intrinsicWidth).toFloat()
                    if(event.x > clearButtonStart) isButtonClicked = true
                }
            }

            when(isButtonClicked) {

                true -> when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        mClearButton = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        mClearButton = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
                else -> return false
            }
        }
        return false
    }
}