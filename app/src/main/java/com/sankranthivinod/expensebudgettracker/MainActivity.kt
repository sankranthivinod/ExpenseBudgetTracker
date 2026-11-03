package com.sankranthivinod.expensebudgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.sankranthivinod.expensebudgettracker.data.AppDatabase
import com.sankranthivinod.expensebudgettracker.data.ExpenseRepository
import com.sankranthivinod.expensebudgettracker.ui.ExpenseViewModel
import com.sankranthivinod.expensebudgettracker.ui.ExpenseViewModelFactory
import com.sankranthivinod.expensebudgettracker.ui.MainContainer
import com.sankranthivinod.expensebudgettracker.ui.theme.ExpenseBudgetTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Database and Repository Initialization
        val database = AppDatabase.getDatabase(this)
        val repository = ExpenseRepository(database.expenseDao())
        val viewModelFactory = ExpenseViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ExpenseViewModel::class.java]

        setContent {
            ExpenseBudgetTrackerTheme {
                MainContainer(viewModel = viewModel)
            }
        }
    }
}
