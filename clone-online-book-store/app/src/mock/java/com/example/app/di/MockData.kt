package com.example.app.di

import com.example.app.R
import com.example.app.data.models.Book
import com.example.app.data.models.Profile
import java.util.Date


object MockData {
    @OptIn(ExperimentalStdlibApi::class)
    private val mockBooks: List<Book> = listOf(
        Book(
            id = 1,
            title = "Fashionopolis",
            author = "Dana Thomas",
            createdAt = Date(),
            image = R.drawable.book_1,
            shortDescription = "A spectacular visual journey through 40 years of haute couture from one of the best-known and most trend-setting brands in fashion."
        ),
        Book(
            id = 2,
            title = "Chanel",
            author = "Patrick Mauriès",
            createdAt = Date(),
            image = R.drawable.book_2,
            shortDescription = "A spectacular visual journey through 40 years of haute couture from one of the best-known and most trend-setting brands in fashion."
        ),
        Book(
            id = 3,
            title = "Calligraphy",
            author = "June & Lucy",
            createdAt = Date(),
            image = R.drawable.book_3,
            shortDescription = "A spectacular visual journey through 40 years of haute couture from one of the best-known and most trend-setting brands in fashion."
        ),
        Book(
            id = 4,
            title = "Yves Saint Laurent",
            author = "Suzy Menkes",
            createdAt = Date(),
            image = R.drawable.book_4,
            shortDescription = "A spectacular visual journey through 40 years of haute couture from one of the best-known and most trend-setting brands in fashion."
        ),
        Book(
            id = 5,
            title = "The Book of Signs",
            author = "Rudolf Koch",
            createdAt = Date(),
            image = R.drawable.book_5,
            shortDescription = "A spectacular visual journey through 40 years of haute couture from one of the best-known and most trend-setting brands in fashion."
        ),
    )

    private val mockProfile: Profile = Profile(
        id = 1,
        fullName = "John Doe",
        email = "john@mail.com",
        image = "https://picsum.photos/id/433/300/300",
    )

    fun getNewestBooks(): List<Book> = mockBooks.takeLast(2)

    fun getPopularBooks(): List<Book> = mockBooks.take(3)

    fun getNote(id: Int): Book = mockBooks.find { it.id == id }
        ?: throw IllegalArgumentException("Could not found note with id: $id")

    fun getProfile(): Profile = mockProfile
}
