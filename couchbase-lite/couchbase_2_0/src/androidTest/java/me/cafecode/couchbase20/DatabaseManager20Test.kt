package me.cafecode.couchbase20

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import me.cafecode.sharedresources.GsonUtils
import me.cafecode.sharedresources.Repo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseManager20Test {

    private lateinit var databaseManager: DatabaseManager20

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        databaseManager = DatabaseManager20(context, "test-database")
    }

    @After
    fun tearDown() {
        databaseManager.cleanDatabase()
    }

    @Test
    fun insert() {
        val repo = GsonUtils.mockRepo

        databaseManager.insert(repo)

        val actualRepo = databaseManager.get<Repo>(repo.id!!)
        assertNotNull(actualRepo)
        assertEquals(repo.id, actualRepo!!.id)
        assertEquals(repo.fullName, actualRepo.fullName)
    }

    @Test
    fun insertList() {
        val repos = GsonUtils.mockRepoList

        databaseManager.insert(repos)

        val actualRepo = databaseManager.get<Repo>(1)
        assertNotNull(actualRepo)
    }

    @Test
    fun update() {
        val repo = GsonUtils.mockRepo
        repo.fullName = "natthawut/samplecouchbase"
        repo.description = "Sample project usage couchbase lite for Android."

        databaseManager.update(repo)

        val actualRepo = databaseManager.get<Repo>(repo.id!!)
        assertNotNull(actualRepo)
        assertEquals(repo.fullName, actualRepo!!.fullName)
        assertEquals(repo.description, actualRepo.description)
    }

    @Test
    fun get() {
        val repos = GsonUtils.mockRepoList
        databaseManager.insert(repos)

        val actualRepo = databaseManager.get<Repo>(1)

        assertNotNull(actualRepo)
        assertEquals("mojombo/grit", actualRepo!!.fullName)
    }

    @Test
    fun getList() {
        val repos = GsonUtils.mockRepoList
        databaseManager.insert(repos)

        val actualRepos = databaseManager.getList<Repo>()

        assertNotNull(actualRepos)
        assertEquals(100, actualRepos.size)
    }

    @Test
    fun delete() {
        val repos = GsonUtils.mockRepoList
        databaseManager.insert(repos)

        databaseManager.delete<Repo>(1)

        val actualRepo = databaseManager.get<Repo>(1)
        assertNull(actualRepo)
    }
}