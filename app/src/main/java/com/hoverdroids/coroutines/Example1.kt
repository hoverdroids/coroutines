package com.hoverdroids.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Example1  : AppCompatActivity(), CoroutineScope {
    //https://blog.mindorks.com/mastering-kotlin-coroutines-in-android-step-by-step-guide

    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job + handler //a job on the main thread that gets cancelled in onDestroy, with exception handling
    private lateinit var job: Job
    private val handler = CoroutineExceptionHandler {_, exception -> Log.d("Tag", "$exception handled!")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example1)

        job = Job()

        //Create a coroutine on the main thread that doesn't return anything
        //These will get cancelled when the activity is destroyed
        launch {
            val userOne = async(Dispatchers.IO) { fetchUserOne() }//Call non suspend fun from coroutine
            val userTwo = async(Dispatchers.IO) { fetchUser("Chris1") }//Call suspend fun from coroutine (is there a good case for this?)
            val user = fetchUser("Chris2")//directly call the suspend fun from within this coroutine
            showUsers(arrayListOf<User>(userOne.await(), userTwo.await(), user))//back on UI thread
        }

        //When we need the global scope which is our application scope, not the activity scope:
        //Here, even if the activity gets destroyed, the fetchUser functions will continue running as we
        //have used the global scope
        GlobalScope.launch(Dispatchers.Main + handler) { //handle exceptions as well
            val userOne = async(Dispatchers.IO) { fetchUserOne() }
            val userTwo = async(Dispatchers.IO) { fetchUser("Chris3")  }
            val user = fetchUser("Chris4")

            //Show now because we don't think there's gonna be an exception
            showUsers(arrayListOf(userOne.await(), userTwo.await(), user))

            //There could be an exception here so try-catch
            val deferredUser4 = GlobalScope.async {
                fetchUser("Jim")
            }
            try {
                showUsers(arrayListOf(deferredUser4.await()))
            } catch (exception: Exception) {
                Log.d("TAG", "$exception handled!")
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    //Suspend functions are only allowed to be called from a coroutine or another suspend function
    private suspend fun fetchUser(name: String): User {
        return withContext(Dispatchers.IO) {//same as async without writing await()
            //make network call
            //return user
            User(name)
        }
    }

    //No need to make this a suspend function as we are not calling any other suspend function from it
    private fun fetchUserOne(): User {
        //make network call
        return User("Balla")
    }

    private fun showUsers(users:List<User>) {
        users.forEach {
            Log.d("Chris", "User: ${it.name}")
        }
    }

    data class User(val name: String)
}