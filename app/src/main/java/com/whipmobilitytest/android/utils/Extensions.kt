package com.whipmobilitytest.android.utils

/**
 * Separate number every three digits
 */
fun Number.separate() = "%,d".format(this)