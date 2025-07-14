package com.midasmoney.shared.model.data

enum class IconType {
    SETTINGS,
    PROFILE,
    HOME,
    SHOPPING_CART,
    BOLT,
    DIRECTIONS_CAR,
    RESTAURANT,
    THEATER_COMEDY,
    WORK,
    BUILD,
    TRENDING_UP,
    CARD_GIFT_CARD,
    SAVINGS,
    FLIGHT,
    MONEY,
}

data class IconModel(
    val iconType: IconType
)