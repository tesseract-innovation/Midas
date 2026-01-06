package com.midasmoney.core.domain.model

import com.midasmoney.core.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Enum class representing different types of financial transactions.
 */
@Suppress("unused")
enum class TransactionType(val displayName: String) {
    /**
     * Represents outgoing transactions or expenses, such as purchases or bills.
     */
    EXPENSE("Expense"),

    /**
     * Represents incoming transactions or income, such as salaries or gifts.
     */
    INCOME("Income"),

    /**
     * Represents funds transferred between accounts within the same system, resulting in no net balance change.
     */
    TRANSFER("Transfer"),

    /**
     * Represents physical withdrawal of funds, such as cash withdrawn from an ATM.
     */
    WITHDRAWAL("Withdrawal"),

    /**
     * Represents the addition of funds to an account, such as cash deposits or salary credits.
     */
    DEPOSIT("Deposit"),

    /**
     * Represents charges or penalties incurred, such as bank fees or late payment penalties.
     */
    FEES("Fees"),

    /**
     * Represents earned interest (e.g., on savings) or paid interest (e.g., on loans).
     */
    INTEREST("Interest"),

    /**
     * Represents the reversal of a previous transaction, such as a refunded purchase.
     */
    REFUND("Refund"),

    /**
     * Represents automated, scheduled recurring payments, such as subscriptions or utility bills.
     */
    RECURRING("Recurring"),

    /**
     * Represents tax-related transactions, such as tax payments or refunds.
     */
    TAX("Tax"),

    /**
     * Represents irregular income from bonuses, such as work or performance bonuses.
     */
    BONUS("Bonus"),

    /**
     * Represents income received from investment dividends.
     */
    DIVIDEND("Dividend"),

    /**
     * Represents payments made toward loans or credit card debt.
     */
    LOAN_PAYMENT("Loan Payment");

    companion object {
        fun getByName(target: String): TransactionType {
            return entries.find { it.name == target } ?: TRANSFER
        }
    }
}

/**
 * Enum class representing the status of a financial transaction.
 */
@Suppress("unused")
enum class TransactionStatus(val displayName: String) {
    /**
     * Transaction has been created but is not yet processed.
     */
    PENDING("Pending"),

    /**
     * Transaction is scheduled for future execution, such as recurring payments.
     */
    SCHEDULED("Scheduled"),

    /**
     * Transaction has been completed successfully.
     */
    COMPLETED("Completed"),

    /**
     * Transaction was canceled before execution.
     */
    CANCELED("Canceled"),

    /**
     * Transaction failed during processing.
     */
    FAILED("Failed"),

    /**
     * Transaction has been approved but is not yet executed, such as authorized payments.
     */
    APPROVED("Approved"),

    /**
     * Transaction was rejected, for example, due to fraud verification.
     */
    REJECTED("Rejected"),

    /**
     * Transaction is on hold, pending manual review or additional verification.
     */
    ON_HOLD("On Hold"),

    /**
     * Transaction is part of a batch processing group, typically for grouped transactions.
     */
    BATCH_PROCESSING("Batch processing");

    companion object {
        fun getByName(target: String): TransactionStatus {
            return TransactionStatus.entries.find { it.name == target } ?: PENDING
        }
    }
}

@Serializable
@OptIn(ExperimentalTime::class)
data class Transaction(
    val accountId: UUID,
    val icon: IconModel,
    val color: Int,
    val title: String,
    val description: String,
    val type: TransactionType,
    val status: TransactionStatus,
    val amount: Double,
    val date: String,
    val time: String,
    @Contextual val createAt: Instant,
    val id: UUID = UUID.Companion.randomUUID()
)

@OptIn(ExperimentalTime::class)
data class TransactionReport(
    val accountId: UUID,
    val type: TransactionType,
    val amount: Double,
    val percentage: Double,
    @Contextual val startDateTime: Instant,
    @Contextual val endDateTime: Instant,
    val id: UUID = UUID.Companion.randomUUID()
)
