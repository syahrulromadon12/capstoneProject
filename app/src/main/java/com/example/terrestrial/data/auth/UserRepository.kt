package com.example.terrestrial.data.auth

import android.content.Context
import com.example.terrestrial.data.api.ApiConfig
import com.example.terrestrial.data.api.ApiService
import com.example.terrestrial.data.response.AllCourseResponse
import com.example.terrestrial.data.response.DetailCourseResponse
import com.example.terrestrial.data.response.LoginResponse
import com.example.terrestrial.data.response.QuestionResponse
import com.example.terrestrial.data.response.SignupResponse
import com.example.terrestrial.utils.Constant.OUTPUT_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import com.example.terrestrial.ml.ModelLoader as ModelLoader1

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun signup(request: ApiService.SignupRequest): SignupResponse {
        return apiService.signup(request)
    }

    suspend fun login(request: ApiService.LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    suspend fun saveLoginSession(user: UserModel) {
        userPreferences.saveLoginSession(user)
    }

    fun getLoginSession(): Flow<UserModel> = userPreferences.getLoginSession()

    suspend fun clearLoginSession() {
        userPreferences.clearLoginSession()
    }

    suspend fun getAllCourse(): Result<AllCourseResponse?> {
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            val response = apiService.getAllCourse()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }


    suspend fun getRecommendCourse(): Result<AllCourseResponse?> {
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            val response = apiService.getRecommendCourse()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    suspend fun getDetailCourse(id: String): Result<DetailCourseResponse?> {
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            val response = apiService.getDetailCourse(id)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    suspend fun searchCourse(query: String): AllCourseResponse {
        val user = userPreferences.getLoginSession().first()
        val apiService = ApiConfig.getApiService(user.token)
        return apiService.searchUser(mapOf("query" to query))
    }

    suspend fun getQuestion(): Result<QuestionResponse>{
        return try {
            val user = userPreferences.getLoginSession().first()
            val apiService = ApiConfig.getApiService(user.token)
            val response = apiService.getQuestion()
            Result.Success(response)
        }catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }

    suspend fun processModel(context: Context, answers: FloatArray): FloatArray {
        return try {
            val model = ModelLoader1.loadModel(context, "model.tflite")

            // Buat input tensor dari array float
            val inputTensor = TensorBuffer.createFixedSize(intArrayOf(1, answers.size), DataType.FLOAT32)
            inputTensor.loadArray(answers)

            // Persiapkan output tensor untuk menampung hasil
            val outputTensor = TensorBuffer.createFixedSize(intArrayOf(1, OUTPUT_SIZE.toInt()), DataType.FLOAT32)

            // Jalankan inferensi pada model dan simpan hasil di outputTensor
            model.process(inputTensor.buffer, outputTensor.buffer)

            // Dapatkan hasil sebagai array float
            val resultArray = outputTensor.floatArray

            resultArray
        } catch (e: Exception) {
            floatArrayOf()
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreferences)
            }.also { instance = it }
    }
}
