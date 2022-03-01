package com.whipmobilitytest.android.ui.components

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

@Composable
fun VerticalSpacer(@DimenRes space: Int) {
  Spacer(modifier = Modifier.height(dimensionResource(id = space)))
}