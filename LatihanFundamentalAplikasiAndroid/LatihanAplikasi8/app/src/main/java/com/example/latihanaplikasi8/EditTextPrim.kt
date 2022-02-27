package com.example.latihanaplikasi8

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.LAYOUT_DIRECTION_RTL
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener

class EditTextPrim : AppCompatEditText, View.OnTouchListener {

    internal lateinit var  mclearButtonImage : Drawable
    constructor(context : Context) :super(context) {
        init()
    }
    constructor(context : Context, attrs : AttributeSet) : super(context,attrs){
        init()
    }
    constructor(context : Context, attrs : AttributeSet, defStyleAttr: Int) : super(context,attrs,defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Masukan Your Name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {

        mclearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24dp) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()) {
                    showClearButton()
                }
                else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    override fun onTouch(p0: View?, event: MotionEvent): Boolean {

        if(compoundDrawables[2] != null) {
            val clearButtonStart : Float
            val clearButtonEnd : Float
            var isClearButtonClicked : Boolean = false
            when(layoutDirection) {


                View.LAYOUT_DIRECTION_RTL -> {
                    clearButtonEnd = (mclearButtonImage.intrinsicWidth + paddingStart.toFloat())
                    when {
                        event.x < clearButtonEnd -> isClearButtonClicked = true
                    }
                }
                else -> {
                    clearButtonStart = (width - paddingEnd - mclearButtonImage.intrinsicWidth).toFloat()
                    when {
                        event.x > clearButtonStart -> isClearButtonClicked = true
                    }
                }
            }

            when {

                isClearButtonClicked -> when
                {
                    event.action == MotionEvent.ACTION_DOWN ->
                    {
                        mclearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24dp) as Drawable
                        showClearButton()
                        return true
                    }

                    event.action == MotionEvent.ACTION_UP -> {
                        mclearButtonImage = ContextCompat.getDrawable(context,R.drawable.ic_close_black_24dp) as Drawable
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



    private fun showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, mclearButtonImage, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)

    }



}