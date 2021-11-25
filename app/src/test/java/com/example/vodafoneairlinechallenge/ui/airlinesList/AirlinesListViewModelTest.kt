package com.example.vodafoneairlinechallenge.ui.airlinesList


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepo
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepoInterface
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.utils.Resource
import com.example.vodafoneairlinechallenge.utils.TestCoroutineRule
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
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
import retrofit2.Response
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
    private lateinit var airlinesRepo: AirlinesRepoInterface

    @Mock
    private lateinit var airlinesResponseObserver: Observer<Resource<List<AirlinesResponseItem>>?>

    @Mock
    private lateinit var airlinesCreationResponseObserver: Observer<Resource<AirLineCreationResponse>?>

    @Before
    fun setUp() {
        viewModel = AirlinesListViewModel(airlinesRepo)
    }

    @Test
    fun `when fetching results ok then return a list of AirlinesResponseItem successfully`() {
        val emptyList = arrayListOf<AirlinesResponseItem>()
        testCoroutineRule.runBlockingTest {
            viewModel.airLinesListResponse.observeForever(airlinesResponseObserver)
            whenever(airlinesRepo.getAirLinesListFromAPI()).thenAnswer {
                Response.success(emptyList)
            }
            viewModel.getAirLinesList()
            assertNotNull(viewModel.airLinesListResponse.value)
            assertEquals(Resource.success(emptyList), viewModel.airLinesListResponse.value)
        }
    }


    @Test
    fun `when calling for  results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.airLinesListResponse.observeForever(airlinesResponseObserver)
            viewModel.getAirLinesList()
            verify(airlinesResponseObserver).onChanged(Resource.loading(null))
        }
    }

    @Test
    fun `when calling for airline list results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.airLinesListResponse.observeForever(airlinesResponseObserver)
            viewModel.getAirLinesList()
            verify(airlinesResponseObserver).onChanged(Resource.loading(null))
        }
    }

    @Test
    fun `when creating new airline success`() {
        val airLineCreationResponse = Gson().fromJson(
            "{\n" +
                    "    \"_id\": \"619fcb7d96681d2fe752277f\",\n" +
                    "    \"id\": 1233532345,\n" +
                    "    \"name\": \"Quatar Airways\",\n" +
                    "    \"country\": \"Quatar\",\n" +
                    "    \"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/300px-Qatar_Airways_Logo.svg.png\",\n" +
                    "    \"slogan\": \"Going Places Together\",\n" +
                    "    \"head_quaters\": \"Qatar Airways Towers, Doha, Qatar\",\n" +
                    "    \"website\": \"www.qatarairways.com\",\n" +
                    "    \"established\": \"1994\",\n" +
                    "    \"__v\": 0\n" +
                    "}", AirLineCreationResponse::class.java
        )
        val airLineCreationRequest = AirLineCreationRequest(
            country = "Quatar",
            established = "1994",
            head_quaters = "Qatar Airways Towers, Doha, Qatar",
            id = 1233532345,
            logo = "https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/300px-Qatar_Airways_Logo.svg.png",
            name = "Quatar Airways",
            slogan = "Going Places Together",
            website = "www.qatarairways.com"
        )
        testCoroutineRule.runBlockingTest {
            viewModel.airLineCreationResponse.observeForever(airlinesCreationResponseObserver)
            whenever(airlinesRepo.createAirlineNewRecord(airLineCreationRequest)).thenAnswer {
                Response.success(airLineCreationResponse)
            }
            viewModel.createNewAirLineRecord(
                airLineCreationRequest
            )
            assertNotNull(viewModel.airLineCreationResponse.value)
            assertEquals(
                Resource.success(airLineCreationResponse),
                viewModel.airLineCreationResponse.value
            )
        }
    }

    @Test
    fun `when creating new airline Error`() {
        val airLineCreationResponseError = "{\n" +
                "    \"message\": \"there is an airline registered under same id you've entered\"\n" +
                "}"
        val airLineCreationRequest = AirLineCreationRequest(
            country = "Quatar",
            established = "1994",
            head_quaters = "Qatar Airways Towers, Doha, Qatar",
            id = 1233532345,
            logo = "https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/300px-Qatar_Airways_Logo.svg.png",
            name = "Quatar Airways",
            slogan = "Going Places Together",
            website = "www.qatarairways.com"
        )
        val expectedErrorResponse = Response.error<AirLineCreationResponse>(
            400,
            airLineCreationResponseError.toResponseBody("application/json".toMediaTypeOrNull())
        )
        testCoroutineRule.runBlockingTest {
            viewModel.airLineCreationResponse.observeForever(airlinesCreationResponseObserver)
            whenever(airlinesRepo.createAirlineNewRecord(airLineCreationRequest)).thenAnswer {
                expectedErrorResponse
            }
            viewModel.createNewAirLineRecord(
                airLineCreationRequest
            )
            assertNotNull(viewModel.airLineCreationResponse.value)
            assertEquals(
                Resource.error("Response.error()", null),
                viewModel.airLineCreationResponse.value
            )
        }
    }

//    @Test
//    fun `when fetching results fails then return an error`() {
//        val exception = mock(HttpException::class.java)
//        testCoroutineRule.runBlockingTest {
//            viewModel.airLinesListResponse.observeForever(airlinesResponseObserver)
//            whenever(airlinesRepo.getAirLinesListFromAPI()).thenAnswer {
//                Response.error(200,null)
//            }
//            viewModel.getAirLinesList()
//            assertNotNull(viewModel.airLinesListResponse.value)
//            assertEquals(
//                Resource.error(exception.message!!, null),
//                viewModel.airLinesListResponse.value
//            )
//        }
//    }

    @After
    fun tearDown() {
        viewModel.airLinesListResponse.removeObserver(airlinesResponseObserver)
        viewModel.airLineCreationResponse.removeObserver(airlinesCreationResponseObserver)
    }

    @Test
    fun `test validate create airline attributes fun success`() {
        val result = viewModel.validateAirLineData("egypt", "1995", "AA", "Egypt", "Egypt")
        assertEquals(true, result)
    }

    @Test
    fun `test validate create airline attributes fun fail`() {
        val result = viewModel.validateAirLineData("", "1995", "AA", "Egypt", "Egypt")
        assertEquals(false, result)
    }

}