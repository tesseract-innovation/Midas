package com.midasmoney.core.util.serializer

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

/**
 * Creates a custom navigation type for Parcelable objects.
 * This method defines a custom navigation type that enables passing Parcelable objects
 * between navigation destinations. It uses the Kotlin serialization library to convert
 * Parcelable objects to JSON strings and vice versa. Compatibility with different Android
 * versions is ensured through version checks.
 *
 * @param T The type of the Parcelable object.
 * @param isNullableAllowed Indicates whether null values are allowed.
 * @param json Instance of Json for serialization and deserialization.
 * @return A custom NavType object for the specified Parcelable type.
 */
@Suppress("unused")
inline fun <reified T> serializableNavType(
    isNullableAllowed: Boolean = false,
    json: Json = Json
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? {
        val str = bundle.getString(key) ?: return null
        return json.decodeFromString(str)
    }

    override fun parseValue(value: String): T =
        json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: T): String =
        Uri.encode(json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}
