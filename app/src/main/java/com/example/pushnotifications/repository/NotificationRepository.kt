package com.example.pushnotifications.repository

import com.example.pushnotifications.api.NotificationsApi
import com.example.pushnotifications.data.PushNotifications
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationsApi: NotificationsApi
) {
    suspend fun postNotification(
        notification: PushNotifications
    ): Response<ResponseBody>{
        return notificationsApi.postNotification(notification)
    }
}