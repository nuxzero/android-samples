package com.example.app.di

import androidx.lifecycle.ViewModel
import com.example.app.ui.account.AccountFragment
import com.example.app.ui.account.AccountViewModel
import com.example.app.ui.note_detail.NoteDetailFragment
import com.example.app.ui.note_detail.NoteDetailViewModel
import com.example.app.ui.notes.NoteListFragment
import com.example.app.ui.notes.NoteListViewModel
import com.example.app.ui.splash_screen.SplashScreenFragment
import com.example.app.ui.splash_screen.SplashScreenViewModel
import com.example.app.util.ViewModelBuilder
import com.example.app.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun accountFragment(): AccountFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun notesFragment(): NoteListFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun noteDetailFragment(): NoteDetailFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun splashScreenFragment(): SplashScreenFragment

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    abstract fun bindNotesViewModel(viewModel: NoteListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteDetailViewModel::class)
    abstract fun bindNoteDetailViewModel(viewModel: NoteDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    abstract fun bindSplashScreenViewModel(viewModel: SplashScreenViewModel): ViewModel
}
