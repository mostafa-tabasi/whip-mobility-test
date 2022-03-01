package com.whipmobilitytest.android.ui.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whipmobilitytest.android.data.repository.StatisticsRepository
import com.whipmobilitytest.android.utils.AppConstants.TIME_SCOPE_ALL
import com.whipmobilitytest.android.utils.NoConnectionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

@HiltViewModel
class StatisticsViewModel @Inject constructor(
  private val statisticsRepository: StatisticsRepository,
  private val state: SavedStateHandle,
) : ViewModel() {

  // keep state of the UI
  var uiState by mutableStateOf(StatisticsUiState())
    private set

  init {
    fetchDashboardStatistics()
  }

  /**
   * get statistic data from backend
   */
  private fun fetchDashboardStatistics() {
    // for handling any exception that occur in coroutine
    val exceptionHandler = CoroutineExceptionHandler { c, e ->
      uiState.loading = false
      when (e) {
        is NoConnectionException -> {}
        else -> {}
      }
    }
    // start a coroutine for fetching data
    viewModelScope.launch(exceptionHandler) {
      // show loading
      update(StatisticsUiState::loading, true)
      // fetch data
      val result = statisticsRepository.dashboardStatistics(TIME_SCOPE_ALL)
      // hide loading
      update(StatisticsUiState::loading, false)
      // update data in state to show on UI
      update(StatisticsUiState::dataString, result.response.data.analytics.toString())
    }
  }

  // update property of the class
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
  var dataString: String = "",
  var loading: Boolean = false
)