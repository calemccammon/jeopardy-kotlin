package com.mccammon.jeopardykotlin.ui.main

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.Bindable
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.mccammon.jeopardykotlin.BR
import com.mccammon.jeopardykotlin.R
import com.mccammon.jeopardykotlin.service.Clue

@BindingMethods (
    BindingMethod(
        type = EditText::class,
        attribute = "editTextListener",
        method = "setOnEditorActionListener"
    ), BindingMethod(
        type = EditText::class,
        attribute = "textWatcher",
        method = "addTextChangedListener"
    )
)
class MainViewModel(clue: Clue?, private val inputListener: InputListener) : ObservableViewModel() {

    private var answer: String? = null
    private var question: String? = null
    private var category: String? = null
    private var submittedAnswer = ""
    private var editTextListener: TextView.OnEditorActionListener? = null
    private var textWatcher: TextWatcher? = null

    init {
        setAnswer(clue?.answer)
        setQuestion(clue?.question)
        setCategory(clue?.category?.title)
        setTextWatcher()
        setSubmittedAnswer(submittedAnswer)
        setEditTextListener()
    }

    private fun setTextWatcher() {
        textWatcher = object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                submittedAnswer = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        notifyPropertyChanged(BR.textWatcher)
    }

    @Bindable
    fun getTextWatcher(): TextWatcher? {
        return textWatcher
    }

    @Bindable
    fun getSubmittedAnswer(): String = submittedAnswer

    fun setSubmittedAnswer(submittedAnswer: String) {
        this.submittedAnswer = submittedAnswer
        notifyPropertyChanged(BR.submittedAnswer)
    }

    fun submit(view: View) {
        if(submittedAnswer.isNotEmpty()) {
            inputListener.onSubmit(submittedAnswer)
        }
    }

    fun skip(view: View) {
        inputListener.onSkip()
    }

    fun show(view: View) {
        inputListener.onShow()
    }

    private fun setEditTextListener() {
        editTextListener = object: TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if(p1 == EditorInfo.IME_ACTION_DONE) {
                    inputListener.onSubmit(p0!!.text.toString())
                    return true
                }
                return false
            }
        }
        notifyPropertyChanged(BR.editTextListener)
    }

    @Bindable
    fun getEditTextListener(): TextView.OnEditorActionListener? {
        return editTextListener
    }

    @Bindable
    fun getAnswer(): String? = answer

    @Bindable
    fun getQuestion(): String? = question

    @Bindable
    fun getCategory(): String? = category

    fun setCategory(category: String?) {
        this.category = category
        notifyPropertyChanged(BR.category)
    }

    fun setQuestion(question: String?) {
        this.question = question
        notifyPropertyChanged(BR.question)
    }

    fun setAnswer(answer: String?) {
        this.answer = answer
        notifyPropertyChanged(BR.answer)
    }

    fun getLayoutId(): Int = R.layout.main_fragment

}
