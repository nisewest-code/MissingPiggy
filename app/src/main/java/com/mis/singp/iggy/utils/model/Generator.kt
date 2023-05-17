package com.mis.singp.iggy.utils.model

import com.mis.singp.iggy.R

class Generator {

    companion object {
        fun generatorId(): Int{
            return when ((0 until 3).random()) {
                0 -> R.drawable.life
                1 -> R.drawable.bonus
                else -> R.drawable.negative
            }
        }
    }
}