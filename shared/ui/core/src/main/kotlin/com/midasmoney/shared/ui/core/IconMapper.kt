package com.midasmoney.shared.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import com.midasmoney.shared.model.data.IconModel
import com.midasmoney.shared.model.data.IconType

@Suppress("DEPRECATION")
object IconMapper {
    fun getImageVector(iconModel: IconModel): ImageVector {
        return when (iconModel.iconType) {
            IconType.SETTINGS -> Icons.Default.Settings
            IconType.PROFILE -> Icons.Default.Person
            IconType.HOME -> Icons.Filled.Home
            IconType.SHOPPING_CART -> Icons.Filled.ShoppingCart
            IconType.BOLT -> Icons.Filled.Bolt
            IconType.DIRECTIONS_CAR -> Icons.Filled.DirectionsCar
            IconType.RESTAURANT -> Icons.Filled.Restaurant
            IconType.THEATER_COMEDY -> Icons.Filled.TheaterComedy
            IconType.WORK -> Icons.Filled.Work
            IconType.BUILD -> Icons.Filled.Build
            IconType.TRENDING_UP -> Icons.Filled.TrendingUp
            IconType.CARD_GIFT_CARD -> Icons.Filled.CardGiftcard
            IconType.SAVINGS -> Icons.Filled.Savings
            IconType.FLIGHT -> Icons.Filled.Flight
            IconType.MONEY -> Icons.Filled.Money
        }
    }

    fun getIconType(icon: ImageVector): IconType? {
        return when (icon) {
            Icons.Default.Settings -> IconType.SETTINGS
            Icons.Default.Person -> IconType.PROFILE
            Icons.Filled.Home -> IconType.HOME
            Icons.Filled.ShoppingCart -> IconType.SHOPPING_CART
            Icons.Filled.Bolt -> IconType.BOLT
            Icons.Filled.DirectionsCar -> IconType.DIRECTIONS_CAR
            Icons.Filled.Restaurant -> IconType.RESTAURANT
            Icons.Filled.TheaterComedy -> IconType.THEATER_COMEDY
            Icons.Filled.Work -> IconType.WORK
            Icons.Filled.Build -> IconType.BUILD
            Icons.Filled.TrendingUp -> IconType.TRENDING_UP
            Icons.Filled.CardGiftcard -> IconType.CARD_GIFT_CARD
            Icons.Filled.Savings -> IconType.SAVINGS
            Icons.Filled.Flight -> IconType.FLIGHT
            Icons.Filled.Money -> IconType.MONEY
            else -> null
        }
    }
}