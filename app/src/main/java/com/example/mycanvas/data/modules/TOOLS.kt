package com.example.mycanvas.data.modules

import androidx.annotation.DrawableRes
import com.example.mycanvas.R

enum class TOOLS(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.ic_baseline_draw_24),
    DASH(R.drawable.ic_dashed_line),
    SIZE(R.drawable.ic_baseline_format_size_24),
    PALETTE(R.drawable.ic_baseline_color_lens_24),
    ERASER(R.drawable.ic_baseline_edit_off_24)
}