package com.example.woapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_history.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates() {
        val dbHandler = SqliteOpenHelper(this, null)
        val completedDatesList = dbHandler.addAllCompletedDates()

        for(i in completedDatesList) {
            if(completedDatesList.size > 0) {
                txt_exerciseFinished.visibility = View.VISIBLE
                rvHistory.visibility = View.VISIBLE
                txt_noDataAvailable.visibility = View.GONE

                rvHistory.layoutManager = LinearLayoutManager(this)
                val historyAdapter = HistoryAdapter(this, completedDatesList)
                rvHistory.adapter = historyAdapter
            } else {
                txt_exerciseFinished.visibility = View.GONE
                rvHistory.visibility = View.GONE
                txt_noDataAvailable.visibility = View.VISIBLE
            }
        }
    }
}