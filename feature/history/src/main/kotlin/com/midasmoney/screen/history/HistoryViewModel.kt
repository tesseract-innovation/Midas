package com.midasmoney.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.time.ExperimentalTime

data class GroupedTransactions(val date: String, val transactions: List<Transaction>)

class HistoryViewModel : ViewModel() {

    private var _days = MutableStateFlow(7)
    var daysState = _days.asStateFlow()

    private var _selectedTabIndex = MutableStateFlow(0)
    var selectedTabIndex = _selectedTabIndex.asStateFlow()

    private var _filterText = MutableStateFlow("")
    var filterText = _filterText.asStateFlow()

    @OptIn(ExperimentalTime::class)
    val combinedState: StateFlow<List<GroupedTransactions>> = combine(
        filterText,
        daysState,
        selectedTabIndex
    ) { filter, days, tab ->
        val allTransactions = HistoryHandleDatabase.getTransactionsGroupByDate(
            filter,
            days,
            tab.getType()
        )
        allTransactions.map { (date, transactions) ->
            GroupedTransactions(date.toString(), transactions)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setSelectedTab(value: Int) {
        _selectedTabIndex.value = value
    }

    fun setDays(value: Int) {
        _days.value = value
    }

    fun setFilterString(value: String) {
        _filterText.value = value
    }
}

private fun Int.getType(): TransactionType? {
    return when (this) {
        1 -> TransactionType.INCOME
        2 -> TransactionType.EXPENSE
        else -> null
    }
}

fun String.toDays(): Int {
    return when (this) {
        "7 days" -> 7
        "15 days" -> 15
        "30 days" -> 30
        "60 days" -> 60
        "90 days" -> 90
        "180 days" -> 180
        "360 days" -> 360
        else -> 0
    }
}

fun Int.toDays(): String {
    return when (this) {
        7 -> "7 days"
        15 -> "15 days"
        30 -> "30 days"
        60 -> "60 days"
        90 -> "90 days"
        180 -> "180 days"
        360 -> "360 days"
        else -> ""
    }
}
