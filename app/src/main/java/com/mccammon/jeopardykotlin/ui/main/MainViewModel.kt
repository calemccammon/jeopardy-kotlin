package com.mccammon.jeopardykotlin.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import com.mccammon.jeopardykotlin.BR
import com.mccammon.jeopardykotlin.R
import com.mccammon.jeopardykotlin.service.Clue

class MainViewModel(clue: Clue?) : ObservableViewModel() {

    // TODO: Implement the ViewModel
    private var answer: String? = null

    init {
        setAnswer(clue?.answer)
    }

    @Bindable
    fun getAnswer(): String? = answer

    fun setAnswer(answer: String?) {
        this.answer = answer + "testing"
        notifyPropertyChanged(BR.answer)
    }

    fun getLayoutId(): Int = R.layout.main_fragment

}
