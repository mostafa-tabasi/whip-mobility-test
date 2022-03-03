package com.whipmobilitytest.android.utils

/**
 * Separate number every three digits
 */
fun Number.separated() = "%,d".format(this)