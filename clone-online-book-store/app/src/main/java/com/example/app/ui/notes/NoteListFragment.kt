package com.example.app.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.models.Book
import com.example.app.databinding.NoteItemBinding
import com.example.app.databinding.NoteListFragmentBinding
import com.example.app.util.BaseFragment
import com.google.android.material.transition.MaterialElevationScale

typealias NoteListItemClickListener = (View, Book) -> Unit

class NoteListFragment : BaseFragment() {

    private lateinit var binding: NoteListFragmentBinding
    private val viewModel: NoteListViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NoteListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())
//        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
//        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        postponeEnterTransition()
        binding.root.doOnPreDraw { startPostponedEnterTransition() }

        setupListView()
    }

    private fun setupListView() {
        val adapter = NoteListItemAdapter { itemView, note ->
            // Navigate to note detail
            val noteDetailTransitionName = getString(R.string.book_detail_transition_name)
            val extras = FragmentNavigatorExtras(itemView to noteDetailTransitionName)
            val direction = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(note)
            findNavController().navigate(direction, extras)

            // Set layout motion
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.note_motion_duration).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.note_motion_duration).toLong()
            }
        }
        binding.noteList.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner, { notes ->
            adapter.setNotes(notes)
        })
    }
}

class NoteListItemAdapter(private val listener: NoteListItemClickListener) :
    RecyclerView.Adapter<NoteListItemViewHolder>() {

    private var _notes = listOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListItemViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteListItemViewHolder, position: Int) {
        holder.bind(_notes[position])
    }

    override fun getItemCount(): Int = _notes.size
    fun setNotes(books: List<Book>) {
        _notes = books
        notifyDataSetChanged()
    }
}

class NoteListItemViewHolder(
    private val binding: NoteItemBinding,
    private val listener: NoteListItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        with(binding) {
            this.book = book
            this.root.setOnClickListener { listener.invoke(binding.root, book) }
        }
    }
}
