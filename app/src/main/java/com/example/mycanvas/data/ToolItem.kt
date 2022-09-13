package com.example.mycanvas.data

import androidx.annotation.ColorRes
import com.example.mycanvas.base.Item
import com.example.mycanvas.data.modules.COLOR
import com.example.mycanvas.data.modules.SIZE
import com.example.mycanvas.data.modules.TOOLS

sealed class ToolItem : Item {
    data class ColorModel(@ColorRes val color: Int) : ToolItem()
    data class SizeModel(val size: Int) : ToolItem()
    data class ToolModel(
        val type: TOOLS,
        val selectedTool: TOOLS = TOOLS.NORMAL,
        val isSelected: Boolean = false,
        val selectedSize: SIZE = SIZE.MEDIUM,
        val selectedColor: COLOR = COLOR.BLACK
    ) : ToolItem()
}