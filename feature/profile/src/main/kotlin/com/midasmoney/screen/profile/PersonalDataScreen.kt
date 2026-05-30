package com.midasmoney.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.component.MidasButton
import com.midasmoney.core.ui.component.MidasSectionLabel
import com.midasmoney.core.ui.component.MidasTextField
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─── State ───────────────────────────────────────────────────────────────────

data class PersonalDataUiState(
    val name: String = "Dayvson Silva",
    val cpf: String = "123.456.789-00",
    val birthDate: String = "15/03/1995",
    val phone: String = "+55 (92) 99999-0000",
    val gender: String = "Masculino",
    val address: String = "Manaus, AM",
    val avatarInitials: String = "DS",
    val isSaving: Boolean = false,
)

// ─── Screen ──────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(
    uiState: PersonalDataUiState = PersonalDataUiState(),
    onBackClick: () -> Unit = {},
    onSaveClick: (PersonalDataUiState) -> Unit = {},
) {
    var name by remember { mutableStateOf(uiState.name) }
    var phone by remember { mutableStateOf(uiState.phone) }
    var birthDate by remember { mutableStateOf(uiState.birthDate) }
    var gender by remember { mutableStateOf(uiState.gender) }
    var address by remember { mutableStateOf(uiState.address) }

    val hasChanges =
        name != uiState.name ||
            phone != uiState.phone ||
            birthDate != uiState.birthDate ||
            gender != uiState.gender ||
            address != uiState.address

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Dados pessoais",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { padding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
            ) {
                // Avatar section
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier =
                                Modifier
                                    .size(88.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(MidasColors.Purple.primary, MidasColors.Purple.dark),
                                        ),
                                    )
                                    .border(2.dp, MidasColors.Green.primary.copy(alpha = 0.4f), CircleShape),
                        ) {
                            Text(
                                uiState.avatarInitials,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MidasColors.White,
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier =
                                Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(MidasColors.Green.primary)
                                    .border(2.dp, MaterialTheme.colorScheme.background, CircleShape),
                        ) {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = "Alterar foto",
                                tint = MidasColors.White,
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }
                    Text(
                        "Alterar foto",
                        style = MaterialTheme.typography.labelMedium,
                        color = MidasColors.Green.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                // Form
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    MidasSectionLabel("Informações básicas")
                    MidasTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nome completo",
                        leadingIcon = Icons.Outlined.Person,
                    )
                    MidasTextField(
                        value = uiState.cpf,
                        onValueChange = {},
                        label = "CPF",
                        leadingIcon = Icons.Outlined.Badge,
                        enabled = false,
                        helperText = "O CPF não pode ser alterado",
                    )
                    MidasTextField(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        label = "Data de nascimento",
                        leadingIcon = Icons.Outlined.Cake,
                        keyboardType = KeyboardType.Number,
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    MidasSectionLabel("Contato")
                    MidasTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Telefone",
                        leadingIcon = Icons.Outlined.Phone,
                        keyboardType = KeyboardType.Phone,
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    MidasSectionLabel("Outros")
                    MidasTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = "Gênero",
                        leadingIcon = Icons.Outlined.People,
                    )
                    MidasTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Cidade / Estado",
                        leadingIcon = Icons.Outlined.LocationOn,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MidasButton(
                        text = if (uiState.isSaving) "" else "Salvar alterações",
                        onClick = {
                            onSaveClick(
                                uiState.copy(
                                    name = name,
                                    phone = phone,
                                    birthDate = birthDate,
                                    gender = gender,
                                    address = address,
                                ),
                            )
                        },
                        containerColor =
                            if (hasChanges && !uiState.isSaving) {
                                MidasColors.Green.primary
                            } else {
                                MidasColors.Green.primary.copy(
                                    alpha = 0.3f,
                                )
                            },
                        contentColor =
                            if (hasChanges && !uiState.isSaving) {
                                MidasColors.White
                            } else {
                                MidasColors.White.copy(
                                    alpha = 0.5f,
                                )
                            },
                    )

                    if (uiState.isSaving) {
                        Box(
                            modifier = Modifier.fillMaxWidth().offset(y = (-35).dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MidasColors.White,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PersonalDataPreview() = PersonalDataScreen()
