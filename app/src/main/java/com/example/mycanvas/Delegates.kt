package com.example.mycanvas

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.mycanvas.base.Item
import com.example.mycanvas.data.modules.TOOLS
import com.example.mycanvas.data.ToolItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ColorModel, Item>(
        R.layout.item_palette
    ) {

        val color: ImageView = findViewById(R.id.color)
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind { list ->
            color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

fun sizeAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.SizeModel, Item>(
        R.layout.item_size
    ) {
        val size: TextView = findViewById(R.id.size)
        itemView.setOnClickListener { onClick(adapterPosition) }
        bind { list ->
            size.text = item.size.toString()
        }
    }

fun toolsAdapterDelegate(
    onToolsClick: (Int) -> Unit
): AdapterDelegate<List<Item>> = adapterDelegateLayoutContainer<ToolItem.ToolModel, Item>(
    R.layout.item_tools
) {

    val ivTool: ImageView = findViewById(R.id.ivTool)

    bind { list ->
        ivTool.setImageResource(item.type.value)
        when (item.type) {

            TOOLS.SIZE -> {
                val textSelectedSize: TextView by lazy { findViewById(R.id.tvSelectedSize) }
                textSelectedSize.text = item.selectedSize.value.toString()
                ivTool.isVisible = false
                textSelectedSize.isVisible = true
            }

            TOOLS.PALETTE -> {
                ivTool.setColorFilter(
                    context.resources.getColor(item.selectedColor.value, null),
                    PorterDuff.Mode.SRC_IN
                )
            }
            else -> {
                if (item.isSelected) {
                    ivTool.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    ivTool.setBackgroundResource(android.R.color.transparent)
                }
            }
        }

        itemView.setOnClickListener {
            onToolsClick(adapterPosition)
        }
    }
}