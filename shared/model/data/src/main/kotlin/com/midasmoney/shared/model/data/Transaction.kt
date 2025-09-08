package com.midasmoney.shared.model.data

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

enum class TransactionType {
    EXPENSE, // Represents outgoing transactions or expenses.
    INCOME, // Represents incoming transactions or income.
    TRANSFER, // Funds transferred between accounts within the same system (no net balance change).
    WITHDRAWAL, // Physical withdrawal of funds (e.g., ATM cash withdrawal).
    DEPOSIT, // Addition of funds to an account (e.g., cash deposit, salary).
    FEES, // Charges or penalties incurred (e.g., bank fees, late payments).
    INTEREST, // Earned interest (e.g., savings) or paid interest (e.g., loans).
    REFUND, // Reversal of a previous transaction (e.g., refunded purchase).
    RECURRING, // Automated, scheduled recurring payments (e.g., subscriptions).
    TAX, // Tax-related transactions (e.g., payments, refunds).
    BONUS, // Irregular income from bonuses (e.g., work bonuses).
    DIVIDEND, // Income from investment dividends.
    LOAN_PAYMENT // Payments toward loans or credit card debt.
}

enum class TransactionStatus {
    PENDING,      // Transaction created but not yet processed
    SCHEDULED,    // Scheduled for future execution (e.g., recurring payments)
    COMPLETED,    // Completed successfully
    CANCELED,     // Canceled before execution
    FAILED,       // Failed during processing
    APPROVED,     // Approved but not yet executed (e.g., authorized payments)
    REJECTED,     // Rejected (e.g., fraud verification)
    ON_HOLD,      // On hold (e.g., pending manual review)
    BATCH_PROCESSING // Batch processing (grouped transactions)
}

data class Category(
    val name: String,
    val type: TransactionType,
    val description: String,
    val icon: IconModel,
    val color: Int,
    val id: UUID = UUID.randomUUID()
)

data class Transaction(
    val accountId: UUID,
    val type: TransactionType,
    val amount: BigDecimal,
    val title: String,
    val description: String,
    val dateTime: Instant,
    val dueDate: Instant,
    val date: LocalDate,
    val time: LocalTime,
    val paidFor: Instant,
    val category: Category,
    val status: TransactionStatus,
    val id: UUID = UUID.randomUUID()
)

data class TransactionReport(
    val accountId: UUID,
    val type: TransactionType,
    val amount: BigDecimal,
    val percentage: Double,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val id: UUID = UUID.randomUUID()
)