package com.mis.singp.iggy.utils.model

import kotlin.math.abs

class ItemUtils {

    companion object {
        fun collision(basketX: Float, basketY: Float, itemX: Float, itemY: Float): Boolean {
            return abs(basketX - itemX) <= 150 && abs(basketY - itemY) <= 150
        }
    }
}