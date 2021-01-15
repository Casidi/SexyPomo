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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    lateinit var mainHandler: Handler
    var counter: Int = 0
    var togglePause: Boolean = true

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
        //val textView: TextView = root.findViewById(R.id.textView)
        val timeText = root.findViewById<TextView>(R.id.timeText)
        timeText.setOnClickListener { togglePause() }
        pageViewModel.text.observe(this, Observer<String> {
            //textView.text = it
        })

        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(updateTextTask)
        return root
    }

    private val updateTextTask = object : Runnable {
        override fun run() {
            //minusOneSecond()
            timeText.text = "" + counter
            counter += 1
            mainHandler.postDelayed(this, 1000)
        }
    }

    private fun togglePause() {

        if(togglePause) {
            mainHandler.removeCallbacks(updateTextTask)
            timeText.setTextColor(Color.parseColor("#ff0000"))
        }
        else {
            mainHandler.post(updateTextTask)
            timeText.setTextColor(Color.parseColor("#0000ff"))
        }
        togglePause = !togglePause
        //Snackbar.make(timeText, "Replace with your own action", Snackbar.LENGTH_LONG)
        //    .setAction("Action", null).show()
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