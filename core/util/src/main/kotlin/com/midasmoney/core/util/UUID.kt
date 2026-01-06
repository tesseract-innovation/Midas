package com.midasmoney.core.util

import com.midasmoney.core.util.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlin.random.Random

/**
 * A data class representing a UUID (Universally Unique Identifier) as a string.
 * The UUID is validated to ensure it follows the standard format (8-4-4-4-12 characters).
 *
 * @param value The string representation of the UUID.
 * @throws IllegalArgumentException If the provided value does not match the UUID format.
 */
@Serializable(with = UUIDSerializer::class)
data class UUID(val value: String) {
    init {
        require(value.matches(Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"))) {
            "Invalid UUID format: $value"
        }
    }

    companion object {
        /**
         * Generates a random UUID in the standard 8-4-4-4-12 format.
         *
         * @return A new [UUID] instance with a randomly generated value.
         */
        fun randomUUID(): UUID {
            val chars = "0123456789abcdef"
            val segments = listOf(8, 4, 4, 4, 12)
            val uuidBuilder = StringBuilder()
            segments.forEachIndexed { index, size ->
                repeat(size) {
                    uuidBuilder.append(chars[Random.nextInt(16)])
                }
                if (index < segments.size - 1) {
                    uuidBuilder.append('-')
                }
            }
            return UUID(uuidBuilder.toString())
        }
    }

    /**
     * Returns the string representation of the UUID.
     *
     * @return The UUID value as a string.
     */
    override fun toString(): String = value
}

