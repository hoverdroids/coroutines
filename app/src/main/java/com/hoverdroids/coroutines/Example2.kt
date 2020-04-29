package com.hoverdroids.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.elvishew.xlog.XLog
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

//Some flow experiments
class Example2  : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)

        Timber.plant()

        experimentOne()
    }

    @ExperimentalCoroutinesApi
    private fun experimentOne(){

        val intervalFlow : Flow<Int> = flow {
            //Do on the bg thread
            (0..10).forEach {
                emit(it)
                delay(100)
            }
        }.flowOn(Dispatchers.Main)//do on main thread

        CoroutineScope(Dispatchers.Main).launch {//executing in bg
            //now collect the flow
            intervalFlow.collect {
                Log.d("YO","Collection1A $it")
            }

            intervalFlow.collect {
                Log.d("YO","Collection1B $it")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            intervalFlow.collect {
                Log.d("YO","Collections2A $it")
            }
            intervalFlow.collect {
                Log.d("YO","Collection2B $it")
            }
        }
    }
}