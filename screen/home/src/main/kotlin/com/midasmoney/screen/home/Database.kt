package com.midasmoney.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Work
import com.midasmoney.shared.ui.core.MidasColors
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

object Database {
    // List of common categories for personal finance management
    val commonCategories = listOf(
        Category(
            id = UUID.randomUUID(),
            name = "Groceries",
            type = TransactionType.Expense,
            description = "Purchases for food and household supplies",
            icon = Icons.Filled.ShoppingCart,
            color = MidasColors.Green.primary // Green for food-related expenses
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Utilities",
            type = TransactionType.Expense,
            description = "Bills for electricity, water, gas, and internet",
            icon = Icons.Filled.Bolt,
            color = MidasColors.Blue.primary // Blue for utility services
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Rent/Mortgage",
            type = TransactionType.Expense,
            description = "Monthly housing payments",
            icon = Icons.Filled.Home,
            color = MidasColors.Orange.primary // Orange for housing stability
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Transportation",
            type = TransactionType.Expense,
            description = "Costs for fuel, public transit, or vehicle maintenance",
            icon = Icons.Filled.DirectionsCar,
            color = MidasColors.Yellow.primary // Yellow for mobility
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Dining Out",
            type = TransactionType.Expense,
            description = "Expenses for restaurants and takeout",
            icon = Icons.Filled.Restaurant,
            color = MidasColors.Pink.primary // Pink for leisure dining
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Entertainment",
            type = TransactionType.Expense,
            description = "Costs for movies, concerts, and subscriptions",
            icon = Icons.Filled.TheaterComedy,
            color = MidasColors.Purple.primary // Purple for fun and creativity
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Salary",
            type = TransactionType.Income,
            description = "Regular income from employment",
            icon = Icons.Filled.Work,
            color = MidasColors.Green.kindaLight // Lighter green for steady income
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Freelance",
            type = TransactionType.Income,
            description = "Income from freelance or contract work",
            icon = Icons.Filled.Build,
            color = MidasColors.Blue.kindaLight // Lighter blue for flexible work
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Investments",
            type = TransactionType.Income,
            description = "Returns from investments or dividends",
            icon = Icons.Filled.TrendingUp,
            color = MidasColors.Yellow.kindaLight // Lighter yellow for growth
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Gifts",
            type = TransactionType.Income,
            description = "Money received as gifts or bonuses",
            icon = Icons.Filled.CardGiftcard,
            color = MidasColors.Pink.kindaLight // Lighter pink for celebratory income
        )
    )

    val transactionHistoryList = listOf(
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.Expense,
            amount = BigDecimal("45.99"),
            title = "Grocery Shopping",
            description = "Weekly grocery purchase at Supermarket",
            dateTime = Instant.parse("2025-07-10T14:30:00Z"),
            dueDate = null,
            date = LocalDate.of(2025, 7, 10),
            time = LocalTime.of(14, 30),
            paidFor = Instant.parse("2025-07-10T14:35:00Z"),
            category = commonCategories.find { it.name == "Groceries" },
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.Income,
            amount = BigDecimal("1500.00"),
            title = "Freelance Payment",
            description = "Payment for web development project",
            dateTime = Instant.parse("2025-07-09T09:00:00Z"),
            dueDate = null,
            date = LocalDate.of(2025, 7, 9),
            time = LocalTime.of(9, 0),
            paidFor = Instant.parse("2025-07-09T09:05:00Z"),
            category = commonCategories.find { it.name == "Freelance" },
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.Expense,
            amount = BigDecimal("120.50"),
            title = "Electricity Bill",
            description = "Monthly electricity bill for July",
            dateTime = Instant.parse("2025-07-08T10:15:00Z"),
            dueDate = Instant.parse("2025-07-15T23:59:59Z"),
            date = LocalDate.of(2025, 7, 8),
            time = LocalTime.of(10, 15),
            paidFor = null,
            category = commonCategories.find { it.name == "Utilities" },
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.Income,
            amount = BigDecimal("250.75"),
            title = "Refund",
            description = "Refund for returned electronics",
            dateTime = Instant.parse("2025-07-07T16:45:00Z"),
            dueDate = null,
            date = LocalDate.of(2025, 7, 7),
            time = LocalTime.of(16, 45),
            paidFor = Instant.parse("2025-07-07T16:50:00Z"),
            category = commonCategories.find { it.name == "Freelance" },
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.Expense,
            amount = BigDecimal("75.00"),
            title = "Restaurant Dinner",
            description = "Dinner with friends at Italian restaurant",
            dateTime = Instant.parse("2025-07-06T19:30:00Z"),
            dueDate = null,
            date = LocalDate.of(2025, 7, 6),
            time = LocalTime.of(19, 30),
            paidFor = Instant.parse("2025-07-06T20:00:00Z"),
            category = commonCategories.find { it.name == "Dining Out" },
            id = UUID.randomUUID()
        )
    )

    val goalList = listOf(
        Goal(
            title = "Emergency Fund",
            description = "Save for unexpected expenses",
            amount = 5000.0,
            progress = 2000.0,
            icon = Icons.Filled.Savings,
            color = MidasColors.Green.primary
        ),
        Goal(
            title = "Vacation",
            description = "Trip to Europe next summer",
            amount = 3000.0,
            progress = 750.0,
            icon = Icons.Filled.Flight,
            color = MidasColors.Blue.primary
        ),
        Goal(
            title = "New Car",
            description = "Down payment for a new vehicle",
            amount = 10000.0,
            progress = 4000.0,
            icon = Icons.Filled.DirectionsCar,
            color = MidasColors.Yellow.primary
        ),
        Goal(
            title = "Pay Off Credit Card",
            description = "Clear outstanding credit card debt",
            amount = 2000.0,
            progress = 1200.0,
            icon = Icons.Filled.Money,
            color = MidasColors.Red.primary
        ),
        Goal(
            title = "Home Renovation",
            description = "Remodel kitchen and bathroom",
            amount = 15000.0,
            progress = 3000.0,
            icon = Icons.Filled.Home,
            color = MidasColors.Orange.primary
        )
    )

}