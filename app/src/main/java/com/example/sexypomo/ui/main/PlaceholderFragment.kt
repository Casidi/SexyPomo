package com.example.sexypomo.ui.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sexypomo.R
import kotlinx.android.synthetic.main.fragment_main.*
import android.os.CountDownTimer
import android.widget.Button

enum class TimerState {
    WAIT_USER, COUNTING, PAUSED
}

enum class PomoPhase {
    FOCUS, SHORT_BREAK, LONG_BREAK
}

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    lateinit var mainHandler: Handler
    var counter: Int = 3
    var timerState: TimerState = TimerState.WAIT_USER
    var pomoPhase: PomoPhase = PomoPhase.FOCUS
    var pomoSoFar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val timerControlButton: Button = root.findViewById(R.id.button2)
        timerControlButton.setOnClickListener {
            when(timerState) {
                TimerState.WAIT_USER -> {
                    timerState = TimerState.COUNTING
                    button2.text = "Pause"
                    mainHandler.post(updateTextTask)
                }
                TimerState.COUNTING -> {
                    timerState = TimerState.PAUSED
                    button2.text = "Resume"
                    mainHandler.removeCallbacks(updateTextTask)
                }
                TimerState.PAUSED -> {
                    timerState = TimerState.COUNTING
                    button2.text = "Pause"
                    mainHandler.post(updateTextTask)
                }
            }
        }

        mainHandler = Handler(Looper.getMainLooper())
        return root
    }

    private val updateTextTask = object : Runnable {
        override fun run() {
            timeText.text = counterToTimeFormat(counter)
            when(pomoPhase) {
                PomoPhase.FOCUS -> progressBar.progress = 100 * counter / 3
                PomoPhase.SHORT_BREAK -> progressBar.progress = 100 * counter / 5
                PomoPhase.LONG_BREAK -> progressBar.progress = 100 * counter / 10
            }

            if(counter == 0) {
                timerState = TimerState.WAIT_USER
                button2.text = "Start"
                when(pomoPhase) {
                    PomoPhase.FOCUS -> {
                        pomoSoFar++
                        if(pomoSoFar == 4) {
                            pomoPhase = PomoPhase.LONG_BREAK

                            counter = 10
                        } else {
                            pomoPhase = PomoPhase.SHORT_BREAK
                            counter = 5
                        }
                    }
                    PomoPhase.SHORT_BREAK -> {
                        pomoPhase = PomoPhase.FOCUS

                        counter = 3
                    }
                    PomoPhase.LONG_BREAK -> {
                        pomoPhase = PomoPhase.FOCUS
                        counter = 3
                        pomoSoFar = 0
                    }
                }
            } else {
                counter -= 1
                mainHandler.postDelayed(this, 1000)
            }
        }
    }

    fun counterToTimeFormat(x: Int): String {
        return "" + x/60 + ":" + x%60
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {

            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}