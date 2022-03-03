package com.whipmobilitytest.android.ui.screens.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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


@Composable
fun JobsAndServices(
  title: String?,
  description: String?,
  data: List<DashboardStatisticsData.Analytics.JobAndServiceItem>
) {
  Column(Modifier.fillMaxWidth()) {
    // section title
    if (!title.isNullOrEmpty()) Text(
      text = title,
      modifier = Modifier
        .padding(horizontal = dimensionResource(id = R.dimen._8sdp)),
      color = MaterialTheme.colors.onSurface,
      fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._14ssp).value.sp,
      fontWeight = FontWeight.ExtraBold
    )
    // section description
    if (!description.isNullOrEmpty()) Text(
      text = description,
      modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen._8sdp)),
      color = MaterialTheme.colors.secondary,
      fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp,
      fontWeight = FontWeight.Light
    )
  }
  // section items
  AnimatedVisibility(
    enter = expandVertically(),
    exit = shrinkVertically(),
    visible = true
  ) {
    LazyRow(Modifier.fillMaxWidth()) {
      items(data) {
        Column(
          Modifier
            .padding(dimensionResource(id = R.dimen._8sdp))
            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen._8sdp)))
            .background(
              color = when {
                it.growth ?: 0 > 0 -> Green600.copy(alpha = 0.1f)
                it.growth ?: 0 < 0 -> Red600.copy(alpha = 0.1f)
                else -> MaterialTheme.colors.secondary.copy(alpha = 0.1f)
              }
            )
            .padding(
              start = dimensionResource(id = R.dimen._16sdp),
              end = dimensionResource(id = R.dimen._16sdp),
              bottom = dimensionResource(id = R.dimen._16sdp)
            )
        ) {
          Row(verticalAlignment = Alignment.Bottom) {
            // item value
            Text(
              text = "${it.avg ?: it.total ?: ""}",
              modifier = Modifier.alignByBaseline(),
              color = MaterialTheme.colors.onSurface,
              style = MaterialTheme.typography.h2
            )
            if (it.growth != null) {
              HorizontalSpacer(space = R.dimen._4sdp)
              Row(modifier = Modifier.alignByBaseline()) {
                if (it.growth != 0) {
                  // job item growth icon
                  Icon(
                    painter = painterResource(id = if (it.growth!! > 0) com.whipmobilitytest.android.R.drawable.ic_growth_up else com.whipmobilitytest.android.R.drawable.ic_growth_down),
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
                    else -> MaterialTheme.colors.onSurface
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
            color = MaterialTheme.colors.onSurface,
            fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value.sp,
            fontWeight = FontWeight.Bold
          )
          // item description
          Text(
            text = it.description ?: "",
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp,
            fontWeight = FontWeight.Light
          )
        }
      }
    }
  }
}