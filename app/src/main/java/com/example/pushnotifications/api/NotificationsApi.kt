package com.example.pushnotifications.api

import com.example.pushnotifications.BuildConfig
import com.example.pushnotifications.data.PushNotifications
import com.example.pushnotifications.utility.Constants.Companion.CONTENT_TYPE
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationsApi {

    @Headers("Authorization: key=${BuildConfig.API_KEY}","Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotifications
    ): Response<ResponseBody>
}