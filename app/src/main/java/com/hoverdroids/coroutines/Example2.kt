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
        experimentTwo()
        experimentThree()
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

    data class Thing(val name:String)
    private val things = listOf(Thing("0"), Thing("1"), Thing("2"), Thing("3"))
    @ExperimentalCoroutinesApi
    private fun experimentTwo(){
        val flowyThings : Flow<Thing> = flow {
            //Do something great in the background
            things.forEach {thing ->
                emit(thing)
                delay(100)
            }
        }.flowOn(Dispatchers.Main)

        //Let's get our things
        CoroutineScope(Dispatchers.Main).launch {
            flowyThings.collect {
                Log.d("YO", "Our flowy thing ${it.name}")
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun experimentThree() {
        val flowyThingsList : Flow<List<Thing>> = flow {
            //Do in the bg thread
            (0..5).forEach {
                //Let's show the list five times
                emit(things)

                delay(100)
            }
        }.flowOn(Dispatchers.Main)

        //Let's collect our lists of things
        CoroutineScope(Dispatchers.Main).launch {
            flowyThingsList.collect {
                val list = it
                Log.d("Yo", "The list:$list")
            }

        }
    }
}