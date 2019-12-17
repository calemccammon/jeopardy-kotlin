package com.mccammon.jeopardykotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mccammon.jeopardykotlin.AnswerMatcher
import com.mccammon.jeopardykotlin.R
import com.mccammon.jeopardykotlin.databinding.MainFragmentBinding
import com.mccammon.jeopardykotlin.service.ApiFactory
import com.mccammon.jeopardykotlin.service.Clue
import com.mccammon.jeopardykotlin.service.JServiceRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope, InputListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val parentJob = Job()
    private lateinit var viewModel: MainViewModel
    private val repo = JServiceRepository(ApiFactory.J_SERVICE_API)
    override val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        load()
        val mainFragmentBinding: MainFragmentBinding = DataBindingUtil.inflate(inflater, viewModel.getLayoutId(),
            container, false)
        mainFragmentBinding.viewModel = viewModel
        return mainFragmentBinding.root
    }

    fun load() {
        runBlocking {
            val clue: Clue? = repo.getClue()
            viewModel = MainViewModel(clue, this@MainFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }

    override fun onSkip() {
        var clue: Clue?
        launch {
            clue = repo.getClue()
            viewModel.setCategory(clue?.category?.title)
            viewModel.setQuestion(clue?.question)
            viewModel.setAnswer(clue?.answer)
            viewModel.setSubmittedAnswer("")
        }
    }

    override fun onSubmit(submittedAnswer: String) {
        val isCorrect = AnswerMatcher.isCorrect(submittedAnswer, viewModel.getAnswer())
        val duration = Toast.LENGTH_SHORT
        val text = if(isCorrect) {
            R.string.correct
        } else {
            R.string.incorrect
        }
        val toast = Toast.makeText(context, text, duration)
        toast.show()
        if(isCorrect) {
            onSkip()
        }
    }

    override fun onShow() {
        val alert = context?.let { AlertDialog.Builder(it) }
        alert?.setTitle(R.string.showing_answer)
        alert?.setMessage(viewModel.getAnswer())
        alert?.setCancelable(true)
        alert?.setOnDismissListener {
            onSkip()
        }
        alert?.setNeutralButton(R.string.close) { p0, _ ->
            p0.dismiss()
        }
        alert?.create()
        alert?.show()
    }

}
