package com.example.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.models.Book
import com.example.app.databinding.BookLargeItemBinding

class PopularBookListItemAdapter(private val listener: BookListItemClickListener) :
    RecyclerView.Adapter<PopularBookListItemViewHolder>() {

    private var _books = listOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularBookListItemViewHolder {
        val binding = BookLargeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularBookListItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PopularBookListItemViewHolder, position: Int) {
        holder.bind(_books[position])
    }

    override fun getItemCount(): Int = _books.size
    fun setBooks(books: List<Book>) {
        _books = books
        notifyDataSetChanged()
    }
}

class PopularBookListItemViewHolder(
    private val binding: BookLargeItemBinding,
    private val listener: BookListItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        with(binding) {
            this.book = book
            this.root.setOnClickListener { listener.invoke(binding.root, book) }
        }
    }
}
