package com.example.sharedelement.ui

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


const val PHONE_SPEC = "spec:shape=Normal,width=360,height=840,unit=dp,dpi=480"
const val PHONE_LANDSCAPE_SPEC =
    "spec:shape=Normal,width=782,height=360,unit=dp,dpi=480" // for Player Composable Previews
const val PHONE_SCROLLABLE_SPEC = "spec:shape=Normal,width=360,height=1200,unit=dp,dpi=480"
const val FOLDABLE_SPEC = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"
const val LANDSCAPE_SPEC = "spec:shape=Normal,width=1280,height=980,unit=dp,dpi=480"
const val PORTRAIT_SPEC = "spec:shape=Normal,width=840,height=1024,unit=dp,dpi=480"

const val PIXEL_6 = "spec:shape=Normal,width=866,height=387,unit=dp,dpi=420"
const val PIXEL_7 = "spec:shape=Normal,width=724,height=322,unit=dp,dpi=500"


/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(name = "phone", device = Devices.FOLDABLE)
@Preview(name = "scrollable-phone", device = PHONE_SCROLLABLE_SPEC)
@Preview(name = "landscape", device = LANDSCAPE_SPEC)
@Preview(name = "foldable", device = FOLDABLE_SPEC)
@Preview(name = "tablet", device = PORTRAIT_SPEC)
annotation class DevicePreviews

private const val SCALED_DEVICE_PREVIEWS_FONT_SIZE = 2f

@Preview(name = "phone", device = PHONE_SPEC, fontScale = SCALED_DEVICE_PREVIEWS_FONT_SIZE)
@Preview(
    name = "scrollable-phone",
    device = PHONE_SCROLLABLE_SPEC,
    fontScale = SCALED_DEVICE_PREVIEWS_FONT_SIZE,
)
@Preview(name = "landscape", device = LANDSCAPE_SPEC, fontScale = SCALED_DEVICE_PREVIEWS_FONT_SIZE)
@Preview(name = "foldable", device = FOLDABLE_SPEC, fontScale = SCALED_DEVICE_PREVIEWS_FONT_SIZE)
@Preview(name = "tablet", device = PORTRAIT_SPEC, fontScale = SCALED_DEVICE_PREVIEWS_FONT_SIZE)
annotation class ScaledDevicePreviews

@Preview(name = "phone", device = PHONE_LANDSCAPE_SPEC)
@Preview(name = "landscape", device = LANDSCAPE_SPEC)
annotation class PlayerDevicePreviews

@Preview(name = "phone", device = PHONE_LANDSCAPE_SPEC, fontScale = 1.15f)
@Preview(name = "landscape", device = LANDSCAPE_SPEC, fontScale = 1.15f)
annotation class ScaledPlayerDevicePreviews

@Preview(name = "phone", device = PHONE_LANDSCAPE_SPEC, fontScale = 1.3f)
@Preview(name = "landscape", device = LANDSCAPE_SPEC, fontScale = 1.3f)
annotation class BiggestScaledPlayerDevicePreviews
