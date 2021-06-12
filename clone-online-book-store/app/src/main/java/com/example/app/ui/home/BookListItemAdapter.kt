package com.example.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.models.Book
import com.example.app.databinding.BookItemBinding

class BookListItemAdapter(private val listener: BookListItemClickListener) :
    RecyclerView.Adapter<BookListItemViewHolder>() {

    private var _books = listOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListItemViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookListItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: BookListItemViewHolder, position: Int) {
        holder.bind(_books[position])
    }

    override fun getItemCount(): Int = _books.size
    fun setNotes(books: List<Book>) {
        _books = books
        notifyDataSetChanged()
    }
}

class BookListItemViewHolder(
    private val binding: BookItemBinding,
    private val listener: BookListItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        with(binding) {
            this.book = book
            this.root.setOnClickListener { listener.invoke(binding.root, book) }
        }
    }
}
