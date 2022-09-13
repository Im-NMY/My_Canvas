package com.example.mycanvas.ui

import com.example.mycanvas.DataEvent
import com.example.mycanvas.UiEvent
import com.example.mycanvas.ViewState
import com.example.mycanvas.base.BaseViewModel
import com.example.mycanvas.base.Event
import com.example.mycanvas.data.CanvasViewState
import com.example.mycanvas.data.modules.COLOR
import com.example.mycanvas.data.modules.SIZE
import com.example.mycanvas.data.modules.TOOLS
import com.example.mycanvas.data.ToolItem

class CanvasViewModel : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState =
        ViewState(
            colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
            toolsList = enumValues<TOOLS>().map { ToolItem.ToolModel(it) },
            sizeList = enumValues<SIZE>().map { ToolItem.SizeModel(it.value) },

            canvasViewState = CanvasViewState(
                color = COLOR.BLACK,
                size = SIZE.MEDIUM,
                tools = TOOLS.NORMAL
            ),

            isPaletteVisible = false,
            isToolsVisible = false,
            isBrushSizeChangerVisible = false
//            isNewBoardVisible = false,
//            isSeveVisible = false
        )

    init {
        processDataEvent(
            DataEvent.OnSetDefaultTools(
                tool = TOOLS.NORMAL,
                color = COLOR.BLACK,
                size = SIZE.MEDIUM
            )
        )
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false,
                    isBrushSizeChangerVisible = false
                )
            }

// контроль видимости отрисовки на действия пользовотеля
            is UiEvent.OnToolsClick -> {
                when (event.index) {

                    TOOLS.PALETTE.ordinal -> {
                        if (previousState.isBrushSizeChangerVisible == true)
                            return previousState.copy(
                                isPaletteVisible = !previousState.isPaletteVisible,
                                isBrushSizeChangerVisible = !previousState.isBrushSizeChangerVisible
                            )
                        return previousState.copy(isPaletteVisible = !previousState.isPaletteVisible)
                    }

                    TOOLS.SIZE.ordinal -> {
                        if (previousState.isPaletteVisible == true)
                            return previousState.copy(
                                isBrushSizeChangerVisible = !previousState.isBrushSizeChangerVisible,
                                isPaletteVisible = !previousState.isPaletteVisible
                            )
                        return previousState.copy(isBrushSizeChangerVisible = !previousState.isBrushSizeChangerVisible)
                    }

                    TOOLS.ERASER.ordinal -> {
                        if (previousState.isPaletteVisible == true || previousState.isBrushSizeChangerVisible == true)
                            return previousState.copy(
                                isPaletteVisible = false,
                                isBrushSizeChangerVisible = false,
                                canvasViewState = previousState.canvasViewState.copy(
                                    color = COLOR.WHITE,
                                    tools = TOOLS.NORMAL,
                                    size = SIZE.EXTRA
                                )
                            )
                        return null
                    }

                    else -> {

                        val toolsList = previousState.toolsList.mapIndexed() { index, model ->
                            if (index == event.index) {
                                model.copy(isSelected = true)
                            } else {
                                model.copy(isSelected = false)
                            }
                        }

                        return previousState.copy(
                            toolsList = toolsList,
                            canvasViewState = previousState.canvasViewState.copy(tools = TOOLS.values()[event.index])
                        )
                    }
                }
            }

            is UiEvent.OnPaletteClicked -> {
                val selectedColor = COLOR.values()[event.index]
                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.PALETTE) {
                        it.copy(selectedColor = selectedColor)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            is UiEvent.OnSizeClick -> {
                val selectedSize = SIZE.values()[event.index]
                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.SIZE) {
                        it.copy(selectedSize = selectedSize)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(size = selectedSize)
                )
            }

            is DataEvent.OnSetDefaultTools -> {
                val toolsList = previousState.toolsList.map { model ->
                    if (model.type == event.tool) {
                        model.copy(isSelected = true, selectedColor = event.color)
                    } else {
                        model.copy(isSelected = false)
                    }
                }

                return previousState.copy(toolsList = toolsList)
            }

            else -> return null
        }
    }
}