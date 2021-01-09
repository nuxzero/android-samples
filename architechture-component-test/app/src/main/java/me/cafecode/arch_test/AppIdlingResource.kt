package me.cafecode.arch_test

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import java.util.concurrent.atomic.AtomicBoolean


class AppIdlingResource : IdlingResource {

    private var resourceCallback: ResourceCallback? = null

    private val isIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this::class.java.name
    }

    override fun isIdleNow(): Boolean {
        if (isIdleNow.get()) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.resourceCallback = callback
    }

    fun setIdleState(isIdle: Boolean) {
        isIdleNow.set(isIdle)
    }
}