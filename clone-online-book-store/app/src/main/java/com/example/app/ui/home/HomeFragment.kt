package com.example.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.app.R
import com.example.app.data.models.Book
import com.example.app.databinding.HomeFragmentBinding
import com.example.app.util.BaseFragment
import com.google.android.material.transition.MaterialElevationScale

typealias BookListItemClickListener = (View, Book) -> Unit

class HomeFragment : BaseFragment() {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())
//        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
//        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        postponeEnterTransition()
        binding.root.doOnPreDraw { startPostponedEnterTransition() }

        setupPopularBookList()
        setupNewestBookList()

    }

    private fun setupPopularBookList() {
        val adapter = PopularBookListItemAdapter(::navigateToBookDetail)
        binding.popularBooksList.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner, { notes ->
            adapter.setBooks(notes)
        })
    }

    private fun setupNewestBookList() {
        val adapter = BookListItemAdapter(::navigateToBookDetail)
        binding.noteList.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner, { notes ->
            adapter.setNotes(notes)
        })
    }

    private fun navigateToBookDetail(itemView: View, book: Book) {
        // Navigate to note detail
        FragmentManager.enableNewStateManager(false)
        val noteDetailTransitionName = getString(R.string.book_detail_transition_name)
        val extras = FragmentNavigatorExtras(itemView to noteDetailTransitionName)
        val direction = HomeFragmentDirections.actionBookFragmentToNoteDetailFragment(book)
        findNavController().navigate(direction, extras)

        // Set layout motion
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.note_motion_duration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.note_motion_duration).toLong()
        }
    }
}
