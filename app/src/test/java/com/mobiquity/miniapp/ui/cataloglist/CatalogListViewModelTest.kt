package com.mobiquity.miniapp.ui.cataloglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobiquity.miniapp.model.entities.Category
import com.mobiquity.miniapp.model.remote.CatalogRemoteDataSource
import com.mobiquity.miniapp.model.remote.CatalogService
import com.mobiquity.miniapp.model.repository.CatalogRepository
import com.mobiquity.miniapp.util.TestCoroutineRule
import com.mobiquity.miniapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class CatalogListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var catalogService: CatalogService

    @Mock
    private lateinit var catalogRemoteDataSource: CatalogRemoteDataSource

    @InjectMocks
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var categoriesObserver: Observer<Result<List<Category>>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun givenApiResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<Category>())
                .`when`(catalogRepository)
                .getCatalog()
            val viewModel = CatalogListViewModel(catalogRepository)
            viewModel.getCategories().observeForever { categoriesObserver }
            verify(catalogRepository).getCatalog()
            verify(categoriesObserver).onChanged(Result.success(emptyList()))
            viewModel.getCategories().removeObserver(categoriesObserver)
        }
    }
}