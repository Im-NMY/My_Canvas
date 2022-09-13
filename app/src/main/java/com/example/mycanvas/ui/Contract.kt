package com.example.mycanvas

import com.example.mycanvas.base.Event
import com.example.mycanvas.data.CanvasViewState
import com.example.mycanvas.data.modules.COLOR
import com.example.mycanvas.data.modules.TOOLS
import com.example.mycanvas.data.ToolItem
import com.example.mycanvas.data.modules.SIZE

data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorList: List<ToolItem.ColorModel>,
    val sizeList: List<ToolItem.SizeModel>,
    val canvasViewState: CanvasViewState,
    val isPaletteVisible: Boolean,
    val isBrushSizeChangerVisible: Boolean,
    val isToolsVisible: Boolean
//    val isSeveVisible: Boolean,
//    val isNewBoardVisible: Boolean
)

sealed class UiEvent : Event {
    data class OnPaletteClicked(val index: Int) : UiEvent()
    data class OnColorClick(val index: Int) : UiEvent()
    data class OnSizeClick(val index: Int) : UiEvent()
    data class OnToolsClick(val index: Int) : UiEvent()

    object OnDrawViewClicked : UiEvent()
    object OnToolbarClicked : UiEvent()
}

sealed class DataEvent : Event {
    data class OnSetDefaultTools(val tool: TOOLS, val color: COLOR, val size: SIZE) : DataEvent()
}