package ai.google.gemini.ViewModel

import ai.google.gemini.Api.ApiRepository
import ai.google.gemini.Models.GeminiModel
import ai.google.gemini.Models.RawInput
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class MainViewModel(private val apiReps: ApiRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    val date: LiveData<GeminiModel>
        get() = apiReps.liveData

    fun getResult(raw: RawInput) {
        viewModelScope.launch(Dispatchers.IO) {
            apiReps.getResult(raw)
        }
    }
}