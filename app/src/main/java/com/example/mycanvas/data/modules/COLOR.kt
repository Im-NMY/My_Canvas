package com.example.mycanvas.data.modules

import androidx.annotation.ColorRes
import com.example.mycanvas.R

enum class COLOR(
    @ColorRes
    val value: Int
) {

    BLACK(R.color.black),
    RED(R.color.red),
    BLUE(R.color.blue),
    GREEN(R.color.green),
    WHITE(R.color.white),
    YELLOW(R.color.yellow),
    ORANGE(R.color.orange),
    VIOLET(R.color.violet),
    MAROON(R.color.maroon),
    SALMON(R.color.salmon),
    PINK(R.color.pink),
    GRAY(R.color.gray);

    companion object {
        private val map = values().associateBy(COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}