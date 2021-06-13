package com.example.app.di

import androidx.lifecycle.ViewModel
import com.example.app.ui.account.AccountFragment
import com.example.app.ui.account.AccountViewModel
import com.example.app.ui.book_detail.BookDetailFragment
import com.example.app.ui.book_detail.BookDetailViewModel
import com.example.app.ui.home.HomeFragment
import com.example.app.ui.home.HomeViewModel
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
    internal abstract fun notesFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun noteDetailFragment(): BookDetailFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun splashScreenFragment(): SplashScreenFragment

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindNotesViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookDetailViewModel::class)
    abstract fun bindNoteDetailViewModel(viewModel: BookDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    abstract fun bindSplashScreenViewModel(viewModel: SplashScreenViewModel): ViewModel
}
