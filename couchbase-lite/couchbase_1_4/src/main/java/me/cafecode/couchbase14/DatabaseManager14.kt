package me.cafecode.couchbase14

import com.couchbase.lite.Database
import com.couchbase.lite.Document
import com.couchbase.lite.Manager
import com.couchbase.lite.android.AndroidContext
import com.couchbase.lite.util.Log
import com.google.gson.reflect.TypeToken
import me.cafecode.sharedresources.GsonUtils
import me.cafecode.sharedresources.Model


class DatabaseManager14(context: android.content.Context, databaseName: String) {

    val database: Database
    val gson = GsonUtils.getGson()

    val REPO_VIEW_NAME = "me.cafecode.couchbase14.repo-view"

    init {
        val manager = Manager(AndroidContext(context), Manager.DEFAULT_OPTIONS)
        database = manager.getDatabase(databaseName)

    }

    fun <T : Model> insert(data: T) {
        val properties = serialize(data)
        val doc = Document(database, data.id.toString())
        doc.putProperties(properties)
    }

    fun <T : Model> insert(list: List<T>) {
        list.map {
            insert(it)
        }
    }

    inline fun <reified T : Model> update(data: T) {
        val properties = serialize(data)

        val doc = database.getDocument(data.id.toString())
        doc.putProperties(properties)
    }

    inline fun <reified T : Model> get(id: Long): T? {
        val doc = database.getDocument(id.toString()) ?: return null
        return deserialize(doc)
    }

    inline fun <reified T : Model> getList(limit: Int = 0, skip: Int = 0): List<T> {
        // Create View
        val repoView = database.getView(REPO_VIEW_NAME)

        repoView.setMap({ document, emitter ->
            Log.d("RepoView", document.toString())
            if (document["objectType"] == T::class.java.simpleName) {
                // Emit key/value
                emitter.emit(document["name"], document)
            }
        }, "1")

        // Create query
        val query = repoView.createQuery()
        if (limit > 0) {
            query.limit = limit
        }
        query.skip = skip
        query.isDescending = false
        val result = query.run()

        // Deserialize data
        val resultList = arrayListOf<T>()
        while (result.hasNext()) {
            val row = result.next()
            val item = deserialize<T>(row.document)
            item?.let {
                resultList.add(it)
            }
        }
        return resultList
    }

    inline fun <reified T : Model> delete(id: Long) {
        val doc = database.getDocument(id.toString())
        doc.delete()
    }

    fun <T : Model> serialize(data: T): Map<String, Any> {
        val json = gson.toJson(data)
        val map = gson.fromJson<Map<String, Any>>(json, object : TypeToken<Map<String, Any>>() {}.type)
        return map
    }

    inline fun <reified T : Model> deserialize(doc: Document): T? {
        val map = doc.properties ?: return null
        val json = gson.toJson(map)
        return gson.fromJson(json, T::class.java)
    }

    fun cleanDatabase() {
        database.delete()
    }
}