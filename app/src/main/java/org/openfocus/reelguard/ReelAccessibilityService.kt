package org.openfocus.reelguard
import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
class ReelAccessibilityService:AccessibilityService(){
 private var lastBlock=0L; private var lastScan=0L
 override fun onAccessibilityEvent(event:AccessibilityEvent?){
  if(event==null||!GuardSettings.guardOn(this))return
  val pkg=event.packageName?.toString()?:return
  if(pkg !in GuardSettings.supported||!GuardSettings.enabled(this,pkg))return
  val now=SystemClock.elapsedRealtime()
  if(now-lastScan<350||now-lastBlock<2500)return
  lastScan=now
  val root=rootInActiveWindow?:return
  val labels=mutableListOf<String>();val ids=mutableListOf<String>()
  try{collect(root,labels,ids,0)}finally{root.recycle()}
  if(!ReelDetector.matches(pkg,labels,ids))return
  lastBlock=now;GuardSettings.increment(this);performGlobalAction(GLOBAL_ACTION_HOME)
  try{startActivity(Intent(this,BlockedActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))}catch(_:Exception){}
 }
 private fun collect(node:AccessibilityNodeInfo,labels:MutableList<String>,ids:MutableList<String>,depth:Int){
  if(depth>16||labels.size>350)return
  node.text?.toString()?.let(labels::add);node.contentDescription?.toString()?.let(labels::add);node.viewIdResourceName?.let(ids::add)
  for(i in 0 until node.childCount){val child=node.getChild(i)?:continue;try{collect(child,labels,ids,depth+1)}finally{child.recycle()}}
 }
 override fun onInterrupt(){}
}
