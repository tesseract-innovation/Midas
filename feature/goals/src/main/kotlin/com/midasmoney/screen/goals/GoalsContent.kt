package com.midasmoney.screen.goals

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.usercase.GetGoalsSummaryUseCase
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.screen.goals.card.GoalCard
import com.midasmoney.screen.goals.card.GoalCardBalanceStatus
import com.midasmoney.screen.goals.card.GoalCardCompleted

@Composable
fun GoalsContentImp(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    val viewModel: GoalsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GoalsContent(
        navController = navController,
        paddingValues = paddingValues,
        uiState = uiState,
        viewModel = viewModel,
    )
}

@Composable
fun GoalsContent(
    navController: NavController,
    paddingValues: PaddingValues,
    uiState: GoalsUiState,
    viewModel: GoalsViewModel,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    Surface(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                is GoalsUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is GoalsUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.error_load_goals),
                                style = MaterialTheme.typography.titleMedium,
                                color = MidasColors.Red.primary,
                            )
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MidasColors.Gray,
                            )
                        }
                    }
                }

                is GoalsUiState.Success -> {
                    val activeGoals = uiState.goals.filter { it.progress < it.amount }
                    val completedGoals = uiState.goals.filter { it.progress >= it.amount }

                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 80.dp),
                    ) {
                        GoalCardBalanceStatus(summary = uiState.summary)

                        if (uiState.goals.isEmpty()) {
                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(R.string.no_goals),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MidasColors.Gray,
                                )
                            }
                        } else {
                            if (activeGoals.isNotEmpty()) {
                                TitleItem(
                                    textTitle = stringResource(R.string.goal_active),
                                    textButton = "",
                                    actionButton = {},
                                )
                                activeGoals.forEach { goal ->
                                    GoalCardWithActions(
                                        goal = goal,
                                        isDarkTheme = isDarkTheme,
                                        onCardClick = {
                                            navController.navigate(GoalsRoute.GoalDetail(goal))
                                        },
                                        onEdit = {
                                            navController.navigate(GoalsRoute.GoalForm(goal))
                                        },
                                        onDelete = { viewModel.deleteGoal(goal) },
                                    )
                                }
                            }

                            if (completedGoals.isNotEmpty()) {
                                TitleItem(
                                    textTitle = stringResource(R.string.goal_recently_completed),
                                    textButton = "",
                                    actionButton = {},
                                )
                                completedGoals.forEach { goal ->
                                    GoalCardCompleted(goal = goal)
                                }
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate(GoalsRoute.GoalForm()) },
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                containerColor = MidasColors.Blue.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.description_add_goal),
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun GoalCardWithActions(
    goal: Goal,
    isDarkTheme: Boolean,
    onCardClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    Box {
        GoalCard(
            goal = goal,
            isDarkTheme = isDarkTheme,
            onCardClick = onCardClick,
            onMenuClick = { showMenu = true },
            onAddMoneyClick = onCardClick,
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.edit)) },
                onClick = {
                    showMenu = false
                    onEdit()
                },
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete), color = MidasColors.Red.primary) },
                onClick = {
                    showMenu = false
                    onDelete()
                },
            )
        }
    }
}

@CustomPreview
@Composable
fun GoalsContentPreview() {
    MidasTheme {
        val goals = Database.goalList
        GoalsContent(
            navController = androidx.navigation.compose.rememberNavController(),
            paddingValues = PaddingValues(),
            uiState =
                GoalsUiState.Success(
                    goals = goals,
                    summary = GetGoalsSummaryUseCase()(goals),
                ),
            viewModel = hiltViewModel(),
            isDarkTheme = true,
        )
    }
}
