package com.midasmoney.core.util.serializer

import com.midasmoney.core.util.UUID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A custom serializer for the [com.midasmoney.core.util.UUID] class, enabling serialization and deserialization
 * of [com.midasmoney.core.util.UUID] instances to and from strings using kotlinx.serialization.
 */
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    /**
     * Serializes a [UUID] instance to its string representation.
     *
     * @param encoder The encoder used to serialize the UUID.
     * @param value The [UUID] instance to serialize.
     */
    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.value)
    }

    /**
     * Deserializes a string into a [UUID] instance.
     *
     * @param decoder The decoder used to read the string.
     * @return A [UUID] instance created from the decoded string.
     * @throws IllegalArgumentException If the decoded string does not match the UUID format.
     */
    override fun deserialize(decoder: Decoder): UUID {
        return UUID(decoder.decodeString())
    }
}