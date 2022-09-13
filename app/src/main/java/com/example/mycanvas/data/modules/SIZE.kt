package com.example.mycanvas.data.modules

enum class SIZE(
    val value: Int
) {
    SMALL(4),
    MEDIUM(16),
    LARGE(32),
    EXTRA(64),
    UlTRA(96),
    MEGA(128);

    companion object {
        private val map = values().associateBy(SIZE::value)
        fun from(size: Int) = map[size] ?: SMALL
    }
}