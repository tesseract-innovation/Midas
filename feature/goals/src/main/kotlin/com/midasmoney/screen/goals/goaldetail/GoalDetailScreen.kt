package com.midasmoney.screen.goals.goaldetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.GoalContribution
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.screen.goals.GoalsRoute
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailScreen(
    args: GoalsRoute.GoalDetail,
    navController: NavController,
    viewModel: GoalDetailViewModel = hiltViewModel(),
) {
    val observedGoal by viewModel.goal.collectAsStateWithLifecycle()
    val goal = observedGoal ?: args.goal
    val state by viewModel.state.collectAsStateWithLifecycle()
    val contributions by viewModel.contributions.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showContributeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(goal.id) {
        viewModel.loadContributions(goal.id.toString())
    }

    LaunchedEffect(state) {
        when (state) {
            is GoalDetailState.ContributeSuccess -> {
                snackbarHostState.showSnackbar("Contribution added!")
                viewModel.resetState()
            }
            is GoalDetailState.Error -> {
                snackbarHostState.showSnackbar((state as GoalDetailState.Error).message)
                viewModel.resetState()
            }
            else -> {}
        }
    }

    if (showContributeDialog) {
        GoalContributeDialog(
            goalTitle = goal.title,
            onConfirm = { amount ->
                showContributeDialog = false
                viewModel.contribute(goal, amount)
            },
            onDismiss = { showContributeDialog = false },
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            GoalDetailTopBar(
                title = goal.title,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(GoalsRoute.GoalForm(goal)) },
            )
        },
        floatingActionButton = {
            ContributeFab(onClick = { showContributeDialog = true })
        },
    ) { padding ->
        GoalDetailContent(
            goal = goal,
            contributions = contributions,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoalDetailTopBar(
    title: String,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )
            }
        },
        actions = {
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.description_edit_goal),
                )
            }
        },
    )
}

@Composable
private fun ContributeFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = MidasColors.Blue.primary,
        contentColor = Color.White,
    ) {
        Text(stringResource(R.string.add_money))
    }
}

@Composable
private fun GoalDetailContent(
    goal: Goal,
    contributions: List<GoalContribution>,
    modifier: Modifier = Modifier,
) {
    val icon = IconConverter.getImageVector(goal.icon)
    val color = ColorConverter.aRgbToColor(goal.color)
    val progress = (goal.progress / goal.amount).coerceIn(0.0, 1.0).toFloat()

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GoalHeaderCard(goal = goal, accentColor = color, icon = icon)
        GoalMonthlyContributionCard(goal = goal, accentColor = color)
        GoalProgressCard(goal = goal, progress = progress, accentColor = color)
        GoalContributionsCard(contributions = contributions, accentColor = color)
    }
}

@Composable
private fun GoalHeaderCard(
    goal: Goal,
    accentColor: Color,
    icon: ImageVector,
) {
    MidasCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = goal.description,
                    tint = accentColor,
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(accentColor.copy(alpha = 0.2f))
                            .padding(12.dp),
                )
                Column {
                    Text(
                        text = goal.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    if (goal.description.isNotBlank()) {
                        Text(
                            text = goal.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MidasColors.Gray,
                        )
                    }
                }
            }
            Text(
                text = "${stringResource(R.string.target)}: ${goal.targetDate.format(TargetDateFormat)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MidasColors.Gray,
            )
        }
    }
}

@Composable
private fun GoalProgressCard(
    goal: Goal,
    progress: Float,
    accentColor: Color,
) {
    MidasCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = stringResource(R.string.progress),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = goal.progress.toCurrency(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MidasColors.Gray,
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                )
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape),
                color = accentColor,
                trackColor = MidasColors.ExtraLightGray,
            )
            Text(
                text = "${stringResource(R.string.target)}: ${goal.amount.toCurrency()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MidasColors.Gray,
            )
        }
    }
}

@Composable
private fun GoalMonthlyContributionCard(
    goal: Goal,
    accentColor: Color,
) {
    MidasCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.monthly_target),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = goal.monthlyValue.toCurrency(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = accentColor,
            )
        }
    }
}

@Composable
private fun GoalContributionsCard(
    contributions: List<GoalContribution>,
    accentColor: Color,
) {
    MidasCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.contribution_history),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            if (contributions.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_contributions),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MidasColors.Gray,
                )
            } else {
                contributions.forEach { contribution ->
                    ContributionRow(contribution = contribution, accentColor = accentColor)
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun ContributionRow(
    contribution: GoalContribution,
    accentColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = contribution.date.formatAsContributionDate(),
            style = MaterialTheme.typography.bodyMedium,
            color = MidasColors.Gray,
        )
        Text(
            text = contribution.amount.toCurrency(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = accentColor,
        )
    }
}

@OptIn(ExperimentalTime::class)
private fun Instant.formatAsContributionDate(): String =
    toLocalDateTime(TimeZone.currentSystemDefault()).date.format(TargetDateFormat)

private val TargetDateFormat =
    LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        day()
        chars(", ")
        year()
    }

@OptIn(ExperimentalTime::class)
@CustomPreview
@Composable
private fun GoalDetailContentPreview() {
    val goal = Database.goalList.first()
    val now = Clock.System.now()
    val sampleContributions =
        listOf(
            GoalContribution(goalId = goal.id, amount = 250.0, date = now),
            GoalContribution(goalId = goal.id, amount = 100.0, date = now),
            GoalContribution(goalId = goal.id, amount = 75.5, date = now),
        )
    MidasTheme {
        GoalDetailContent(
            goal = goal,
            contributions = sampleContributions,
        )
    }
}

@OptIn(ExperimentalTime::class)
@CustomPreview
@Composable
private fun GoalContributionsCardEmptyPreview() {
    MidasTheme {
        GoalContributionsCard(
            contributions = emptyList(),
            accentColor = MidasColors.Blue.primary,
        )
    }
}
