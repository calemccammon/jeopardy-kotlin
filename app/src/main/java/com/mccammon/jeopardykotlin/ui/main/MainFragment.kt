package com.mccammon.jeopardykotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mccammon.jeopardykotlin.R
import com.mccammon.jeopardykotlin.databinding.MainFragmentBinding
import com.mccammon.jeopardykotlin.service.ApiFactory
import com.mccammon.jeopardykotlin.service.Clue
import com.mccammon.jeopardykotlin.service.JServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment() {

    val parentJob = Job()
    val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repo = JServiceRepository(ApiFactory.J_SERVICE_API)
        val scope = CoroutineScope(coroutineContext)
        var clue: Clue? = null
        scope.launch {
            clue = repo.getClue()
        }
        viewModel = MainViewModel(clue)
        val mainFragmentBinding: MainFragmentBinding = DataBindingUtil.inflate(inflater, viewModel.getLayoutId(),
            container, false)
        mainFragmentBinding.viewModel = viewModel
        return mainFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val view: TextView? = activity?.findViewById(R.id.message)
        view?.setOnClickListener {
            val repo = JServiceRepository(ApiFactory.J_SERVICE_API)
            val scope = CoroutineScope(coroutineContext)
            var clue: Clue? = null
            scope.launch {
                clue = repo.getClue()
            }
            viewModel.setAnswer(clue?.answer)
        }
        // TODO: Use the ViewModel
    }

}
