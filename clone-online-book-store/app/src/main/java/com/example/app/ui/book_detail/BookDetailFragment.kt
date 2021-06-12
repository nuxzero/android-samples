package com.example.app.ui.book_detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.app.R
import com.example.app.databinding.BookDetailFragmentBinding
import com.example.app.util.BaseFragment
import com.example.app.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform

class BookDetailFragment : BaseFragment() {

    private lateinit var binding: BookDetailFragmentBinding

    private val viewModel: BookDetailViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BookDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.note_motion_duration).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }

        val args = navArgs<BookDetailFragmentArgs>().value
        val book = args.book
        viewModel.setNoteId(book.id)
        viewModel.book.observe(viewLifecycleOwner, { book -> binding.book = book })
    }
}
