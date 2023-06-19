package com.example.hw_widgets

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener


class Logic(private val context: AppCompatActivity) {

    class Watcher(private val context: AppCompatActivity): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            val button = context.findViewById<Button>(R.id.btn_send)
            if (s != null) {
                if(s.isNotEmpty())
                    button.visibility = View.VISIBLE
                else
                    button.visibility = View.INVISIBLE
            }
            else
                button.visibility = View.INVISIBLE
        }
    }
    class SeekbarChanged (private val context: AppCompatActivity):SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            context.findViewById<TextView>(R.id.current_salary).setText(progress.toString())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit


        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

    }


    fun assignListeners(){
        context.findViewById<EditText>(R.id.input_fio).addTextChangedListener(Watcher(context))
        context.findViewById<EditText>(R.id.input_age).addTextChangedListener(Watcher(context))
        context.findViewById<SeekBar>(R.id.salary_seekbar).setOnSeekBarChangeListener(SeekbarChanged(context))
        context.findViewById<Button>(R.id.btn_send).setOnClickListener{ btnSendClk()}
    }

    private fun btnSendClk(){
        if(checkResults()){
            Toast.makeText(context, "Congratulations, you are useful to us!", Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(context, "Sorry, but you're not compatible with us",Toast.LENGTH_LONG).show()
    }

    private fun langResult(rdbx: RadioGroup): Int{
        return when(context.findViewById<RadioButton>(rdbx.checkedRadioButtonId).text){
            "Good"->{
                2
            }

            "Mediocre"->{
                1
            }

            "Bad"->{
                0
            }

            else -> 0
        }
    }

    private fun checkResults(): Boolean {
        var score = 0
        if(context.findViewById<SeekBar>(R.id.salary_seekbar).progress !in 900..1700){
            return false
        }
        val age = context.findViewById<EditText>(R.id.input_age).text.toString().toIntOrNull()
        if(age==null || age !in 21..40){
            return false
        }
        if(context.findViewById<CheckBox>(R.id.exp_2years).isChecked){
            score+=2
        }
        if(context.findViewById<CheckBox>(R.id.test_exp).isChecked){
            score+=2
        }
        if(context.findViewById<CheckBox>(R.id.send_ready).isChecked){
            score+=2
        }
        score += langResult( context.findViewById(R.id.radio_cs))
        score += langResult( context.findViewById(R.id.radio_ja))
        score += langResult( context.findViewById(R.id.radio_js))
        score += langResult( context.findViewById(R.id.radio_kt))
        score += langResult( context.findViewById(R.id.radio_cpp))


        return score>10
    }
}