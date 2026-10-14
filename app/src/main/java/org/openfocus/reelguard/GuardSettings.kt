package org.openfocus.reelguard
import android.content.Context
object GuardSettings {
 private const val PREFS="reelguard"
 const val INSTAGRAM="com.instagram.android"; const val YOUTUBE="com.google.android.youtube"; const val FACEBOOK="com.facebook.katana"
 val supported=listOf(INSTAGRAM,YOUTUBE,FACEBOOK)
 fun enabled(context:Context,pkg:String)=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE).getBoolean(pkg,true)
 fun setEnabled(context:Context,pkg:String,value:Boolean)=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE).edit().putBoolean(pkg,value).apply()
 fun guardOn(context:Context)=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE).getBoolean("guard_on",true)
 fun setGuardOn(context:Context,value:Boolean)=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE).edit().putBoolean("guard_on",value).apply()
 fun increment(context:Context){ val p=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE);p.edit().putInt("blocks",p.getInt("blocks",0)+1).apply() }
 fun blocks(context:Context)=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE).getInt("blocks",0)
}
