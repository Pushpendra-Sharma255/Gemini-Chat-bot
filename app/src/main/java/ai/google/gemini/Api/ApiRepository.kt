package ai.google.gemini.Api

import ai.google.gemini.Models.GeminiModel
import ai.google.gemini.Models.RawInput
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.RequestBody
import retrofit2.http.Body

class ApiRepository(val apiInterface: ApiInterface) {

    val data: MutableLiveData<GeminiModel> = MutableLiveData()
    val liveData: LiveData<GeminiModel>
        get() = data

    suspend fun getResult(raw: RawInput) {
        val result = apiInterface.getResult(
            "application/json",
            "AIzaSyDVoWvR2Ac1lX1BC4kYtNAMgCLy2TahJ4E",
            raw
        )
        if (result.body() != null && result.isSuccessful) {
            Log.d("Retrofit_Error", "112"+result.message())
            data.postValue(result.body())
        } else {
            Log.d("Retrofit_Error","Result"+ result.code())
        }
    }

}
