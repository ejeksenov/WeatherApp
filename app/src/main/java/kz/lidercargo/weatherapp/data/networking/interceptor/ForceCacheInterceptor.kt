package kz.lidercargo.weatherapp.data.networking.interceptor

import kz.lidercargo.weatherapp.data.common.utils.Connectivity
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ForceCacheInterceptor(private val connectivity: Connectivity) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!connectivity.hasNetworkAccess()) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }
}