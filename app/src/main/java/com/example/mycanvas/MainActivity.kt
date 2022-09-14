package com.example.mycanvas

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.mycanvas.base.checkSelfPermissionCompat
import com.example.mycanvas.base.requestPermissionsCompat
import com.example.mycanvas.base.shouldShowRequestPermissionRationaleCompat
import com.example.mycanvas.ui.CanvasViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel






class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    companion object {
        private const val  PERMISSION_REQUEST_STORAGE = 0
        private const val PALETTE_VIEW = 0
        private const val TOOLS_VIEW = 1
        private const val SIZE_VIEW = 2
    }

    private val viewModel: CanvasViewModel by viewModel()
    private var toolsList: List<ToolsLayout> = listOf()

    private val paletteLayout: ToolsLayout by lazy { findViewById(R.id.paletteLayout) }
    private val toolsLayout: ToolsLayout by lazy { findViewById(R.id.toolLayout) }
    private val sizeLayout: ToolsLayout by lazy { findViewById(R.id.sizeLayout) }

    private val ivTools: ImageView by lazy { findViewById(R.id.ivTools) }
    private val ivNewBoard: ImageView by lazy { findViewById(R.id.ivNewBoard) }
    private val ivSave: ImageView by lazy { findViewById(R.id.ivSave) }

    private val drawView: DrawView by lazy { findViewById(R.id.viewDraw) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolsList = listOf(paletteLayout, toolsLayout, sizeLayout)
        viewModel.viewState.observe(this, ::render)

        paletteLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnPaletteClicked(it))
        }
        sizeLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnSizeClick(it))
        }

        toolsLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolsClick(it))
        }

        ivTools.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolbarClicked)
        }
        ivNewBoard.setOnClickListener {
            drawView.clear()
        }

        ivSave.setOnClickListener{
            Log.d("Debug", "OPENED")
            if (checkSelfPermissionCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                startSave()
                Toast.makeText(this, "Разрешение не предоставлено", Toast.LENGTH_LONG).show()
            } else {
                requestStoragePermission()
            }
        }
    }

    private fun render(viewState: ViewState) {

        with(toolsList[PALETTE_VIEW]) {
            render(viewState.colorList)
            isVisible = viewState.isPaletteVisible
        }

        with(toolsList[SIZE_VIEW]) {
            render(viewState.sizeList)
            isVisible = viewState.isBrushSizeChangerVisible
        }

        with(toolsList[TOOLS_VIEW]) {
            render(viewState.toolsList)
            isVisible = viewState.isToolsVisible
        }

        drawView.render(viewState.canvasViewState)
    }

    private fun requestStoragePermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Разрешение не предоставлено", Toast.LENGTH_LONG).show()
            requestPermissionsCompat(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE)

        } else {
            requestPermissionsCompat(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE)
            Toast.makeText(this, "Откройте доступ в настройках телефона!", Toast.LENGTH_LONG).show()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if (requestCode == PERMISSION_REQUEST_STORAGE){
           if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               Toast.makeText(this, "РАЗРЕШЕНО!", Toast.LENGTH_LONG).show()
               startSave()
           } else {
               Toast.makeText(this, "НЕ РАЗРЕШЕНО!", Toast.LENGTH_LONG).show()
           }
       }
    }

    private fun startSave(){
        Toast.makeText(this, "СОХРАНЕНО В ГАЛЕРЕЮ!!!", Toast.LENGTH_LONG).show()
    }
}