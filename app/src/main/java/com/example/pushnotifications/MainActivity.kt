package com.example.pushnotifications

import android.content.Context
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pushnotifications.data.NotificationData
import com.example.pushnotifications.data.PushNotifications
import com.example.pushnotifications.databinding.ActivityMainBinding
import com.example.pushnotifications.repository.NotificationRepository
import com.example.pushnotifications.utility.Constants.Companion.TOPIC
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.installations.InstallationTokenResult
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity @Inject constructor(
    private val repository: NotificationRepository
)
: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        val token = FirebaseMessaging.getInstance().token.toString()
        FirebaseInstallations.getInstance().id.addOnSuccessListener {
            FirebaseService.token = token
            binding.etTitle.setText(token)
        }

        binding.btnSend.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val message = binding.etMessage.text.toString()
            val recipientToken = binding.etToken.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()){
                PushNotifications(
                    NotificationData(title,message),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotifications) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = repository.postNotification(notification)
            if (response.isSuccessful){
                Log.d(TAG,"Response: ${Gson().toJson(response)}")
            } else{
                Log.d(TAG,response.errorBody().toString())
            }
        }catch (e:Exception){
            Log.d(TAG,e.toString())
        }
    }
}