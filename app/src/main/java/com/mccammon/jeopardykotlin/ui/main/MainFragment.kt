package com.mccammon.jeopardykotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mccammon.jeopardykotlin.R
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
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val view: TextView? = activity?.findViewById(R.id.message)
        view?.setOnClickListener {
            val repo = JServiceRepository(ApiFactory.J_SERVICE_API)
            val scope = CoroutineScope(coroutineContext)
            var clue: Clue? = null
            scope.launch {
                clue = repo.getClue()
            }
            print("test")
        }
        // TODO: Use the ViewModel
    }

}
