package com.example.app.ui.note_detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.app.R
import com.example.app.databinding.NoteDetailFragmentBinding
import com.example.app.util.BaseFragment
import com.example.app.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform

class NoteDetailFragment : BaseFragment() {

    private lateinit var binding: NoteDetailFragmentBinding

    private val viewModel: NoteDetailViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NoteDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.note_motion_duration).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }

        val args = navArgs<NoteDetailFragmentArgs>().value
        val book = args.book
        viewModel.setNoteId(book.id)
        viewModel.book.observe(viewLifecycleOwner, { book ->
            binding.book = book
        })
    }
}
