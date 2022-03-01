package com.whipmobilitytest.android.ui.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whipmobilitytest.android.data.repository.StatisticsRepository
import com.whipmobilitytest.android.utils.NoConnectionException
import com.whipmobilitytest.android.utils.TimeScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
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

  // coroutine job object
  var dashboardJob: Job? = null

  init {
    fetchDashboardStatistics()
  }

  /**
   * get statistic data from backend
   */
  private fun fetchDashboardStatistics() {
    // for handling any exception that occur in coroutine
    val exceptionHandler = CoroutineExceptionHandler { c, e ->
      // hide loading
      update(StatisticsUiState::loading, false)
      when (e) {
        is NoConnectionException -> {}
        else -> {}
      }
    }
    // cancel the job in case of requesting another fetch before finishing the previous one
    dashboardJob?.cancel()
    // start a coroutine for fetching data
    dashboardJob = viewModelScope.launch(exceptionHandler) {
      // show loading
      update(StatisticsUiState::loading, true)
      // fetch dashboard data based on selected time scope
      val result =
        statisticsRepository.dashboardStatistics(uiState.currentSelectedTimeScope.apiQueryValue)
      // hide loading
      update(StatisticsUiState::loading, false)
      // update data in state to show on UI
      update(StatisticsUiState::dataString, result.response.data.analytics.toString())
    }
  }

  /**
   * when time scope is changed by user
   *
   * @param scope the scope that the user has chosen
   */
  fun onTimeScopeChange(scope: TimeScope) {
    // if selected scope is equal the previous one, ignore update process
    if (scope == uiState.currentSelectedTimeScope) return
    // update current selected scope value with the selected one
    update(StatisticsUiState::currentSelectedTimeScope, scope)
    // refresh data
    fetchDashboardStatistics()
  }

  /**
   * when visibility state of the scope drop down menu is changed
   */
  fun onScopeMenuToggle() {
    // update current value of scope menu expanded with reverse one
    update(StatisticsUiState::isScopeMenuExpanded, !uiState.isScopeMenuExpanded)
  }

  /**
   * update property of the class
   *
   * @param field the required field of class to update its value
   * @param value the new field value that should be updated to
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
  var dataString: String = "",
  var loading: Boolean = false,
  var isScopeMenuExpanded: Boolean = false,
  var currentSelectedTimeScope: TimeScope = TimeScope.ALL
)