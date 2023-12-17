package com.example.terrestrial.ml

import android.content.Context
import org.tensorflow.lite.support.model.Model
import java.io.File

object ModelLoader {
    private var model: Model? = null

    fun loadModel(context: Context, modelName: String): Model {
        if (model == null) {
            val modelFile = File(context.filesDir, modelName)
            if (!modelFile.exists()) {
                // Copy model from assets to internal storage
                copyModelFromAssets(context, modelName, modelFile)
            }
            model = Model(Model.Options.Builder().build())
            model!!.load(modelFile)
        }
        return model!!
    }

    private fun copyModelFromAssets(context: Context, modelName: String, destination: File) {
        context.assets.open(modelName).use { inputStream ->
            destination.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}
