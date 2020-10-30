package com.example.workoutapp.activities

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.*
import com.example.workoutapp.adapters.ExerciseStatusAdapter
import com.example.workoutapp.model.ExerciseModel
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.custom_back_confirmation_dialog.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //0 -> je Rest Mode
    //1 -> je Exercise Mode
    private var restTimer: CountDownTimer? = null
    private var timerDuration: Long = 10000
//    private var pauseOffSet: Long = 0 //Vreme koje je proslo
    private var progressBarValue = 0
    private var changeMade = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = 0

    private var textToSpeech: TextToSpeech? = null
    private lateinit var player: MediaPlayer

    private lateinit var exerciseStatusAdapter: ExerciseStatusAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val toolbar = toolbar_exercise_activity
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //Definisanje funkcionalnosti klikom na toolbar
        toolbar.setNavigationOnClickListener {
            backButtonCustomDialog()
        }
        exerciseList =
            Constants.defaultExerciseList()
        textToSpeech = TextToSpeech(this, this)
        setupExerciseStatusRecyclerView()
        setupRestView()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            progressBarValue = 0
        }
        player.stop()
        super.onDestroy()
    }
    private fun startTimer(timer: Long, currentProgressBar: ProgressBar, showSeconds: TextView) {
        progressBarValue = 0
        restTimer = object : CountDownTimer(timer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarValue += 1
//                pauseOffSet = timerDuration - millisUntilFinished
                showSeconds.text = (millisUntilFinished/1000).toString()
                currentProgressBar.progress = currentProgressBar.max - progressBarValue
            }
            override fun onFinish() {

                if (changeMade == 0) {
                    changeMade = 1
//                    setupExerciseStatusRecyclerView()
                    exerciseStatusAdapter.notifyDataSetChanged()
                    setupRestView()
                } else if (changeMade == 1) {
                    exerciseList[currentExercisePosition - 1].isCompleted = true
                    changeMade = 0
//                    setupExerciseStatusRecyclerView()
                    exerciseStatusAdapter.notifyDataSetChanged()
                    setupRestView()
                }
            }
        }.start()
    }
    private fun setupRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            progressBarValue = 0
        }
        when (changeMade) {
            1 -> {
                linear_layout_rest_view.visibility = View.GONE
                linear_layout_exercise_view.visibility = View.VISIBLE
                tvExerciseName.text = exerciseList[currentExercisePosition].name
                ivExercise.setImageResource(exerciseList[currentExercisePosition].image)
                exerciseList[currentExercisePosition].isSelected = true
                currentExercisePosition += 1
                if (currentExercisePosition > 11) {
                    val intent = Intent(this, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val message = "Exercise is start. Name is ${exerciseList[currentExercisePosition - 1].name}"
                    speakOut(message)
                    startTimer(30000, progressBarExercise, tvTimerExercise)
                }

            }
            0 -> {
                try {
                    player = MediaPlayer.create(this,
                        R.raw.press_start
                    )
                    player.isLooping = false
                    player.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                linear_layout_rest_view.visibility = View.VISIBLE
                linear_layout_exercise_view.visibility = View.GONE
                tv_exercise_name_rest.text = exerciseList[currentExercisePosition].name
                startTimer(timerDuration, progressBar, tvTimer)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported!")
            } else {
                Log.d("VTSAPPS", "OK")
            }
        } else {
            Log.e("TTS", "Initialization failed!")
        }
    }

    private fun speakOut(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView() {
        exerciseStatusAdapter =
            ExerciseStatusAdapter(
                exerciseList,
                this
            )
        rvExerciseStatus.adapter = exerciseStatusAdapter
        rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun backButtonCustomDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_back_confirmation_dialog)
        dialog.btn_custom_dialog_yes.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialog.btn_custom_dialog_no.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}