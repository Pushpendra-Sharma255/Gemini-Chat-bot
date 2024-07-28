package ai.google.gemini.Api

import ai.google.gemini.Models.GeminiModel
import ai.google.gemini.Models.RawInput
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    suspend fun getResult(
        @Header("Content-Type") contentType: String,
        @Query("key") apiKey: String,
        @Body body: RawInput
    ): Response<GeminiModel>
}