package com.sankranthivinod.expensebudgettracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sankranthivinod.expensebudgettracker.data.ExpenseEntity
import com.sankranthivinod.expensebudgettracker.data.ExpenseRepository
import com.sankranthivinod.expensebudgettracker.model.ExpenseCategory
import com.sankranthivinod.expensebudgettracker.model.TimeFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class CategorySummary(
    val category: ExpenseCategory,
    val totalAmount: Double,
    val percentage: Float
)

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    // Form inputs state
    var selectedCategory = MutableStateFlow(ExpenseCategory.ENTERTAINMENT.title)
    var amountInput = MutableStateFlow("")
    var noteInput = MutableStateFlow("")
    var selectedDateMillis = MutableStateFlow(System.currentTimeMillis())
    var formError = MutableStateFlow<String?>(null)

    // Analytics state
    private val _timeFilter = MutableStateFlow(TimeFilter.MONTHLY)
    val timeFilter: StateFlow<TimeFilter> = _timeFilter.asStateFlow()

    private val _customStartDate = MutableStateFlow(getStartOfDefaultMonth())
    val customStartDate: StateFlow<Long> = _customStartDate.asStateFlow()

    private val _customEndDate = MutableStateFlow(System.currentTimeMillis())
    val customEndDate: StateFlow<Long> = _customEndDate.asStateFlow()

    // All expenses from Room
    val allExpenses: StateFlow<List<ExpenseEntity>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            allExpenses.collect { list ->
                if (list.isEmpty()) {
                    val now = System.currentTimeMillis()
                    val oneDay = 86400000L
                    repository.insertExpense(ExpenseEntity(category = "Rent", amount = 1250.00, note = "Monthly Apartment Rent", dateMillis = now - oneDay * 2))
                    repository.insertExpense(ExpenseEntity(category = "Groceries", amount = 340.50, note = "Supermarket Stockup", dateMillis = now - oneDay * 1))
                    repository.insertExpense(ExpenseEntity(category = "Insurance", amount = 220.00, note = "Car & Health Insurance", dateMillis = now - oneDay * 3))
                    repository.insertExpense(ExpenseEntity(category = "Outside Food", amount = 125.75, note = "Weekend Dinner with Family", dateMillis = now - oneDay * 0))
                    repository.insertExpense(ExpenseEntity(category = "Entertainment", amount = 89.99, note = "Movie Tickets & Concert", dateMillis = now - oneDay * 4))
                    repository.insertExpense(ExpenseEntity(category = "Utilities", amount = 145.20, note = "Electricity & Internet Bill", dateMillis = now - oneDay * 5))
                }
            }
        }
    }

    // Filtered expenses based on selected time filter / custom dates
    val filteredExpenses: StateFlow<List<ExpenseEntity>> = combine(
        allExpenses,
        _timeFilter,
        _customStartDate,
        _customEndDate
    ) { expenses, filter, startMillis, endMillis ->
        val range = calculateTimeRange(filter, startMillis, endMillis)
        expenses.filter { it.dateMillis in range.first..range.second }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Total spent in filtered view
    val totalSpentInFilter: StateFlow<Double> = filteredExpenses
        .combine(MutableStateFlow(Unit)) { expenses, _ ->
            expenses.sumOf { it.amount }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Category breakdown list
    val categorySummaries: StateFlow<List<CategorySummary>> = filteredExpenses
        .combine(totalSpentInFilter) { expenses, total ->
            if (total == 0.0) return@combine emptyList()

            val grouped = expenses.groupBy { ExpenseCategory.fromString(it.category) }
            ExpenseCategory.entries.mapNotNull { category ->
                val categoryExpenses = grouped[category] ?: emptyList()
                val sum = categoryExpenses.sumOf { it.amount }
                if (sum > 0) {
                    CategorySummary(
                        category = category,
                        totalAmount = sum,
                        percentage = (sum / total).toFloat()
                    )
                } else null
            }.sortedByDescending { it.totalAmount }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setTimeFilter(filter: TimeFilter) {
        _timeFilter.value = filter
    }

    fun setCustomDateRange(startMillis: Long, endMillis: Long) {
        _customStartDate.value = startMillis
        _customEndDate.value = endMillis
        _timeFilter.value = TimeFilter.CUSTOM
    }

    fun addExpense(): Boolean {
        val amountStr = amountInput.value.trim()
        val amount = amountStr.toDoubleOrNull()

        if (amount == null || amount <= 0.0) {
            formError.value = "Please enter a valid amount greater than 0"
            return false
        }

        val entity = ExpenseEntity(
            category = selectedCategory.value,
            amount = amount,
            note = noteInput.value.trim(),
            dateMillis = selectedDateMillis.value
        )

        viewModelScope.launch {
            repository.insertExpense(entity)
        }

        // Reset form
        amountInput.value = ""
        noteInput.value = ""
        formError.value = null
        selectedDateMillis.value = System.currentTimeMillis()
        return true
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    private fun calculateTimeRange(
        filter: TimeFilter,
        customStart: Long,
        customEnd: Long
    ): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        val now = System.currentTimeMillis()

        return when (filter) {
            TimeFilter.WEEKLY -> {
                calendar.timeInMillis = now
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                Pair(calendar.timeInMillis, now)
            }
            TimeFilter.MONTHLY -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                Pair(calendar.timeInMillis, now)
            }
            TimeFilter.YEARLY -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                Pair(calendar.timeInMillis, now)
            }
            TimeFilter.CUSTOM -> {
                val minStart = minOf(customStart, customEnd)
                val maxEnd = maxOf(customStart, customEnd)
                calendar.timeInMillis = maxEnd
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                Pair(minStart, calendar.timeInMillis)
            }
        }
    }

    private fun getStartOfDefaultMonth(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}

class ExpenseViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
