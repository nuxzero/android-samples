package com.example.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainViewModel"

open class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<String>()
    open val user: LiveData<String> = _user

    init {
        loadUser()
    }

    fun loadUser() {
        Log.d(TAG, "loadUser()")
        _user.postValue(repository.getUser())
    }

    fun updateUser() {
        _user.postValue(repository.updateUser("Elon Musk"))
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(UserRepository()) as T
            }
        }
    }
}
