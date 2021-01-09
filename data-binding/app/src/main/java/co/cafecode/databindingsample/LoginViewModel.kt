package co.cafecode.databindingsample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


open class LoginViewModel : ViewModel() {
	open val isLoginabled = MutableLiveData<Boolean>()
	private val loginCredential = LoginCredential()

	fun onEmailChanged(text: CharSequence) {
		loginCredential.email = text.toString()
		checkLogin()
	}

	fun onPasswordChanged(text: CharSequence) {
		loginCredential.password = text.toString()
		checkLogin()
	}

	private fun checkLogin() {
		if (loginCredential.email.isNullOrEmpty() || loginCredential.password.isNullOrEmpty()) {
			isLoginabled.postValue(false)
		} else {
			isLoginabled.postValue(true)
		}
	}
}
