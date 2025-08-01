package com.rahulprac.arassignemnt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val drillNames = listOf("Drill 1", "Drill 2", "Drill 3")
    private lateinit var spinner:Spinner
    private lateinit var image: ImageView
    private lateinit var tips:TextView
    private lateinit var desc:TextView
    private lateinit var btnStart:Button
    val drillImages = listOf(R.drawable.dr1, R.drawable.dr2, R.drawable.dr3)
    val drillDescs = listOf(
        "Description for Drill 1",
        "Description for Drill 2",
        "Description for Drill 3"
    )
    val drillTips = listOf(
        "Tips for Drill 1",
        "Tips for Drill 2",
        "Tips for Drill 3"
    )

    companion object{
        val DRILL_NAME="101"
        val DRILL_TIP="102"
        val DRILL_DES="103"
        val DRILL_INDEX="104"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         spinner = findViewById<Spinner>(R.id.spinnerDrills)
         image = findViewById<ImageView>(R.id.imageDrill)
         desc = findViewById<TextView>(R.id.textDescription)
         tips = findViewById<TextView>(R.id.textTips)
         btnStart = findViewById<Button>(R.id.btnStartAR)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }


        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, drillNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter




        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, pos: Int, id: Long
            ) {
                image.setImageResource(drillImages[pos])
                desc.text = drillDescs[pos]
                tips.text = drillTips[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnStart.setOnClickListener {
           startArActivity()
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
       // no need to do anything
    }

    private fun startArActivity(){
        val selectedIndex = spinner.selectedItemPosition
        val intent = Intent(this, ARActivity::class.java)
        intent.putExtra(DRILL_INDEX,selectedIndex+1)
        intent.putExtra(DRILL_NAME, drillNames[selectedIndex])
        intent.putExtra(DRILL_DES,drillDescs[selectedIndex])
        intent.putExtra(DRILL_TIP,drillTips[selectedIndex])
        startActivity(intent)
    }
}
