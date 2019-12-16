package com.mccammon.jeopardykotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mccammon.jeopardykotlin.R
import com.mccammon.jeopardykotlin.databinding.MainFragmentBinding
import com.mccammon.jeopardykotlin.service.ApiFactory
import com.mccammon.jeopardykotlin.service.Clue
import com.mccammon.jeopardykotlin.service.JServiceRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {

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
            viewModel = MainViewModel(clue)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val view: TextView? = activity?.findViewById(R.id.message)
        view?.setOnClickListener {
            var clue: Clue?
            launch {
                clue = repo.getClue()
                viewModel.setAnswer(clue?.answer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }

}
