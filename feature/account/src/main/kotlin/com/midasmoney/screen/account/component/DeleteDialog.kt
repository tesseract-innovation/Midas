package com.midasmoney.screen.account.component

import android.graphics.Color
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.preview.MidasDarkPreview
import com.midasmoney.core.ui.preview.MidasLightPreview

@Composable
fun DeleteDialog(
    titleItem: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.title_dialog_delete, titleItem),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = stringResource(R.string.description_dialog_delete_account, titleItem)
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MidasColors.Red.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.delete)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun DeleteAccountDialogLightPreview() {
    MidasLightPreview {
        DeleteDialog(
            titleItem = "Test Account",
            onConfirm = { },
            onDismiss = { }
        )
    }
}

@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun DeleteAccountDialogDarkPreview() {
    MidasDarkPreview {
        DeleteDialog(
            titleItem = "Transaction",
            onConfirm = { },
            onDismiss = { }
        )
    }
}
