package org.openfocus.reelguard
object ReelDetector {
 fun matches(pkg:String,labels:Collection<String>,viewIds:Collection<String> = emptyList()):Boolean {
  val texts=labels.map{it.lowercase().trim()};val ids=viewIds.map{it.lowercase()}
  return when(pkg) {
   GuardSettings.INSTAGRAM -> texts.any{it=="reels"||it=="reel"||it.startsWith("reels tab")} && (ids.any{it.contains("clips")||it.contains("reel")}||texts.any{it.contains("reels video")||it.contains("reel by")||it.contains("reels viewer")})
   GuardSettings.YOUTUBE -> texts.any{it=="shorts"||it.startsWith("shorts player")||it.startsWith("shorts video")} && (ids.any{it.contains("reel")||it.contains("shorts")}||texts.any{it.startsWith("shorts player")||it.startsWith("shorts video")})
   GuardSettings.FACEBOOK -> texts.any{it=="reels"||it.startsWith("reels video")} && (ids.any{it.contains("reel")}||texts.any{it.startsWith("reels video")})
   else -> false
  }
 }
}
