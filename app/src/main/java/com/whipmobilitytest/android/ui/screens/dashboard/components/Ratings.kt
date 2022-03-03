package com.whipmobilitytest.android.ui.screens.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
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
import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.ui.components.HorizontalSpacer
import com.whipmobilitytest.android.utils.separate
import com.intuit.sdp.R as DP
import com.intuit.ssp.R as SP


@Composable
fun Ratings(
  title: String?,
  description: String?,
  average: Float?,
  data: DashboardStatisticsData.Analytics.Rating.Items?,
  isDetailsVisible: Boolean,
  onDetailsToggle: () -> Unit,
) {
  Column(Modifier.fillMaxWidth()) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Column(Modifier.weight(1f)) {
        // section title
        if (!title.isNullOrEmpty()) Text(
          text = title,
          modifier = Modifier
            .padding(horizontal = dimensionResource(id = DP.dimen._8sdp)),
          color = MaterialTheme.colors.onSurface,
          fontSize = dimensionResource(id = SP.dimen._14ssp).value.sp,
          fontWeight = FontWeight.ExtraBold
        )
        // section description
        if (!description.isNullOrEmpty()) Text(
          text = description,
          modifier = Modifier.padding(horizontal = dimensionResource(id = DP.dimen._8sdp)),
          color = MaterialTheme.colors.secondary,
          fontSize = dimensionResource(id = SP.dimen._12ssp).value.sp,
          fontWeight = FontWeight.Light
        )
      }
      // average rating
      if (average != null) {
        Column(
          modifier = Modifier
            .padding(horizontal = dimensionResource(id = DP.dimen._8sdp))
            .border(
              border = BorderStroke(
                width = dimensionResource(id = DP.dimen._1sdp),
                color = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
              ),
              shape = RoundedCornerShape(dimensionResource(id = DP.dimen._4sdp))
            )
            .clip(RoundedCornerShape(dimensionResource(id = DP.dimen._4sdp)))
            .clickable { onDetailsToggle() }
            .padding(dimensionResource(id = DP.dimen._6sdp)),
          horizontalAlignment = Alignment.CenterHorizontally) {
          // average value
          Text(
            average.toString(),
            color = MaterialTheme.colors.primary,
            fontSize = dimensionResource(id = SP.dimen._24ssp).value.sp,
            fontWeight = FontWeight.Bold
          )
          // number of votes if is greater than zero
          if (data?.numberOfVotes() ?: 0 > 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                modifier = Modifier.size(dimensionResource(id = DP.dimen._15sdp)),
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                tint = MaterialTheme.colors.secondary.copy(alpha = 0.7f)
              )
              HorizontalSpacer(space = DP.dimen._1sdp)
              Text(
                data?.numberOfVotes()!!.separate(),
                color = MaterialTheme.colors.secondary,
                fontSize = dimensionResource(id = SP.dimen._10ssp).value.sp,
                fontWeight = FontWeight.Light
              )
            }
          }
        }
      }
    }
    // rating details
    if (data?.toPairList()?.isNotEmpty() == true) AnimatedVisibility(visible = isDetailsVisible) {
      Column(
        Modifier
          .fillMaxWidth()
          .padding(dimensionResource(id = DP.dimen._8sdp))
      ) {
        data.toPairList().forEach {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = dimensionResource(id = DP.dimen._8sdp)),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // rate value
            Text(
              text = it.first,
              modifier = Modifier.padding(end = dimensionResource(id = DP.dimen._8sdp)),
              color = MaterialTheme.colors.secondary,
              fontSize = dimensionResource(id = SP.dimen._13ssp).value.sp,
              fontWeight = FontWeight.Light
            )
            // rate count progress
            LinearProgressIndicator(
              (it.second ?: 0).toFloat().div(data.numberOfVotes()),
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(dimensionResource(id = DP.dimen._5sdp)))
                .height(dimensionResource(id = DP.dimen._7sdp)),
              color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
              backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.3f),
            )
          }
        }
      }
    }
  }
}