package me.cafecode.sharedresources

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


class GsonUtils {

    companion object {

        @JvmStatic
        fun getGson(): Gson {
            val exclusionStrategy = ModelExclusionStrategy()
            return GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .addDeserializationExclusionStrategy(exclusionStrategy)
                    .addSerializationExclusionStrategy(exclusionStrategy)
                    .create()
        }

        val mockRepo: Repo get() {
            val jsonString = this::class.java.classLoader.getResource("get-repository.json").readText()
            return GsonUtils.getGson().fromJson<Repo>(jsonString, Repo::class.java).apply {
                objectType = "Repo"
                owner?.objectType = "User"
            }
        }

        val mockRepoList: List<Repo> get() {
            val jsonString = this::class.java.classLoader.getResource("get-repositories.json").readText()
            return GsonUtils.getGson().fromJson<List<Repo>>(jsonString, object: TypeToken<List<Repo>>(){}.type).map {
                it.apply {
                    objectType = "Repo"
                    owner?.objectType = "User"
                }
            }
        }
    }
}

class ModelExclusionStrategy: ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        // Ignored Model class
        return false
    }

    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        // Ignored all fields in Model
        val annotation= f?.getAnnotation(SerializedName::class.java)
        return annotation == null
    }
}