package org.gdglille.devfest.android.ui.m2.components.partners

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.ui.m2.theme.Conferences4HallTheme
import org.gdglille.devfest.models.PartnerGroupsUi
import org.gdglille.devfest.models.PartnerItemUi

@ExperimentalMaterialApi
@Composable
fun PartnerRow(
    partners: List<PartnerItemUi>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    maxItems: Int = 3,
    onPartnerClick: (siteUrl: String?) -> Unit,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val width = (this.maxWidth - (8.dp * (maxItems - 1))) / maxItems
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            partners.forEach {
                Conferences4HallTheme(isDarkMode = false) {
                    PartnerItem(
                        partnerUi = it,
                        modifier = Modifier.width(width).aspectRatio(1f),
                        isLoading = isLoading,
                        onClick = onPartnerClick
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PartnerRowPreview() {
    Conferences4HallTheme {
        PartnerRow(
            partners = PartnerGroupsUi.fake.silvers[0],
            onPartnerClick = {}
        )
    }
}
