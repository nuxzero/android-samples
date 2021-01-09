package me.cafecode.couchbase20

import android.content.Context
import com.couchbase.lite.*
import com.google.gson.reflect.TypeToken
import me.cafecode.sharedresources.GsonUtils
import me.cafecode.sharedresources.Model


class DatabaseManager20(context: Context, databaseName: String) {

    val database: Database
    val gson = GsonUtils.getGson()

    init {
        val config = DatabaseConfiguration(context)
        database = Database(databaseName, config)
    }

    fun <T : Model> insert(data: T) {
        val doc = serialize(data)
        database.save(doc)
    }

    fun <T : Model> insert(list: List<T>) {
        list.map {
            val doc = serialize(it)
            database.save(doc)
        }
    }

    inline fun <reified T : Model> update(data: T) {
        val doc = serialize(data)
        database.save(doc)
    }

    inline fun <reified T : Model> get(id: Long): T? {
        val doc = database.getDocument(id.toString()) ?: return null
        return deserialize(doc)
    }

    inline fun <reified T : Model> getList(): List<T> {
        // Build query
        val query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("objectType").equalTo(Expression.string(T::class.java.simpleName)))
                .orderBy(Ordering.property("updatedAt").descending())

        // Execute
        val results: List<Result> = query.execute().allResults()

        // Deserialize result
        val data = results.map {
            deserialize<T>(MutableDocument(it.toMap()))
        }
        return data
    }

    inline fun <reified T : Model> delete(id: Long) {
        val doc = database.getDocument(id.toString())
        database.delete(doc)
    }

    fun <T : Model> serialize(data: T): MutableDocument {
        val json = gson.toJson(data)
        val map = gson.fromJson<Map<String, Any>>(json, object : TypeToken<Map<String, Any>>() {}.type)

        val doc = MutableDocument(data.id.toString(), map)
        return doc
    }

    inline fun <reified T : Model> deserialize(doc: Document): T {
        val map = doc.toMap()
        val json = gson.toJson(map)
        return gson.fromJson(json, T::class.java)
    }

    fun cleanDatabase() {
        database.delete()
    }
}