package com.sankranthivinod.expensebudgettracker.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ExpenseCategory(
    val title: String,
    val icon: ImageVector,
    val color: Color
) {
    ENTERTAINMENT("Entertainment", Icons.Default.Movie, Color(0xFF8E24AA)),
    INSURANCE("Insurance", Icons.Default.Security, Color(0xFFE53935)),
    RENT("Rent", Icons.Default.Home, Color(0xFF1E88E5)),
    GROCERIES("Groceries", Icons.Default.LocalGroceryStore, Color(0xFF43A047)),
    OUTSIDE_FOOD("Outside Food", Icons.Default.Fastfood, Color(0xFFFB8C00)),
    UTILITIES("Utilities", Icons.Default.ReceiptLong, Color(0xFF00ACC1)),
    SUBSCRIPTIONS("Subscriptions", Icons.Default.Subscriptions, Color(0xFFD81B60)),
    TRANSPORTATION("Transportation", Icons.Default.DirectionsCar, Color(0xFFFFB300)),
    HEALTHCARE("Healthcare", Icons.Default.MedicalServices, Color(0xFF00897B)),
    SHOPPING("Shopping", Icons.Default.ShoppingBag, Color(0xFF5E35B1)),
    OTHER("Other", Icons.Default.MoreHoriz, Color(0xFF78909C));

    companion object {
        fun fromString(value: String): ExpenseCategory {
            return entries.find { it.title.equals(value, ignoreCase = true) } ?: OTHER
        }

        fun getAllTitles(): List<String> {
            return entries.map { it.title }
        }
    }
}
