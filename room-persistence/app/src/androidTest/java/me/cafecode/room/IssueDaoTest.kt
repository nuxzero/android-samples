package me.cafecode.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class LocalDatabaseTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var database: LocalDatabase
    private lateinit var issueDao: IssueDao

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private val gson = GsonBuilder()
            .setDateFormat(dateFormat.toPattern())
            .create()

    private val issues = getIssues()
    private val issue = getIssue()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        issueDao = database.issueDao()
    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun getAll_shouldReturnListOfIssues() {
        assertEquals(30, issues.size)
    }

    @Test
    fun get_shouldReturnAParticularIssue() {
        issueDao.insertAll(*issues.toTypedArray())

        val actualIssue = issueDao.get(254613222)

        assertEquals(254613222, actualIssue.id)
        assertEquals("The position of the parameter affects the use？ I have three parameters @Header,@QueryMap,@Url. I put \"@URL\" parameter on the last one useless, put the first one useful.why?", actualIssue.title)
    }

    @Test
    fun get_shouldReturnEmbeddedProperty() {
        issueDao.insertAll(*issues.toTypedArray())

        val actualIssue = issueDao.get(254613222)

        assertEquals(254613222, actualIssue.id)
        assertEquals("The position of the parameter affects the use？ I have three parameters @Header,@QueryMap,@Url. I put \"@URL\" parameter on the last one useless, put the first one useful.why?", actualIssue.title)

        val actualUser = actualIssue.user!!
        assertEquals(7651267, actualUser.id)
        assertEquals("mtf7101520", actualUser.login)
    }

    @Test
    fun typeConverter_shouldReturnConverterProperty() {
        issueDao.insertAll(getIssue())

        val actualIssue = issueDao.get(1)

        // Date
        val expectedCreatedAt = dateFormat.parse("2011-04-22T13:33:48Z")
        val expectedUpdatedAt = dateFormat.parse("2011-04-22T13:33:48Z")
        assertEquals(expectedCreatedAt.time, actualIssue.createdAt!!.time)
        assertEquals(expectedUpdatedAt.time, actualIssue.updatedAt!!.time)

        // User
        assertNotNull(actualIssue.assignee)
        assertEquals("octocat",actualIssue.assignee!!.login)

        // List of users
        assertNotNull(actualIssue.assignees)
        assertEquals(1, actualIssue.assignees!!.size)
    }

    @Test
    fun getAllLiveData_shouldReturnListOfIssues() {
        issueDao.insertAll(*issues.toTypedArray())

        val liveData = issueDao.getAllLiveData()
        val result = getLiveDataValue(liveData)!!

        assertEquals(30, result.size)
    }

    @Test
    fun getLiveData_shouldReturnAParticularIssue() {
        issueDao.insertAll(*issues.toTypedArray())

        val liveData = issueDao.getLiveData(254613222)
        val result = getLiveDataValue(liveData)!!

        assertEquals(254613222, result.id)
        assertEquals("The position of the parameter affects the use？ I have three parameters @Header,@QueryMap,@Url. I put \"@URL\" parameter on the last one useless, put the first one useful.why?", result.title)
    }

    @Test
    fun getAllRx_shouldReturnListOfIssues() {
        issueDao.insertAll(*issues.toTypedArray())

        val testObserver = issueDao.getAllRx().test()
        val result = testObserver.values()[0]

        assertEquals(30, result.size)
    }

    @Test
    fun getRx_shouldReturnAParticularIssue() {
        issueDao.insertAll(*issues.toTypedArray())

        val testObserver = issueDao.getRx(254613222).test()
        val result = testObserver.values()[0]

        assertEquals(254613222, result.id)
        assertEquals("The position of the parameter affects the use？ I have three parameters @Header,@QueryMap,@Url. I put \"@URL\" parameter on the last one useless, put the first one useful.why?", result.title)
    }

    @Test
    fun insertAll_shouldInsertIssuesFromVarargToDatabase() {
        issueDao.insertAll(*issues.toTypedArray())

        val actualIssues = issueDao.getAll()
        assertEquals(30, actualIssues.size)
    }

    @Test
    fun insertAll_shouldInsertIssuesFromListToDatabase() {
        issueDao.insertAll(issues)

        val actualIssues = issueDao.getAll()
        assertEquals(30, actualIssues.size)
    }

    private fun getIssue(): Issue {
        val jsonString = this::class.java.classLoader.getResource("get-issue.json").readText()
        return gson.fromJson<Issue>(jsonString, Issue::class.java)
    }

    private fun getIssues(): List<Issue> {
        val jsonString = this::class.java.classLoader.getResource("get-issue-list.json").readText()
        val type = object : TypeToken<List<Issue>>() {}.type
        return gson.fromJson<List<Issue>>(jsonString, type)
    }

    /**
     * Observe LiveData object and wait for value.
     *
     * @param liveData LiveData<T>
     * @return T
     */
    private fun <T> getLiveDataValue(liveData: LiveData<T>): T? {
        var data: T? = null
        val countDownLatch = CountDownLatch(1)

        val observer = Observer<T> {
            data = it
            countDownLatch.countDown()
        }

        liveData.observeForever(observer)

        countDownLatch.await(1, TimeUnit.SECONDS)

        liveData.removeObserver(observer)
        return data
    }
}