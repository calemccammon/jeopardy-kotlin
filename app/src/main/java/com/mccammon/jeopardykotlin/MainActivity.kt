package com.mccammon.jeopardykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mccammon.jeopardykotlin.service.ApiFactory
import com.mccammon.jeopardykotlin.service.JService
import com.mccammon.jeopardykotlin.service.JServiceRepository
import com.mccammon.jeopardykotlin.ui.main.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    val parentJob = Job()
    val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        val repo = JServiceRepository(ApiFactory.jService)
        val scope = CoroutineScope(coroutineContext)
        scope.launch {
            repo.getClue()
        }
    }

}
