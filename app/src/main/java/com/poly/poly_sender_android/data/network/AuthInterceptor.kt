package com.poly.poly_sender_android.data.network

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val invocation: Invocation? = chain.request().tag(Invocation::class.java)
        val publicAPI: Annotation? = invocation?.method()?.getAnnotation(PublicAPI::class.java)
        if (publicAPI == null) {
            sessionManager.fetchAuthToken()?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}