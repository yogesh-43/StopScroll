package org.openfocus.reelguard
import org.junit.Assert.*
import org.junit.Test
class ReelDetectorTest{
 @Test fun detectsYouTubeShorts(){assertTrue(ReelDetector.matches(GuardSettings.YOUTUBE,listOf("Shorts player")))}
 @Test fun ignoresNormalVideo(){assertFalse(ReelDetector.matches(GuardSettings.YOUTUBE,listOf("Home","Subscriptions")))}
}
