package com.whipmobilitytest.android.ui.screens.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.whipmobilitytest.android.ui.components.VerticalSpacer
import com.intuit.sdp.R as DP
import com.intuit.ssp.R as SP

@Composable
fun SectionHeader(modifier: Modifier = Modifier, title: String?, description: String?) {
  Column(
    Modifier
      .fillMaxWidth()
      .then(modifier)
  ) {
    // section title
    if (!title.isNullOrEmpty()) Text(
      text = title,
      modifier = Modifier
        .padding(horizontal = dimensionResource(id = DP.dimen._8sdp)),
      color = Color.Black,
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
}

@Composable
fun SectionDivider() {
  VerticalSpacer(space = DP.dimen._10sdp)
}