package com.mobiquity.miniapp.ui.cataloglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobiquity.miniapp.model.entities.Category
import com.mobiquity.miniapp.model.repository.CatalogRepository
import com.mobiquity.miniapp.util.TestCoroutineRule
import com.mobiquity.miniapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class CatalogListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: CatalogListViewModel

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var categoriesObserver: Observer<Result<List<Category>>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Result<List<Category>>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CatalogListViewModel(catalogRepository)
    }

    @Test
    fun givenApiResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val expectedResult = Result.Success(emptyList<Category>())
            doReturn(expectedResult)
                .`when`(catalogRepository)
                .getCatalog()

            viewModel.getCategories().observeForever(categoriesObserver)
            viewModel.fetchCatalog()
            verify(categoriesObserver, times(2)).onChanged(argumentCaptor.capture())

            val values = argumentCaptor.allValues
            Assert.assertEquals(Result.Status.LOADING, values[0].status)
            Assert.assertEquals(Result.Status.SUCCESS, values[1].status)
            Assert.assertEquals(emptyList<Category>(), values[1].data)

            viewModel.getCategories().removeObserver(categoriesObserver)
        }
    }
}