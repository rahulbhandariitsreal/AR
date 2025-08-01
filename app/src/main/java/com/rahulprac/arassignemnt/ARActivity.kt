package com.rahulprac.arassignemnt


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.google.ar.core.Config
import com.rahulprac.arassignemnt.MainActivity.Companion.DRILL_DES
import com.rahulprac.arassignemnt.MainActivity.Companion.DRILL_INDEX
import com.rahulprac.arassignemnt.MainActivity.Companion.DRILL_NAME
import com.rahulprac.arassignemnt.MainActivity.Companion.DRILL_TIP
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

class ARActivity : AppCompatActivity() {


    private lateinit var sceneView: ArSceneView
    private lateinit var modelNode: ArModelNode
    private var drillName:String?="";
    private var drillDes:String?="";
    private var drillTip:String?="";
    private var index:Int? = null
    private lateinit var infoTextView:TextView
    private  var model_name:String=""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)

        intentExtract()

        sceneView = findViewById<ArSceneView?>(R.id.sceneview).apply {
            this.lightEstimationMode = Config.LightEstimationMode.DISABLED
        }

        infoTextView=findViewById(R.id.floatingTextView)



        sceneView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP && !modelNode.isAnchored) {
                // Use screen X and Y from MotionEvent
                val hitResults = sceneView.hitTest(event.x, event.y,true,false,false)
                if (hitResults != null) {
                    modelNode.anchor()
                }
                view.performClick()
            }
            true
        }

        modelNode = ArModelNode(sceneView.engine,PlacementMode.PLANE_HORIZONTAL).apply {
            loadModelGlbAsync(
                glbFileLocation = model_name,
                scaleToUnits = 0.5f,
                centerOrigin = Position(0.0f, -0.5f, 0.0f)
            )
            {
                sceneView.planeRenderer.isVisible = true

            }
                onAnchorChanged = {
                    placeModel()
                }
        }

        sceneView.addChild(modelNode)
    }

    private fun placeModel(){
        sceneView.planeRenderer.isVisible = false
        val info = """
        • ${drillName ?: "Unknown Model"}
        • ${drillDes ?: "No description."}
        • ${drillTip ?: ""}
    """.trimIndent()
        infoTextView.text = info

    }

    private fun intentExtract(){
        drillName=intent.getStringExtra(DRILL_NAME)
        drillDes=intent.getStringExtra(DRILL_DES)
        drillTip=intent.getStringExtra(DRILL_TIP)
        index=intent.getIntExtra(DRILL_INDEX,1)
         model_name= "models/d$index.glb"
    }



}