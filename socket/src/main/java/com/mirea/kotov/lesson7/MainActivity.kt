package com.mirea.kotov.lesson7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mirea.kotov.lesson7.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class MainActivity : AppCompatActivity() {
    private val TAG: String? = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding

    private val host: String = "time-a.nist.gov"
    private val port: Int = 13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetTime.setOnClickListener(){
            val backgroundExecutor: ScheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor()
            backgroundExecutor.execute{onGetTime()}
        }
    }

    private fun onGetTime(){
        binding.tvTime.text = getTime()
    }

    private fun getTime(): String{
        var timeResult: String = ""

        try{
            val socket = Socket(host, port)
            val reader: BufferedReader = SocketUtils.getReader(socket)
            reader.readLine()
            timeResult = reader.readLine()

            Log.d(TAG, timeResult)

            socket.close()
            reader.close()
        } catch(e: IOException){
            e.printStackTrace()
        }
        return timeResult
    }

}