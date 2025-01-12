package org.gdglille.devfest.android.ui.m2.components.partners

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.ui.m2.theme.Conferences4HallTheme

@Composable
fun PartnerDivider(
    title: String,
    modifier: Modifier = Modifier,
    dividerColor: Color = MaterialTheme.colors.primary,
    titleColor: Color = MaterialTheme.colors.secondary,
    style: TextStyle = MaterialTheme.typography.subtitle1
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Divider(color = dividerColor, thickness = 1.dp, modifier = Modifier.weight(1f))
            Text(text = title, style = style, color = titleColor)
            Divider(color = dividerColor, thickness = 1.dp, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun PartnerDividerPreview() {
    Conferences4HallTheme {
        PartnerDivider(title = "Gold")
    }
}
