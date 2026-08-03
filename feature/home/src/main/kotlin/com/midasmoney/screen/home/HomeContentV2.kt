package com.midasmoney.screen.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// ─── Models ──────────────────────────────────────────────────────────────────

data class MonthlyData(
    val month: String,
    val income: Float,
    val expense: Float,
)

data class CategoryData(
    val name: String,
    val percentage: Float,
    val color: Color,
)

data class TransactionItem(
    val id: String,
    val title: String,
    val type: String,
    val date: String,
    val amount: Double,
    val status: String,
    val color: Color,
    val isExpense: Boolean,
)

data class GoalSummary(
    val id: String,
    val title: String,
    val current: Double,
    val target: Double,
    val iconColor: Color,
)

data class HomeUiStateV2(
    val userName: String = "Dayvson",
    val currentMonth: String = "Maio 2026",
    val totalBalance: Double = 14_250.00,
    val totalIncome: Double = 3_200.00,
    val totalExpense: Double = 1_850.00,
    val totalInvested: Double = 500.00,
    val isBalanceVisible: Boolean = true,
    val monthlyData: List<MonthlyData> = sampleMonthlyData,
    val categoryData: List<CategoryData> = sampleCategories,
    val recentTransactions: List<TransactionItem> = sampleTransactions,
    val goals: List<GoalSummary> = sampleGoals,
    val selectedMonthIndex: Int = 4,
)

private val sampleMonthlyData = listOf(
    MonthlyData("Jan", 2800f, 1200f),
    MonthlyData("Fev", 3100f, 1500f),
    MonthlyData("Mar", 2600f, 900f),
    MonthlyData("Abr", 3400f, 2100f),
    MonthlyData("Mai", 3200f, 1850f),
)

private val sampleCategories = listOf(
    CategoryData("Transporte", 36f, MidasColors.Gray),
    CategoryData("Alimentação", 28f, MidasColors.Green.dark),
    CategoryData("Lazer", 18f, MidasColors.Green.light),
    CategoryData("Entretenimento", 12f, MidasColors.Orange.primary),
    CategoryData("Investimentos", 6f, MidasColors.Purple.primary),
)

private val sampleTransactions = listOf(
    TransactionItem("1", "Supermarket", "Saída", "Jul 10", 66.90, "Pending", MidasColors.Red.dark, true),
    TransactionItem("2", "Web Dev Payment", "Entrada", "Jul 10", 23.99, "Pending", MidasColors.Green.dark, false),
    TransactionItem("3", "Salário", "Entrada", "Jul 5", 3_200.00, "Approved", MidasColors.Green.dark, false),
)

private val sampleGoals = listOf(
    GoalSummary("1", "Emergency Fund", 2_000.0, 5_000.0, MidasColors.Purple.dark),
    GoalSummary("2", "Vacation Trip", 800.0, 3_000.0, MidasColors.Blue.dark),
)

// ─── Screen ──────────────────────────────────────────────────────────────────

@Composable
fun HomeScreenV2(
    navController: NavHostController,
    paddingValues: PaddingValues,
    uiState: HomeUiStateV2 = HomeUiStateV2(),
    onToggleBalance: () -> Unit = {},
    onViewAllTransactions: () -> Unit = {},
    onManageGoals: () -> Unit = {},
    onTransactionClick: (String) -> Unit = {},
    onMonthSelected: (Int) -> Unit = {},
) {
    MidasTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {

                // ── Hero banner ──────────────────────────────────────────
                HeroBalanceCard(uiState = uiState, onToggleBalance = onToggleBalance)

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {

                    // ── Bar chart: fluxo mensal ──────────────────────────
                    SectionHeader("Fluxo mensal", "2026", onViewAllTransactions)
                    Spacer(modifier = Modifier.height((-8).dp))
                    MonthlyBarChartCard(
                        data = uiState.monthlyData,
                        selectedIndex = uiState.selectedMonthIndex,
                        onMonthSelected = onMonthSelected,
                    )

                    // ── Donut chart: categorias ──────────────────────────
                    SectionHeader("Gastos por categoria", "Mai", {})
                    Spacer(modifier = Modifier.height((-8).dp))
                    CategoryDonutCard(categories = uiState.categoryData, totalExpense = uiState.totalExpense)

                    // ── Recent transactions ──────────────────────────────
                    SectionHeader("Transações recentes", "Ver todas", onViewAllTransactions)
                    Spacer(modifier = Modifier.height((-8).dp))
                    Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
                        Column {
                            uiState.recentTransactions.take(3).forEachIndexed { i, tx ->
                                TransactionRow(item = tx, onClick = { onTransactionClick(tx.id) })
                                if (i < 2) HorizontalDivider(
                                    modifier = Modifier.padding(start = 66.dp),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                )
                            }
                        }
                    }

                    // ── Goals ────────────────────────────────────────────
                    SectionHeader("Metas financeiras", "Gerenciar", onManageGoals)
                    Spacer(modifier = Modifier.height((-8).dp))
                    Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
                        Column {
                            uiState.goals.forEachIndexed { i, goal ->
                                GoalRowCompact(goal = goal)
                                if (i < uiState.goals.lastIndex) HorizontalDivider(
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

// ─── Hero Balance Card ───────────────────────────────────────────────────────

@Composable
private fun HeroBalanceCard(uiState: HomeUiStateV2, onToggleBalance: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        MidasColors.Purple.extraDark,
                        MidasColors.Blue.dark,
                        MidasColors.Green.extraDark,
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                ),
            ),
    ) {
        // Decorative circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 220.dp, y = (-60).dp)
                .clip(CircleShape)
                .background(MidasColors.White.copy(alpha = 0.04f)),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            // Top row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        "Bom dia, ${uiState.userName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MidasColors.White.copy(alpha = 0.65f),
                    )
                    Text(
                        uiState.currentMonth,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MidasColors.White,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MidasColors.White.copy(alpha = 0.1f))
                        .clickable(onClick = onToggleBalance),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Outlined.Notifications, null, tint = MidasColors.White, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Balance
            Text(
                "Saldo total",
                style = MaterialTheme.typography.labelSmall,
                color = MidasColors.White.copy(alpha = 0.6f),
            )
            Text(
                if (uiState.isBalanceVisible) "R$ ${"%.2f".format(uiState.totalBalance)}" else "R$ ••••••",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MidasColors.White,
                modifier = Modifier.padding(top = 4.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(thickness = 0.5.dp, color = MidasColors.White.copy(alpha = 0.12f))
            Spacer(modifier = Modifier.height(16.dp))

            // Stats row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                HeroStat(
                    modifier = Modifier.weight(1f),
                    label = "Entradas",
                    value = "R$ ${"%.0f".format(uiState.totalIncome)}",
                    icon = Icons.Outlined.ArrowDownward,
                    iconColor = MidasColors.Green.primary,
                )
                Box(
                    modifier = Modifier
                        .width(0.5.dp)
                        .height(40.dp)
                        .background(MidasColors.White.copy(alpha = 0.12f))
                        .align(Alignment.CenterVertically),
                )
                HeroStat(
                    modifier = Modifier.weight(1f),
                    label = "Saídas",
                    value = "R$ ${"%.0f".format(uiState.totalExpense)}",
                    icon = Icons.Outlined.ArrowUpward,
                    iconColor = MidasColors.Red.primary,
                )
                Box(
                    modifier = Modifier
                        .width(0.5.dp)
                        .height(40.dp)
                        .background(MidasColors.White.copy(alpha = 0.12f))
                        .align(Alignment.CenterVertically),
                )
                HeroStat(
                    modifier = Modifier.weight(1f),
                    label = "Investido",
                    value = "R$ ${"%.0f".format(uiState.totalInvested)}",
                    icon = Icons.AutoMirrored.Outlined.TrendingUp,
                    iconColor = MidasColors.Purple.light,
                )
            }
        }
    }
}

@Composable
private fun HeroStat(
    modifier: Modifier,
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
) {
    Column(modifier = modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(11.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MidasColors.White.copy(alpha = 0.55f),
                fontSize = 9.sp,
            )
        }
        Text(
            value,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MidasColors.White,
            modifier = Modifier.padding(top = 3.dp),
        )
    }
}

// ─── Monthly Bar Chart Card ───────────────────────────────────────────────────

@Composable
private fun MonthlyBarChartCard(
    data: List<MonthlyData>,
    selectedIndex: Int,
    onMonthSelected: (Int) -> Unit,
) {
    Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Column(modifier = Modifier.padding(bottom = 14.dp)) {
            // Month tab selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                data.forEachIndexed { i, month ->
                    val isSelected = i == selectedIndex
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) MidasColors.Green.primary.copy(alpha = 0.15f) else Color.Transparent)
                            .clickable { onMonthSelected(i) }
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    ) {
                        Text(
                            month.month,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MidasColors.Green.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            // Custom bar chart drawn with Canvas
            val maxVal = data.maxOf { maxOf(it.income, it.expense) } * 1.15f
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 16.dp),
            ) {
                val barGroupWidth = size.width / data.size
                val barWidth = barGroupWidth * 0.28f
                val gap = barGroupWidth * 0.06f
                val chartH = size.height - 24.dp.toPx()

                // Grid lines
                repeat(4) { i ->
                    val y = chartH * (1f - (i + 1) / 4f)
                    drawLine(
                        color = Color(0xFF303033),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 0.5.dp.toPx(),
                    )
                }

                data.forEachIndexed { i, month ->
                    val cx = barGroupWidth * i + barGroupWidth / 2f
                    val incH = (month.income / maxVal) * chartH
                    val expH = (month.expense / maxVal) * chartH
                    val isSelected = i == selectedIndex

                    // Income bar
                    drawRoundRect(
                        color = if (isSelected) Color(0xFF12B880) else Color(0xFF12B880).copy(alpha = 0.45f),
                        topLeft = Offset(cx - barWidth - gap / 2, chartH - incH),
                        size = Size(barWidth, incH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
                    )
                    // Expense bar
                    drawRoundRect(
                        color = if (isSelected) Color(0xFFF53D3D) else Color(0xFFF53D3D).copy(alpha = 0.45f),
                        topLeft = Offset(cx + gap / 2, chartH - expH),
                        size = Size(barWidth, expH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
                    )
                }
            }

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                LegendItem(color = MidasColors.Green.primary, label = "Entradas")
                Spacer(modifier = Modifier.width(20.dp))
                LegendItem(color = MidasColors.Red.primary, label = "Saídas")
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(modifier = Modifier
            .size(10.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// ─── Category Donut Card ─────────────────────────────────────────────────────

@Composable
private fun CategoryDonutCard(categories: List<CategoryData>, totalExpense: Double) {
    Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Custom donut chart with Canvas
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(110.dp)) {
                DonutChart(categories = categories)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "R$ ${"%.0f".format(totalExpense)}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        "total",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            // Legend
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                categories.forEach { cat ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Box(modifier = Modifier
                                .size(9.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(cat.color))
                            Text(
                                cat.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Text(
                            "${"%.0f".format(cat.percentage)}%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DonutChart(categories: List<CategoryData>) {
    val surfaceColor = MaterialTheme.colorScheme.surfaceContainer
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 18.dp.toPx()
        val radius = (min(size.width, size.height) / 2f) - strokeWidth / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        var startAngle = -90f
        val total = categories.sumOf { it.percentage.toDouble() }.toFloat()
        val gapAngle = 2f

        categories.forEach { cat ->
            val sweepAngle = (cat.percentage / total) * (360f - gapAngle * categories.size)
            drawArc(
                color = cat.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
            startAngle += sweepAngle + gapAngle
        }
    }
}

// ─── Goal Row Compact ─────────────────────────────────────────────────────────

@Composable
private fun GoalRowCompact(goal: GoalSummary) {
    val progress = (goal.current / goal.target).coerceIn(0.0, 1.0).toFloat()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(goal.iconColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Outlined.Flag, null, tint = MidasColors.White, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    goal.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    "${"%.0f".format(progress * 100)}%",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MidasColors.Green.primary,
                )
            }
            Text(
                "R$ ${"%.0f".format(goal.current)} de R$ ${"%.0f".format(goal.target)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(50)),
                color = goal.iconColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Composable
private fun TransactionRow(
    item: TransactionItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(item.color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (item.isExpense) Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward,
                contentDescription = null,
                tint = item.color,
                modifier = Modifier.size(18.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = item.type,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
                )
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            val prefix = if (item.isExpense) "- " else "+ "
            Text(
                text = "$prefix R$ ${"%.2f".format(item.amount)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = if (item.isExpense) MidasColors.Red.primary else MidasColors.Green.primary,
            )
            Text(
                text = item.status,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ─── Shared section header ────────────────────────────────────────────────────

@Composable
private fun SectionHeader(title: String, actionLabel: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            actionLabel,
            style = MaterialTheme.typography.labelSmall,
            color = MidasColors.Green.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable(onClick = onAction),
        )
    }
}

@CustomPreview
@Composable
private fun HomeV2Preview() {
    val navController = rememberNavController()
    val paddingValues = PaddingValues()
    HomeScreenV2(
        navController = navController,
        paddingValues = paddingValues
    )
}
