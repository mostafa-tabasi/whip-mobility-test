package com.whipmobilitytest.android.ui.screens.dashboard

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.data.repository.StatisticsRepository
import com.whipmobilitytest.android.utils.NoConnectionException
import com.whipmobilitytest.android.utils.TimeScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

@HiltViewModel
class StatisticsViewModel @Inject constructor(
  private val statisticsRepository: StatisticsRepository,
  private val state: SavedStateHandle,
  private val resources: Resources,
  private val gson: Gson,
) : ViewModel() {

  // keep state of the UI
  var uiState by mutableStateOf(StatisticsUiState())
    private set

  // coroutine job object
  var dashboardJob: Job? = null

  init {
    fetchDashboardStatistics()
  }

  /**
   * Get statistic data from backend
   */
  private fun fetchDashboardStatistics() {
    // for handling any exception that occur in coroutine
    val exceptionHandler = CoroutineExceptionHandler { c, e ->
      // hide loading
      update(StatisticsUiState::loading, false)
      // check exception
      checkCoroutineException(e)
    }
    // cancel the job in case of requesting another fetch before finishing the previous one
    dashboardJob?.cancel()
    // start a coroutine for fetching data
    dashboardJob = viewModelScope.launch(exceptionHandler) {
      // show loading
      update(StatisticsUiState::loading, true)
      // fetch dashboard data based on selected time scope
      val result =
        statisticsRepository.dashboardStatistics(uiState.selectedTimeScope.apiQueryValue)
      // hide loading
      update(StatisticsUiState::loading, false)
      // update data in state to show on UI
      update(StatisticsUiState::dashboardStatisticsData, result.response.data)
    }
  }

  /**
   * Check exception type for handling error message to show to the user
   */
  private fun checkCoroutineException(exception: Throwable) {
    // default error message in case of we couldn't find any error message
    val defaultErrorMessage = resources.getString(R.string.something_went_wrong)

    when (exception) {
      is NoConnectionException -> {
        update(StatisticsUiState::errorMessage, exception.message!!)
      }
      is HttpException -> {
        val errorBodyString = exception.response()?.errorBody()?.string()
        if (errorBodyString.isNullOrEmpty()) {
          update(StatisticsUiState::errorMessage, defaultErrorMessage)
          return
        }
        val json = gson.fromJson(errorBodyString, JsonObject::class.java)
        update(
          StatisticsUiState::errorMessage,
          json["response"]?.asJsonObject?.get("message")?.asString ?: defaultErrorMessage
        )
      }
      else -> {
        update(StatisticsUiState::errorMessage, defaultErrorMessage)
      }
    }
  }

  /**
   * When time scope is changed by user
   *
   * @param scope The scope that the user has chosen
   */
  fun onTimeScopeChange(scope: TimeScope) {
    // if selected scope is equal the previous one, ignore update process
    if (scope == uiState.selectedTimeScope) return
    // update current selected scope value with the selected one
    update(StatisticsUiState::selectedTimeScope, scope)
    // clear previous data
    update(StatisticsUiState::dashboardStatisticsData, null)
    // refresh data
    fetchDashboardStatistics()
  }

  /**
   * When visibility state of the scope drop down menu is changed
   */
  fun onScopeMenuToggle() {
    update(StatisticsUiState::isScopeMenuExpanded, !uiState.isScopeMenuExpanded)
  }

  /**
   * When user click on try again in error message
   */
  fun onTryAgainClick() {
    // clear previous error message
    update(StatisticsUiState::errorMessage, "")
    // refresh data
    fetchDashboardStatistics()
  }

  /**
   * When user changes pie chart type (index of type list)
   */
  fun onPieChartIndexChange(index: Int) {
    update(StatisticsUiState::selectedPieChartIndex, index)
  }

  /**
   * When user toggle visibility state of rating details
   */
  fun onRatingDetailsToggle() {
    update(StatisticsUiState::isRatingsExpanded, !uiState.isRatingsExpanded)
  }

  /**
   * Update property of the class
   *
   * @param field The required field of class to update its value
   * @param value The new field value that should be updated to
   */
  private fun <T> update(field: KMutableProperty1<StatisticsUiState, T>, value: T) {
    // copy the class instance
    val next = uiState.copy()
    // modify the specified class property on the copy
    field.set(next, value)
    // update the state with the new instance of the class
    uiState = next
  }

}

data class StatisticsUiState(
  var dashboardStatisticsData: DashboardStatisticsData? = null,
  var loading: Boolean = false,
  var errorMessage: String = "",
  var isScopeMenuExpanded: Boolean = false,
  var isRatingsExpanded: Boolean = true,
  var selectedTimeScope: TimeScope = TimeScope.ALL,
  var selectedPieChartIndex: Int = 0,
)