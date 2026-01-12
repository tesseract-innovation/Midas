package com.midasmoney.core.data.mock

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Balance
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionReport
import com.midasmoney.core.domain.model.TransactionStatus
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.util.UUID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object Database {
    @OptIn(ExperimentalTime::class)
    val transactions = listOf(
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("66.90").toDouble().toDouble(),
            title = "Supermarket",
            description = "Weekly grocery purchase at Supermarket",
            date = "2025-07-10",
            time = "14:30",
            createAt = Instant.parse("2025-07-10T14:30:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFFFF5252.toInt(), // vermelho p/ despesa
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("23.99").toDouble(),
            title = "Web Development Payment",
            description = "Weekly grocery purchase at Supermarket",
            date = "2025-07-10",
            time = "14:30",
            createAt = Instant.parse("2025-07-10T14:30:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFF4CAF50.toInt(), // verde p/ receita
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("23.99").toDouble(),
            title = "ADS Payment",
            description = "Weekly grocery purchase at Supermarket",
            date = "2025-07-10",
            time = "14:30",
            createAt = Instant.parse("2025-07-10T14:30:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("45.99").toDouble(),
            title = "Grocery Shopping",
            description = "Weekly grocery purchase at Supermarket",
            date = "2025-07-10",
            time = "14:30",
            createAt = Instant.parse("2025-07-10T14:30:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("1500.00").toDouble(),
            title = "Freelance Payment",
            description = "Payment for web development project",
            date = "2025-07-09",
            time = "09:00",
            createAt = Instant.parse("2025-07-09T09:00:00Z"),
            status = TransactionStatus.SCHEDULED,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("120.50").toDouble(),
            title = "Electricity Bill",
            description = "Monthly electricity bill for July",
            date = "2025-07-08",
            time = "10:15",
            createAt = Instant.parse("2025-07-08T10:15:00Z"),
            status = TransactionStatus.COMPLETED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("250.75").toDouble(),
            title = "Refund",
            description = "Refund for returned electronics",
            date = "2025-07-07",
            time = "16:45",
            createAt = Instant.parse("2025-07-07T16:45:00Z"),
            status = TransactionStatus.CANCELED,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Restaurant)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("75.00").toDouble(),
            title = "Restaurant Dinner",
            description = "Dinner with friends at Italian restaurant",
            date = "2025-07-06",
            time = "19:30",
            createAt = Instant.parse("2025-07-06T19:30:00Z"),
            status = TransactionStatus.FAILED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

// 10 Examples for August 2025
        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Restaurant)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("45.50").toDouble(),
            title = "Grocery Shopping",
            description = "Weekly groceries including fruits, vegetables, and dairy",
            date = "2025-08-02",
            time = "14:20",
            createAt = Instant.parse("2025-08-02T14:20:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("2500.00").toDouble(),
            title = "Monthly Salary",
            description = "Regular paycheck from full-time job",
            date = "2025-08-05",
            time = "09:00",
            createAt = Instant.parse("2025-08-05T09:00:00Z"),
            status = TransactionStatus.ON_HOLD,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("120.00").toDouble(),
            title = "Electricity Bill",
            description = "Monthly utility bill for home electricity",
            date = "2025-08-10",
            time = "10:15",
            createAt = Instant.parse("2025-08-10T10:15:00Z"),
            status = TransactionStatus.REJECTED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("800.00").toDouble(),
            title = "Rent Payment",
            description = "Monthly rent for apartment",
            date = "2025-08-01",
            time = "00:00",
            createAt = Instant.parse("2025-08-01T00:00:00Z"),
            status = TransactionStatus.SCHEDULED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("25.00").toDouble(),
            title = "Bus Fare",
            description = "Public transportation to work",
            date = "2025-08-15",
            time = "07:45",
            createAt = Instant.parse("2025-08-15T07:45:00Z"),
            status = TransactionStatus.CANCELED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("60.75").toDouble(),
            title = "Lunch at Cafe",
            description = "Casual lunch outing with colleague",
            date = "2025-08-12",
            time = "12:30",
            createAt = Instant.parse("2025-08-12T12:30:00Z"),
            status = TransactionStatus.FAILED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("35.00").toDouble(),
            title = "Movie Ticket",
            description = "Evening movie at local cinema",
            date = "2025-08-20",
            time = "20:00",
            createAt = Instant.parse("2025-08-20T20:00:00Z"),
            status = TransactionStatus.APPROVED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("300.00").toDouble(),
            title = "Freelance Gig",
            description = "Payment for graphic design project",
            date = "2025-08-18",
            time = "16:00",
            createAt = Instant.parse("2025-08-18T16:00:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("150.00").toDouble(),
            title = "Investment Dividend",
            description = "Quarterly dividend from stock portfolio",
            date = "2025-08-25",
            time = "11:30",
            createAt = Instant.parse("2025-08-25T11:30:00Z"),
            status = TransactionStatus.APPROVED,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("100.00").toDouble(),
            title = "Birthday Gift",
            description = "Gift for friend's birthday party",
            date = "2025-08-28",
            time = "18:00",
            createAt = Instant.parse("2025-08-28T18:00:00Z"),
            status = TransactionStatus.COMPLETED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

// 10 Examples for September 2025

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("52.30").toDouble(),
            title = "Supermarket Run",
            description = "Monthly stock-up on essentials and snacks",
            date = "2025-09-03",
            time = "15:10",
            createAt = Instant.parse("2025-09-03T15:10:00Z"),
            status = TransactionStatus.CANCELED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("2600.00").toDouble(),
            title = "Salary Deposit",
            description = "Bi-weekly salary payment",
            date = "2025-09-05",
            time = "08:30",
            createAt = Instant.parse("2025-09-05T08:30:00Z"),
            status = TransactionStatus.PENDING,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("95.00").toDouble(),
            title = "Internet Bill",
            description = "Monthly internet service fee",
            date = "2025-09-08",
            time = "09:45",
            createAt = Instant.parse("2025-09-08T09:45:00Z"),
            status = TransactionStatus.SCHEDULED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("850.00").toDouble(),
            title = "Mortgage Payment",
            description = "Monthly mortgage installment",
            date = "2025-09-01",
            time = "00:00",
            createAt = Instant.parse("2025-09-01T00:00:00Z"),
            status = TransactionStatus.FAILED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("40.00").toDouble(),
            title = "Gas Station",
            description = "Fuel for weekly commute",
            date = "2025-09-08",
            time = "17:20",
            createAt = Instant.parse("2025-09-08T17:20:00Z"),
            status = TransactionStatus.COMPLETED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("80.00").toDouble(),
            title = "Dinner Date",
            description = "Romantic dinner at sushi place",
            date = "2025-09-07",
            time = "19:00",
            createAt = Instant.parse("2025-09-07T19:00:00Z"),
            status = TransactionStatus.REJECTED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.EXPENSE,
            amount = BigDecimal("50.00").toDouble(),
            title = "Streaming Subscription",
            description = "Monthly fee for video streaming service",
            date = "2025-09-07",
            time = "00:00",
            createAt = Instant.parse("2025-09-07T00:00:00Z"),
            status = TransactionStatus.CANCELED,
            color = 0xFFFF5252.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("450.00").toDouble(),
            title = "Freelance Writing",
            description = "Payment for article submission",
            date = "2025-09-08",
            time = "14:00",
            createAt = Instant.parse("2025-09-08T14:00:00Z"),
            status = TransactionStatus.REJECTED,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("200.00").toDouble(),
            title = "Stock Gains",
            description = "Profit from selling shares",
            date = "2025-09-03",
            time = "10:00",
            createAt = Instant.parse("2025-09-03T10:00:00Z"),
            status = TransactionStatus.APPROVED,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        ),

        Transaction(
            accountId = UUID.randomUUID(),
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            type = TransactionType.INCOME,
            amount = BigDecimal("100.00").toDouble(),
            title = "Birthday Gift Received",
            description = "Cash gift from family for birthday",
            date = "2025-09-05",
            time = "12:00",
            createAt = Instant.parse("2025-09-05T12:00:00Z"),
            status = TransactionStatus.ON_HOLD,
            color = 0xFF4CAF50.toInt(),
            id = UUID.randomUUID()
        )
    )

    val goalList = listOf(
        Goal(
            title = "Emergency Fund",
            description = "Save for unexpected expenses",
            amount = 5000.0,
            progress = 2000.0,
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Savings)!!),
            targetDate = LocalDate(2025, 2, 1),
            monthlyValue = 500.0,
            color = 0xFF6750A4.toInt()
        ),
        Goal(
            title = "Vacation",
            description = "Trip to Europe next summer",
            amount = 3000.0,
            progress = 750.0,
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Flight)!!),
            targetDate = LocalDate(2025, 3, 1),
            monthlyValue = 640.0,
            color = 0xFF4CAF50.toInt()
        ),
        Goal(
            title = "New Car",
            description = "Down payment for a new vehicle",
            amount = 10000.0,
            progress = 4000.0,
            icon = IconModel(IconConverter.getIconType(Icons.Filled.DirectionsCar)!!),
            targetDate = LocalDate(2025, 4, 1),
            monthlyValue = 231.0,
            color = 0xFF2196F3.toInt()
        ),
        Goal(
            title = "Pay Off Credit Card",
            description = "Clear outstanding credit card debt",
            amount = 2000.0,
            progress = 1200.0,
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Money)!!),
            targetDate = LocalDate(2025, 5, 1),
            monthlyValue = 123.0,
            color = 0xFFF44336.toInt()
        ),
        Goal(
            title = "Home Renovation",
            description = "Remodel kitchen and bathroom",
            amount = 15000.0,
            progress = 3000.0,
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Home)!!),
            targetDate = LocalDate(2025, 6, 1),
            monthlyValue = 882.0,
            color = 0xFFFF9800.toInt()
        )
    )

    val balance = Balance(
        initialBalance = 4231.23,
        currentBalance = 14250.00,
        income = 3200.00,
        expense = 1850.00
    )

    val transactionReportIncome = TransactionReport(
        accountId = UUID("123e4567-e89b-12d3-a456-426614174000"),
        type = TransactionType.INCOME,
        amount = BigDecimal("1504.75").toDouble(),
        percentage = 0.015,
        startDateTime = Instant.parse("2025-09-03T10:00:00Z"),
        endDateTime = Instant.parse("2025-09-03T10:01:00Z")
    )

    val transactionReportExpense = TransactionReport(
        accountId = UUID("987fcdeb-51a2-4b7e-9f7b-8d7a12345678"),
        type = TransactionType.EXPENSE,
        amount = BigDecimal("500.00").toDouble(),
        percentage = 0.02,
        startDateTime = Instant.parse("2025-09-02T14:30:00Z"),
        endDateTime = Instant.parse("2025-09-02T14:31:00Z")
    )

    val accounts: List<Account> = listOf(
        Account(
            name = "Checking Account",
            icon = IconModel(IconConverter.getIconType(Icons.Filled.AccountBalance)!!),
            color = 0xFFFF9800.toInt(),
            balance = Balance(
                initialBalance = 234.23,
                currentBalance = 2500.75,
                income = 3000.00,
                expense = 499.25
            ),
            transactions = listOf(
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.INCOME,
                    amount = BigDecimal("2000.00").toDouble(),
                    title = "Salary",
                    description = "Monthly salary payment",
                    date = "2025-09-01",
                    time = "09:00",
                    createAt = Instant.parse("2025-09-01T09:00:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFF4CAF50.toInt() // verde para receita
                ),
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.EXPENSE,
                    amount = BigDecimal("150.00").toDouble(),
                    title = "Groceries",
                    description = "Weekly grocery shopping",
                    date = "2025-09-10",
                    time = "14:30",
                    createAt = Instant.parse("2025-09-10T14:30:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFFFF5252.toInt() // vermelho para despesa
                )
            )
        ),
        Account(
            name = "Savings Account",
            icon = IconModel(IconConverter.getIconType(Icons.Filled.Savings)!!),
            color = 0xFFE91E63.toInt(),
            balance = Balance(
                initialBalance = 234.23,
                currentBalance = 10000.00,
                income = 500.00,
                expense = 0.00
            ),
            transactions = listOf(
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.DEPOSIT,
                    amount = BigDecimal("500.00").toDouble(),
                    title = "Savings Deposit",
                    description = "Monthly savings contribution",
                    date = "2025-09-05",
                    time = "10:00",
                    createAt = Instant.parse("2025-09-05T10:00:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFF4CAF50.toInt() // verde p/ depósito
                ),
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.INTEREST,
                    amount = BigDecimal("10.00").toDouble(),
                    title = "Interest Earned",
                    description = "Monthly interest",
                    date = "2025-09-12",
                    time = "12:00",
                    createAt = Instant.parse("2025-09-12T12:00:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFF4CAF50.toInt() // verde p/ juros
                )
            )
        ),
        Account(
            name = "Credit Card",
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            color = 0xFF00BCD4.toInt(),
            balance = Balance(
                initialBalance = 32352.81,
                currentBalance = -300.50,
                income = 0.00,
                expense = 300.50
            ),
            transactions = listOf(
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.EXPENSE,
                    amount = BigDecimal("200.00").toDouble(),
                    title = "Restaurant",
                    description = "Dinner with friends",
                    date = "2025-09-08",
                    time = "19:00",
                    createAt = Instant.parse("2025-09-08T19:00:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFFFF5252.toInt() // vermelho para despesa
                ),
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.LOAN_PAYMENT,
                    amount = BigDecimal("100.50").toDouble(),
                    title = "Credit Card Payment",
                    description = "Partial payment of credit card balance",
                    date = "2025-09-13",
                    time = "15:00",
                    createAt = Instant.parse("2025-09-13T15:00:00Z"),
                    status = TransactionStatus.PENDING,
                    color = 0xFFFF5252.toInt() // vermelho para pagamento de empréstimo (saída)
                )
            )
        ),
        Account(
            name = "Bradesco",
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            color = 0xFFCDDC39.toInt(),
            balance = Balance(
                initialBalance = 634553.22,
                currentBalance = -300.50,
                income = 0.00,
                expense = 300.50
            ),
            transactions = listOf(
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.EXPENSE,
                    amount = BigDecimal("200.00").toDouble(),
                    title = "Restaurant",
                    description = "Dinner with friends",
                    date = "2025-09-08",
                    time = "19:00",
                    createAt = Instant.parse("2025-09-08T19:00:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFFFF5252.toInt() // vermelho para despesa
                ),
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.LOAN_PAYMENT,
                    amount = BigDecimal("100.50").toDouble(),
                    title = "Credit Card Payment",
                    description = "Partial payment of credit card balance",
                    date = "2025-09-13",
                    time = "15:00",
                    createAt = Instant.parse("2025-09-13T15:00:00Z"),
                    status = TransactionStatus.PENDING,
                    color = 0xFFFF5252.toInt() // vermelho para pagamento de empréstimo
                )
            )
        ),
        Account(
            name = "Nubank",
            icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
            color = 0xFFFF5722.toInt(),
            balance = Balance(
                initialBalance = 532453.2,
                currentBalance = -300.50,
                income = 0.00,
                expense = 300.50
            ),
            transactions = listOf(
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.EXPENSE,
                    amount = BigDecimal("200.00").toDouble(),
                    title = "Restaurant",
                    description = "Dinner with friends",
                    date = "2025-09-08",
                    time = "19:00",
                    createAt = Instant.parse("2025-09-08T19:00:00Z"),
                    status = TransactionStatus.COMPLETED,
                    color = 0xFFFF5252.toInt() // vermelho para despesa
                ),
                Transaction(
                    accountId = UUID.randomUUID(),
                    icon = IconModel(IconConverter.getIconType(Icons.Filled.CreditCard)!!),
                    type = TransactionType.LOAN_PAYMENT,
                    amount = BigDecimal("100.50").toDouble(),
                    title = "Credit Card Payment",
                    description = "Partial payment of credit card balance",
                    date = "2025-09-13",
                    time = "15:00",
                    createAt = Instant.parse("2025-09-13T15:00:00Z"),
                    status = TransactionStatus.PENDING,
                    color = 0xFFFF5252.toInt() // vermelho para pagamento (saída)
                )
            )
        )
    )
}
