package com.example.lamps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var table: TableLayout
    lateinit var text: TextView

    private val rows = 5
    private val cols = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        table = findViewById(R.id.tableLayout)
        text = findViewById(R.id.notification)

        for (i in 0 until rows) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
            for (j in 0 until cols) {
                val image = ImageView(this)
                if (Random.nextBoolean()) {
                    image.setImageResource(R.drawable.circle_contour)
                    image.tag = "c_${i}_${j}"
                } else {
                    image.setImageResource(R.drawable.circle_solid)
                    image.tag = "s_${i}_${j}"
                }
                image.setOnClickListener {
                    val info = image.tag.toString().split("_")
                    changeFilled(info)
                    if (check()) {
                        text.text = "Congrats! You won!"
                    }
                }
                tableRow.addView(image, j)
            }
            table.addView(tableRow)
        }
    }

    private fun changeFilled(info: List<String>) {
        val i_index = info[1].toInt()
        val j_index = info[2].toInt()

        for (i in 0 until rows) {
            val row = table.getChildAt(i) as TableRow
            for(j in 0 until cols) {
                if (i == i_index || j == j_index) {
                    val cell = row.getChildAt(j) as ImageView
                    if (cell.tag.toString()[0] == 'c') {
                        cell.setImageResource(R.drawable.circle_solid)
                        cell.tag = "s${cell.tag.toString().substring(1)}"
                    } else {
                        cell.setImageResource(R.drawable.circle_contour)
                        cell.tag = "c${cell.tag.toString().substring(1)}"
                    }
                }
            }
        }
    }

    private fun check(): Boolean {
        var first = ""
        for (i in 0 until rows) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0 until cols) {
                val cell = row.getChildAt(j) as ImageView
                if (i == 0 && j == 0) {
                    first = cell.tag.toString()[0].toString()
                }
                if (cell.tag.toString()[0].toString() != first) {
                    return false
                }
            }
        }
        return true
    }
}