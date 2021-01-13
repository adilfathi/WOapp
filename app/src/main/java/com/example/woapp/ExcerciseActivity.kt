package com.example.woapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.SyncStateContract
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_excercise.*
import kotlinx.android.synthetic.main.custom_dialog_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var countDownTimer: CountDownTimer? = null
    private var countDownProgress = 0

    private var countExercise: CountDownTimer? = null
    private var countExerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var textToSpeech: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise)

        setSupportActionBar(toolbar_exercise)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        toolbar_exercise.setNavigationOnClickListener {
            customDialogBackButton()
        }

        textToSpeech = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
        setUpCountView()

        setUpExerciseStatusRV()


    }

    override fun onDestroy() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownProgress = 0
        }

        if (countExercise != null) {
            countExercise!!.cancel()
            countExerciseProgress = 0
        }

        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }

        super.onDestroy()
    }

    private fun setCountProgressBar() {
        progressBar.progress = countDownProgress
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownProgress++
                progressBar.progress = 10-countDownProgress
                txt_timer.text = (10-countDownProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpCountView() {

        try {

            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.getready_sound)
            mediaPlayer!!.isLooping = false
            mediaPlayer!!.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        linearRest.visibility = View.VISIBLE
        linearExercise.visibility = View.GONE

        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownProgress = 0
        }

        txt_upcomingExercise.text = exerciseList!![currentExercisePosition +1].getName()

        setCountProgressBar()
    }

    private fun setExerciseProgress() {
        progressBarExercise.progress = countExerciseProgress
        countExercise = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                countExerciseProgress++
                progressBarExercise.progress = 30-countExerciseProgress
                txt_timerExercise.text = (30-countExerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpCountView()
                } else {
                    finish()
                    val intent = Intent(this@ExcerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setUpExerciseView() {

        linearRest.visibility = View.GONE
        linearExercise.visibility = View.VISIBLE

        if (countExercise != null) {
            countExercise!!.cancel()
            countExerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgress()

        img_exercise.setImageResource(exerciseList!![currentExercisePosition].getImage())
        txt_exercise.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech!!.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not Supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusRV() {
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogBackButton() {
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.custom_dialog_confirmation)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        customDialog.btnYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        customDialog.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

}