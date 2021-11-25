package com.example.vodafoneairlinechallenge.ui.airlinesList


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepo
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.utils.Resource
import com.example.vodafoneairlinechallenge.utils.TestCoroutineRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.lang.Thread.sleep
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AirlinesListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var viewModel: AirlinesListViewModel

    @Mock
    private lateinit var airlinesRepo: AirlinesRepo

    @Mock
    private lateinit var airlinesResponseObserver: Observer<Resource<List<AirlinesResponseItem>>?>

    @Before
    fun setUp() {
        viewModel = AirlinesListViewModel(airlinesRepo)
    }

    @Test
    fun `when fetching results ok then return a list successfully`() {
        val emptyList = arrayListOf<AirlinesResponseItem>()
        testCoroutineRule.runBlockingTest {
            viewModel.getAirLinesLiveDataObject().observeForever(airlinesResponseObserver)
            whenever(airlinesRepo.getAirLinesListFromAPI()).thenAnswer {
                Resource.success(emptyList)
            }
            viewModel.getAirLinesList()
            assertNotNull(viewModel.getAirLinesLiveDataObject().value)
            assertEquals(Resource.success(emptyList), viewModel.getAirLinesLiveDataObject().value)
        }
    }

    @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.getAirLinesLiveDataObject().observeForever(airlinesResponseObserver)
            viewModel.getAirLinesList()
            verify(airlinesResponseObserver).onChanged(Resource.loading(null))
        }
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
            viewModel.getAirLinesLiveDataObject().observeForever(airlinesResponseObserver)
            whenever(airlinesRepo.getAirLinesListFromAPI()).thenAnswer {
                Resource.error(exception.message!!, null)
            }
            viewModel.getAirLinesList()
            assertNotNull(viewModel.getAirLinesLiveDataObject().value)
            assertEquals(
                Resource.error(exception.message!!, null),
                viewModel.getAirLinesLiveDataObject().value
            )
        }
    }

    @After
    fun tearDown() {
        viewModel.getAirLinesLiveDataObject().removeObserver(airlinesResponseObserver)
    }

    @Test
    fun `test validate create airline attributes fun success`() {
        val result = viewModel.validateAirLineData("egypt", "1995", "AA", "Egypt", "Egypt")
        assertEquals(true, result)
    }

    @Test
    fun `test validate create airline attributes fun fail`() {
        val result = viewModel.validateAirLineData("", "1995", "AA", "Egypt", "Egypt")
        assertEquals(true, result)
    }

}