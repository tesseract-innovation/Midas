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
import com.midasmoney.shared.model.data.Transaction
import com.midasmoney.shared.model.data.TransactionReport
import com.midasmoney.shared.model.data.TransactionStatus
import com.midasmoney.shared.model.data.TransactionType
import com.midasmoney.shared.ui.core.color.MidasColors
import com.midasmoney.shared.ui.core.icon.IconMapper
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

    val transactions = listOf(
        Transaction(
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
        Transaction(
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
        Transaction(
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
        Transaction(
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
        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("1500.00"),
            title = "Freelance Payment",
            description = "Payment for web development project",
            dateTime = Instant.parse("2025-07-09T09:00:00Z"),
            dueDate = Instant.parse("2025-07-09T09:00:00Z"),
            date = LocalDate.of(2025, 7, 14),
            time = LocalTime.of(9, 0),
            paidFor = Instant.parse("2025-07-09T09:05:00Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.SCHEDULED,
            id = UUID.randomUUID()
        ),
        Transaction(
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
        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("250.75"),
            title = "Refund",
            description = "Refund for returned electronics",
            dateTime = Instant.parse("2025-07-07T16:45:00Z"),
            dueDate = Instant.parse("2025-07-07T16:45:00Z"),
            date = LocalDate.of(2025, 7, 7),
            time = LocalTime.of(16, 45),
            paidFor = Instant.parse("2025-07-07T16:50:00Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.CANCELED,
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("75.00"),
            title = "Restaurant Dinner",
            description = "Dinner with friends at Italian restaurant",
            dateTime = Instant.parse("2025-07-06T19:30:00Z"),
            dueDate = Instant.parse("2025-07-06T19:30:00Z"),
            date = LocalDate.of(2025, 7, 6),
            time = LocalTime.of(19, 30),
            paidFor = Instant.parse("2025-07-06T20:00:00Z"),
            category = commonCategories.find { it.name == "Dining Out" }!!,
            status = TransactionStatus.FAILED,
            id = UUID.randomUUID()
        ),

        // 10 Examples for August 2025
        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("45.50"),
            title = "Grocery Shopping",
            description = "Weekly groceries including fruits, vegetables, and dairy",
            dateTime = Instant.parse("2025-08-02T14:20:00Z"),
            dueDate = Instant.parse("2025-08-02T14:20:00Z"),
            date = LocalDate.of(2025, 8, 2),
            time = LocalTime.of(14, 20),
            paidFor = Instant.parse("2025-08-02T15:00:00Z"),
            category = commonCategories.find { it.name == "Groceries" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("2500.00"),
            title = "Monthly Salary",
            description = "Regular paycheck from full-time job",
            dateTime = Instant.parse("2025-08-05T09:00:00Z"),
            dueDate = Instant.parse("2025-08-05T09:00:00Z"),
            date = LocalDate.of(2025, 8, 5),
            time = LocalTime.of(9, 0),
            paidFor = Instant.parse("2025-08-05T09:00:00Z"),
            category = commonCategories.find { it.name == "Salary" }!!,
            status = TransactionStatus.ON_HOLD,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("120.00"),
            title = "Electricity Bill",
            description = "Monthly utility bill for home electricity",
            dateTime = Instant.parse("2025-08-10T10:15:00Z"),
            dueDate = Instant.parse("2025-08-10T10:15:00Z"),
            date = LocalDate.of(2025, 8, 10),
            time = LocalTime.of(10, 15),
            paidFor = Instant.parse("2025-08-10T11:00:00Z"),
            category = commonCategories.find { it.name == "Utilities" }!!,
            status = TransactionStatus.REJECTED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("800.00"),
            title = "Rent Payment",
            description = "Monthly rent for apartment",
            dateTime = Instant.parse("2025-08-01T00:00:00Z"),
            dueDate = Instant.parse("2025-08-01T00:00:00Z"),
            date = LocalDate.of(2025, 8, 1),
            time = LocalTime.of(0, 0),
            paidFor = Instant.parse("2025-08-01T00:00:00Z"),
            category = commonCategories.find { it.name == "Rent/Mortgage" }!!,
            status = TransactionStatus.SCHEDULED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("25.00"),
            title = "Bus Fare",
            description = "Public transportation to work",
            dateTime = Instant.parse("2025-08-15T07:45:00Z"),
            dueDate = Instant.parse("2025-08-15T07:45:00Z"),
            date = LocalDate.of(2025, 8, 15),
            time = LocalTime.of(7, 45),
            paidFor = Instant.parse("2025-08-15T07:45:00Z"),
            category = commonCategories.find { it.name == "Transportation" }!!,
            status = TransactionStatus.CANCELED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("60.75"),
            title = "Lunch at Cafe",
            description = "Casual lunch outing with colleague",
            dateTime = Instant.parse("2025-08-12T12:30:00Z"),
            dueDate = Instant.parse("2025-08-12T12:30:00Z"),
            date = LocalDate.of(2025, 8, 12),
            time = LocalTime.of(12, 30),
            paidFor = Instant.parse("2025-08-12T13:00:00Z"),
            category = commonCategories.find { it.name == "Dining Out" }!!,
            status = TransactionStatus.FAILED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("35.00"),
            title = "Movie Ticket",
            description = "Evening movie at local cinema",
            dateTime = Instant.parse("2025-08-20T20:00:00Z"),
            dueDate = Instant.parse("2025-08-20T20:00:00Z"),
            date = LocalDate.of(2025, 8, 20),
            time = LocalTime.of(20, 0),
            paidFor = Instant.parse("2025-08-20T20:00:00Z"),
            category = commonCategories.find { it.name == "Entertainment" }!!,
            status = TransactionStatus.APPROVED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("300.00"),
            title = "Freelance Gig",
            description = "Payment for graphic design project",
            dateTime = Instant.parse("2025-08-18T16:00:00Z"),
            dueDate = Instant.parse("2025-08-18T16:00:00Z"),
            date = LocalDate.of(2025, 8, 18),
            time = LocalTime.of(16, 0),
            paidFor = Instant.parse("2025-08-18T16:00:00Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("150.00"),
            title = "Investment Dividend",
            description = "Quarterly dividend from stock portfolio",
            dateTime = Instant.parse("2025-08-25T11:30:00Z"),
            dueDate = Instant.parse("2025-08-25T11:30:00Z"),
            date = LocalDate.of(2025, 8, 25),
            time = LocalTime.of(11, 30),
            paidFor = Instant.parse("2025-08-25T11:30:00Z"),
            category = commonCategories.find { it.name == "Investments" }!!,
            status = TransactionStatus.APPROVED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("100.00"),
            title = "Birthday Gift",
            description = "Gift for friend's birthday party",
            dateTime = Instant.parse("2025-08-28T18:00:00Z"),
            dueDate = Instant.parse("2025-08-28T18:00:00Z"),
            date = LocalDate.of(2025, 8, 28),
            time = LocalTime.of(18, 0),
            paidFor = Instant.parse("2025-08-28T18:00:00Z"),
            category = commonCategories.find { it.name == "Entertainment" }!!, // Reusing Entertainment for gift-related
            status = TransactionStatus.COMPLETED,
            id = UUID.randomUUID()
        ),

        // 10 Examples for September 2025

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("52.30"),
            title = "Supermarket Run",
            description = "Monthly stock-up on essentials and snacks",
            dateTime = Instant.parse("2025-09-03T15:10:00Z"),
            dueDate = Instant.parse("2025-09-03T15:10:00Z"),
            date = LocalDate.of(2025, 9, 3),
            time = LocalTime.of(15, 10),
            paidFor = Instant.parse("2025-09-03T16:00:00Z"),
            category = commonCategories.find { it.name == "Groceries" }!!,
            status = TransactionStatus.CANCELED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("2600.00"),
            title = "Salary Deposit",
            description = "Bi-weekly salary payment",
            dateTime = Instant.parse("2025-09-05T08:30:00Z"),
            dueDate = Instant.parse("2025-09-05T08:30:00Z"),
            date = LocalDate.of(2025, 9, 5),
            time = LocalTime.of(8, 30),
            paidFor = Instant.parse("2025-09-05T08:30:00Z"),
            category = commonCategories.find { it.name == "Salary" }!!,
            status = TransactionStatus.PENDING,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("95.00"),
            title = "Internet Bill",
            description = "Monthly internet service fee",
            dateTime = Instant.parse("2025-09-08T09:45:00Z"),
            dueDate = Instant.parse("2025-09-08T09:45:00Z"),
            date = LocalDate.of(2025, 9, 8),
            time = LocalTime.of(9, 45),
            paidFor = Instant.parse("2025-09-08T10:00:00Z"),
            category = commonCategories.find { it.name == "Utilities" }!!,
            status = TransactionStatus.SCHEDULED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("850.00"),
            title = "Mortgage Payment",
            description = "Monthly mortgage installment",
            dateTime = Instant.parse("2025-09-01T00:00:00Z"),
            dueDate = Instant.parse("2025-09-01T00:00:00Z"),
            date = LocalDate.of(2025, 9, 1),
            time = LocalTime.of(0, 0),
            paidFor = Instant.parse("2025-09-01T00:00:00Z"),
            category = commonCategories.find { it.name == "Rent/Mortgage" }!!,
            status = TransactionStatus.FAILED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("40.00"),
            title = "Gas Station",
            description = "Fuel for weekly commute",
            dateTime = Instant.parse("2025-09-08T17:20:00Z"),
            dueDate = Instant.parse("2025-09-08T17:20:00Z"),
            date = LocalDate.of(2025, 9, 8),
            time = LocalTime.of(17, 20),
            paidFor = Instant.parse("2025-09-12T17:20:00Z"),
            category = commonCategories.find { it.name == "Transportation" }!!,
            status = TransactionStatus.COMPLETED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("80.00"),
            title = "Dinner Date",
            description = "Romantic dinner at sushi place",
            dateTime = Instant.parse("2025-09-07T19:00:00Z"),
            dueDate = Instant.parse("2025-09-07T19:00:00Z"),
            date = LocalDate.of(2025, 9, 7),
            time = LocalTime.of(19, 0),
            paidFor = Instant.parse("2025-09-07T20:30:00Z"),
            category = commonCategories.find { it.name == "Dining Out" }!!,
            status = TransactionStatus.REJECTED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("50.00"),
            title = "Streaming Subscription",
            description = "Monthly fee for video streaming service",
            dateTime = Instant.parse("2025-09-07T00:00:00Z"),
            dueDate = Instant.parse("2025-09-07T00:00:00Z"),
            date = LocalDate.of(2025, 9, 7),
            time = LocalTime.of(0, 0),
            paidFor = Instant.parse("2025-09-07T00:00:00Z"),
            category = commonCategories.find { it.name == "Entertainment" }!!,
            status = TransactionStatus.CANCELED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("450.00"),
            title = "Freelance Writing",
            description = "Payment for article submission",
            dateTime = Instant.parse("2025-09-08T14:00:00Z"),
            dueDate = Instant.parse("2025-09-08T14:00:00Z"),
            date = LocalDate.of(2025, 9, 8),
            time = LocalTime.of(14, 0),
            paidFor = Instant.parse("2025-09-08T14:00:00Z"),
            category = commonCategories.find { it.name == "Freelance" }!!,
            status = TransactionStatus.REJECTED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("200.00"),
            title = "Stock Gains",
            description = "Profit from selling shares",
            dateTime = Instant.parse("2025-09-03T10:00:00Z"),
            dueDate = Instant.parse("2025-09-03T10:00:00Z"),
            date = LocalDate.of(2025, 9, 3),
            time = LocalTime.of(10, 0),
            paidFor = Instant.parse("2025-09-03T10:00:00Z"),
            category = commonCategories.find { it.name == "Investments" }!!,
            status = TransactionStatus.APPROVED,
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            type = TransactionType.INCOME,
            amount = BigDecimal("100.00"),
            title = "Birthday Gift Received",
            description = "Cash gift from family for birthday",
            dateTime = Instant.parse("2025-09-05T12:00:00Z"),
            dueDate = Instant.parse("2025-09-05T12:00:00Z"),
            date = LocalDate.of(2025, 9, 5),
            time = LocalTime.of(12, 0),
            paidFor = Instant.parse("2025-09-05T12:00:00Z"),
            category = commonCategories.find { it.name == "Gifts" }!!,
            status = TransactionStatus.ON_HOLD,
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

    val transactionReportIncome = TransactionReport(
        accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        type = TransactionType.INCOME,
        amount = BigDecimal("1504.75"),
        percentage = 0.015,
        startDateTime = Instant.parse("2025-09-03T10:00:00Z"),
        endDateTime = Instant.parse("2025-09-03T10:01:00Z")
    )

    val transactionReportExpense = TransactionReport(
        accountId = UUID.fromString("987fcdeb-51a2-4b7e-9f7b-8d7a12345678"),
        type = TransactionType.EXPENSE,
        amount = BigDecimal("500.00"),
        percentage = 0.02,
        startDateTime = Instant.parse("2025-09-02T14:30:00Z"),
        endDateTime = Instant.parse("2025-09-02T14:31:00Z")
    )
}