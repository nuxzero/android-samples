package co.cafecode.databindingsample

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class LoginViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    lateinit var viewModel: LoginViewModel

    private val testObserver = mock(Observer::class.java) as Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
        viewModel.isLoginabled.observeForever(testObserver)
    }

    @Test
    fun checkLogin_loginable() {
        viewModel.onEmailChanged("example@mail.com")
        viewModel.onPasswordChanged("testpassword1234")

        assertEquals(true, viewModel.isLoginabled.value)
    }

    @Test
    fun checkLogin_unLoginable() {
        viewModel.onEmailChanged("")
        viewModel.onPasswordChanged("")

        assertEquals(false, viewModel.isLoginabled.value)
    }
}