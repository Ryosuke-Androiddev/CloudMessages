package com.example.pushnotifications.utility

import com.example.pushnotifications.BuildConfig

class Constants {

    companion object{


        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = BuildConfig.API_KEY
        const val CONTENT_TYPE = "application/json"

        const val TOPIC = "/topics/myTopic"

        const val CHANNEL_NAME = "channelName"
        const val DESCRIPTION = "My channel description"
    }
}