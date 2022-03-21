package com.example.sec3_latihanaplikasi13_unittest

class CuboidModel {

    private var width : Float =0.0f
    private var length : Float = 0.0f
    private var height : Float = 0.0f


    fun getVolume() : Float = width * length * height

    fun getSurfaceArea() : Float {
        val wl : Float = width * length
        val wh : Float = width * height
        val lh : Float = length * height

        return 2 * (wl + wh + lh)
    }

    fun getCircumference() : Float {
        return 4 * ( width + length + height)
    }

    fun save(width : Float, length : Float, height : Float) : Unit {
        this.width = width
        this.length = length
        this.height = height

    }
}