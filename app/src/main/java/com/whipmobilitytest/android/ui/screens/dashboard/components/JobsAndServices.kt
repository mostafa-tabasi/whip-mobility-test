package com.whipmobilitytest.android.ui.screens.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.intuit.sdp.R
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.ui.components.HorizontalSpacer
import com.whipmobilitytest.android.ui.theme.Green600
import com.whipmobilitytest.android.ui.theme.Red600
import com.intuit.ssp.R as SP

@Composable
fun JobsAndServices(
  headerTitle: String?,
  headerDescription: String?,
  data: List<DashboardStatisticsData.Analytics.JobAndServiceItem>
) {
  // section header
  SectionHeader(title = headerTitle, description = headerDescription)
  // section items
  LazyRow(Modifier.fillMaxWidth()) {
    items(data) {
      Column(
        Modifier
          .padding(dimensionResource(id = R.dimen._8sdp))
          .border(
            border = BorderStroke(
              width = dimensionResource(id = R.dimen._1sdp),
              color = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen._8sdp))
          )
          .clip(RoundedCornerShape(dimensionResource(id = R.dimen._8sdp)))
          .padding(
            horizontal = dimensionResource(id = R.dimen._12sdp),
            vertical = dimensionResource(id = R.dimen._8sdp)
          )
      ) {
        Row(verticalAlignment = Alignment.Bottom) {
          // item value
          Text(
            text = "${it.avg ?: it.total ?: ""}",
            modifier = Modifier.alignByBaseline(),
            color = MaterialTheme.colors.onBackground,
            fontSize = dimensionResource(id = SP.dimen._25ssp).value.sp,
            fontWeight = FontWeight.ExtraBold
          )
          if (it.growth != null) {
            HorizontalSpacer(space = R.dimen._4sdp)
            Row(modifier = Modifier.alignByBaseline()) {
              if (it.growth != 0) {
                // job item growth icon
                Icon(
                  painter = painterResource(
                    id = if (it.growth!! > 0) com.whipmobilitytest.android.R.drawable.ic_growth_up
                    else com.whipmobilitytest.android.R.drawable.ic_growth_down
                  ),
                  contentDescription = null,
                  tint = if (it.growth!! > 0) Green600 else Red600
                )
                HorizontalSpacer(space = R.dimen._2sdp)
              }
              // item growth value
              Text(
                text = it.growth.toString(),
                color = when {
                  it.growth!! > 0 -> Green600
                  it.growth!! < 0 -> Red600
                  else -> MaterialTheme.colors.onBackground
                },
                fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
        // item title
        Text(
          text = it.title ?: "",
          color = MaterialTheme.colors.onBackground,
          fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp,
          fontWeight = FontWeight.Bold
        )
        // item description
        Text(
          text = it.description ?: "",
          color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
          fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._10ssp).value.sp,
          fontWeight = FontWeight.Light
        )
      }
    }
  }
}