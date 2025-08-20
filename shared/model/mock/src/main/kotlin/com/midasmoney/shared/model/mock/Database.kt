package com.midasmoney.shared.model.mock

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Savings
import androidx.compose.ui.graphics.toArgb
import com.midasmoney.shared.model.data.Balance
import com.midasmoney.shared.model.data.Category
import com.midasmoney.shared.model.data.Goal
import com.midasmoney.shared.model.data.IconModel
import com.midasmoney.shared.model.data.IconType
import com.midasmoney.shared.model.data.TransactionHistoryItem
import com.midasmoney.shared.model.data.TransactionStatus
import com.midasmoney.shared.model.data.TransactionType
import com.midasmoney.shared.ui.core.icon.IconMapper
import com.midasmoney.shared.ui.core.color.MidasColors
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
            type = TransactionType.EXPENSE,
            description = "Purchases for food and household supplies",
            icon = IconModel(IconType.SHOPPING_CART),
            color = MidasColors.Green.primary.toArgb() // Green for food-related expenses
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Utilities",
            type = TransactionType.EXPENSE,
            description = "Bills for electricity, water, gas, and internet",
            icon = IconModel(IconType.BOLT),
            color = MidasColors.Blue.primary.toArgb() // Blue for utility services
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Rent/Mortgage",
            type = TransactionType.EXPENSE,
            description = "Monthly housing payments",
            icon = IconModel(IconType.HOME),
            color = MidasColors.Orange.primary.toArgb() // Orange for housing stability
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Transportation",
            type = TransactionType.EXPENSE,
            description = "Costs for fuel, public transit, or vehicle maintenance",
            icon = IconModel(IconType.DIRECTIONS_CAR),
            color = MidasColors.Yellow.primary.toArgb() // Yellow for mobility
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Dining Out",
            type = TransactionType.EXPENSE,
            description = "Expenses for restaurants and takeout",
            icon = IconModel(IconType.RESTAURANT),
            color = MidasColors.Pink.primary.toArgb() // Pink for leisure dining
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Entertainment",
            type = TransactionType.EXPENSE,
            description = "Costs for movies, concerts, and subscriptions",
            icon = IconModel(IconType.THEATER_COMEDY),
            color = MidasColors.Purple.primary.toArgb() // Purple for fun and creativity
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Salary",
            type = TransactionType.INCOME,
            description = "Regular income from employment",
            icon = IconModel(IconType.WORK),
            color = MidasColors.Green.kindaLight.toArgb() // Lighter green for steady income
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Freelance",
            type = TransactionType.INCOME,
            description = "Income from freelance or contract work",
            icon = IconModel(IconType.BUILD),
            color = MidasColors.Blue.kindaLight.toArgb() // Lighter blue for flexible work
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Investments",
            type = TransactionType.INCOME,
            description = "Returns from investments or dividends",
            icon = IconModel(IconType.TRENDING_UP),
            color = MidasColors.Yellow.kindaLight.toArgb() // Lighter yellow for growth
        ),
        Category(
            id = UUID.randomUUID(),
            name = "Gifts",
            type = TransactionType.INCOME,
            description = "Money received as gifts or bonuses",
            icon = IconModel(IconType.CARD_GIFT_CARD),
            color = MidasColors.Pink.kindaLight.toArgb() // Lighter pink for celebratory income
        )
    )

    val transactionHistoryList = listOf(
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("66.90"),
            title = "Supermarket",
            description = "Weekly grocery purchase at Supermarket",
            dateTime = Instant.parse("2025-07-10T14:30:00Z"),
            dueDate = Instant.parse("2025-07-10T14:30:00Z"),
            date = LocalDate.of(2025, 7, 15),
            time = LocalTime.of(14, 30),
            paidFor = Instant.parse("2025-07-10T14:35:00Z"),
            category = commonCategories.find { it.name == "Groceries" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("23.99"),
            title = "Web Development Payment",
            description = "Weekly grocery purchase at Supermarket",
            dateTime = Instant.parse("2025-07-10T14:30:00Z"),
            dueDate = Instant.parse("2025-07-10T14:30:00Z"),
            date = LocalDate.of(2025, 7, 15),
            time = LocalTime.of(18, 13),
            paidFor = Instant.parse("2025-07-10T14:35:00Z"),
            category = commonCategories.find { it.name == "Utilities" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("23.99"),
            title = "ADS Payment",
            description = "Weekly grocery purchase at Supermarket",
            dateTime = Instant.parse("2025-07-10T14:30:00Z"),
            dueDate = Instant.parse("2025-07-10T14:30:00Z"),
            date = LocalDate.of(2025, 7, 15),
            time = LocalTime.of(18, 13),
            paidFor = Instant.parse("2025-07-10T14:35:00Z"),
            category = commonCategories.find { it.name == "Investments" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("45.99"),
            title = "Grocery Shopping",
            description = "Weekly grocery purchase at Supermarket",
            dateTime = Instant.parse("2025-07-10T14:30:00Z"),
            dueDate = Instant.parse("2025-07-10T14:30:00Z"),
            date = LocalDate.of(2025, 7, 14),
            time = LocalTime.of(14, 30),
            paidFor = Instant.parse("2025-07-10T14:35:00Z"),
            category = commonCategories.find { it.name == "Groceries" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("1500.00"),
            title = "Freelance Payment",
            description = "Payment for web development project",
            dateTime = Instant.parse("2025-07-09T09:00:00Z"),
            dueDate =  Instant.parse("2025-07-09T09:00:00Z"),
            date = LocalDate.of(2025, 7, 14),
            time = LocalTime.of(9, 0),
            paidFor = Instant.parse("2025-07-09T09:05:00Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.SCHEDULED,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("120.50"),
            title = "Electricity Bill",
            description = "Monthly electricity bill for July",
            dateTime = Instant.parse("2025-07-08T10:15:00Z"),
            dueDate = Instant.parse("2025-07-15T23:59:59Z"),
            date = LocalDate.of(2025, 7, 8),
            time = LocalTime.of(10, 15),
            paidFor = Instant.parse("2025-07-15T23:59:59Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.COMPLETED,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("250.75"),
            title = "Refund",
            description = "Refund for returned electronics",
            dateTime = Instant.parse("2025-07-07T16:45:00Z"),
            dueDate =  Instant.parse("2025-07-07T16:45:00Z"),
            date = LocalDate.of(2025, 7, 7),
            time = LocalTime.of(16, 45),
            paidFor = Instant.parse("2025-07-07T16:50:00Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.CANCELED,
            id = UUID.randomUUID()
        ),
        TransactionHistoryItem(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("75.00"),
            title = "Restaurant Dinner",
            description = "Dinner with friends at Italian restaurant",
            dateTime = Instant.parse("2025-07-06T19:30:00Z"),
            dueDate =  Instant.parse("2025-07-06T19:30:00Z"),
            date = LocalDate.of(2025, 7, 6),
            time = LocalTime.of(19, 30),
            paidFor = Instant.parse("2025-07-06T20:00:00Z"),
            category = commonCategories.find { it.name == "Dining Out" }!!,
            status = TransactionStatus.FAILED,
            id = UUID.randomUUID()
        )
    )

    val goalList = listOf(
        Goal(
            title = "Emergency Fund",
            description = "Save for unexpected expenses",
            amount = 5000.0,
            progress = 2000.0,
            icon = IconModel(IconMapper.getIconType(Icons.Filled.Savings)!!),
            targetDate = LocalDate.of(2025, 2, 1),
            monthlyValue = 500.0,
            color = MidasColors.Green.primary.toArgb()
        ),
        Goal(
            title = "Vacation",
            description = "Trip to Europe next summer",
            amount = 3000.0,
            progress = 750.0,
            icon = IconModel(IconMapper.getIconType(Icons.Filled.Flight)!!),
            targetDate = LocalDate.of(2025, 3, 1),
            monthlyValue = 640.0,
            color = MidasColors.Blue.primary.toArgb()
        ),
        Goal(
            title = "New Car",
            description = "Down payment for a new vehicle",
            amount = 10000.0,
            progress = 4000.0,
            icon = IconModel(IconMapper.getIconType(Icons.Filled.DirectionsCar)!!),
            targetDate = LocalDate.of(2025, 4, 1),
            monthlyValue = 231.0,
            color = MidasColors.Yellow.primary.toArgb()
        ),
        Goal(
            title = "Pay Off Credit Card",
            description = "Clear outstanding credit card debt",
            amount = 2000.0,
            progress = 1200.0,
            icon = IconModel(IconMapper.getIconType(Icons.Filled.Money)!!),
            targetDate = LocalDate.of(2025, 5, 1),
            monthlyValue = 123.0,
            color = MidasColors.Red.primary.toArgb()
        ),
        Goal(
            title = "Home Renovation",
            description = "Remodel kitchen and bathroom",
            amount = 15000.0,
            progress = 3000.0,
            icon = IconModel(IconMapper.getIconType(Icons.Filled.Home)!!),
            targetDate = LocalDate.of(2025, 6, 1),
            monthlyValue = 882.0,
            color = MidasColors.Orange.primary.toArgb()
        )
    )

    val balance = Balance(
        totalValue = 14250.00f,
        incomeValue = 3200.00f,
        expenseValue = 1850.00f,
    )
}