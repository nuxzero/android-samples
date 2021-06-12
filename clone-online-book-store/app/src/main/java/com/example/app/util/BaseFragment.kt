package com.example.app.util

import android.content.Context
import dagger.android.support.DaggerFragment
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var fragmentLifecycle: FragmentLifecycle? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        fragmentLifecycle?.onAttached(this)
    }

    @TestOnly
    fun setFragmentLifecycle(fragmentLifecycle: FragmentLifecycle) {
        this.fragmentLifecycle = fragmentLifecycle
    }
}

@TestOnly
fun BaseFragment.onAttached(action: () -> Unit) {
    setFragmentLifecycle(object : FragmentLifecycle {
        override fun onAttached(fragment: BaseFragment) {
            action()
        }
    })
}

interface FragmentLifecycle {
    fun onAttached(fragment: BaseFragment)
}
