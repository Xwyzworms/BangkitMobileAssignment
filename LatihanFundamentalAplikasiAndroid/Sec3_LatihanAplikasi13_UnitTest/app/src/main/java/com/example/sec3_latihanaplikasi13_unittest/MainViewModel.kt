package com.example.sec3_latihanaplikasi13_unittest


class MainViewModel(private val cuboidModel: CuboidModel){

    fun getCircumference() = cuboidModel.getCircumference()

    fun getSurfaceArea() = cuboidModel.getSurfaceArea()

    fun getVolume() = cuboidModel.getVolume()

    fun save(w : Float, l : Float, h : Float) {
        cuboidModel.save(w,l,h)
    }

}