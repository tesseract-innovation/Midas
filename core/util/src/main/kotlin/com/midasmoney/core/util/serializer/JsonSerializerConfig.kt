package com.midasmoney.core.util.serializer

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
val customModule = SerializersModule {
    contextual(Instant::class, InstantSerializer)
}

@Suppress("unused")
val customJson = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    serializersModule = customModule
}