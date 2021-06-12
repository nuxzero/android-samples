package com.example.app.di

import com.example.app.data.models.Book
import com.example.app.data.models.Profile
import java.util.Date


object MockData {
    @OptIn(ExperimentalStdlibApi::class)
    private val mockBooks: List<Book>
        get() = buildList {
            repeat(10) { i ->
                val note = Book(
                    id = i,
                    title = "$i sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                    author = "John Doe",
                    createdAt = Date(),
                    image = "https://picsum.photos/id/486/1280/720",
                    note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis ultricies lacus sed turpis tincidunt id aliquet risus feugiat. Morbi tincidunt augue interdum velit euismod in pellentesque massa. Donec pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Tincidunt tortor aliquam nulla facilisi cras. Adipiscing at in tellus integer. Lorem donec massa sapien faucibus et molestie ac feugiat sed. Adipiscing elit ut aliquam purus sit amet luctus venenatis. Nisl nunc mi ipsum faucibus. A pellentesque sit amet porttitor. Mattis rhoncus urna neque viverra justo nec ultrices dui sapien.\\n\\nElementum nisi quis eleifend quam adipiscing vitae proin sagittis. Faucibus pulvinar elementum integer enim neque. Dapibus ultrices in iaculis nunc sed. Sit amet justo donec enim diam vulputate ut pharetra. Risus at ultrices mi tempus. Cursus in hac habitasse platea dictumst quisque sagittis purus sit. Lorem ipsum dolor sit amet consectetur adipiscing elit pellentesque. Imperdiet sed euismod nisi porta lorem mollis aliquam ut. Diam maecenas ultricies mi eget. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis. Non diam phasellus vestibulum lorem sed risus ultricies tristique. In aliquam sem fringilla ut morbi tincidunt augue interdum. Lorem sed risus ultricies tristique nulla. Purus semper eget duis at tellus at urna condimentum. Feugiat vivamus at augue eget arcu dictum."
                )
                add(note)
            }
        }

    private val mockProfile: Profile = Profile(
        id = 1,
        fullName = "John Doe",
        email = "john@mail.com",
        image = "https://picsum.photos/id/433/300/300",
    )

    fun getAllNotes(): List<Book> = mockBooks

    fun getNote(id: Int): Book = mockBooks.find { it.id == id }
        ?: throw IllegalArgumentException("Could not found note with id: $id")

    fun getProfile(): Profile = mockProfile
}
