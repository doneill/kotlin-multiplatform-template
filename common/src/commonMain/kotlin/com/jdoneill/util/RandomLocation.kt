package com.jdoneill.util

import kotlin.jvm.JvmStatic
import kotlin.random.Random

class RandomLocation {
    companion object {
        @JvmStatic
        fun washingtonStateCoords():Pair<Double, Double> {
            val random = Random.Default

            val lat = (45..49).shuffled().first()
            val latDecimal = random.nextDouble()
            val lng = (-124..-116).shuffled().first()
            val lngDecimal = random.nextDouble()

            val y = lat + latDecimal
            val x = lng + lngDecimal

            return Pair(y, x)
        }
    }
}