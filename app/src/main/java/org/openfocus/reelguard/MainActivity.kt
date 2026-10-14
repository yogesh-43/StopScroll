package org.openfocus.reelguard
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
class MainActivity:ComponentActivity(){
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{
 MaterialTheme{
 var guard by remember{mutableStateOf(GuardSettings.guardOn(this))}
 var instagram by remember{mutableStateOf(GuardSettings.enabled(this,GuardSettings.INSTAGRAM))}
 var youtube by remember{mutableStateOf(GuardSettings.enabled(this,GuardSettings.YOUTUBE))}
 var facebook by remember{mutableStateOf(GuardSettings.enabled(this,GuardSettings.FACEBOOK))}
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
 Text("ReelGuard",style=MaterialTheme.typography.headlineLarge)
 Text("Reduce short-video scrolling. Local-only, no account required.")
 ToggleRow("Enable ReelGuard",guard){guard=it;GuardSettings.setGuardOn(this@MainActivity,it)}
 HorizontalDivider()
 ToggleRow("Instagram Reels",instagram){instagram=it;GuardSettings.setEnabled(this@MainActivity,GuardSettings.INSTAGRAM,it)}
 ToggleRow("YouTube Shorts",youtube){youtube=it;GuardSettings.setEnabled(this@MainActivity,GuardSettings.YOUTUBE,it)}
 ToggleRow("Facebook Reels",facebook){facebook=it;GuardSettings.setEnabled(this@MainActivity,GuardSettings.FACEBOOK,it)}
 HorizontalDivider()
 Text("Blocked feed detections: ${GuardSettings.blocks(this@MainActivity)}")
 Text("Enable ReelGuard in Android Accessibility settings. Detection uses visible labels locally.")
 Button(onClick={startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))}){Text("Open Accessibility Settings")}
 Text("Detection is best-effort and may miss feeds or misidentify tabs.",style=MaterialTheme.typography.bodySmall)
 }
 }
 }}
}
@Composable private fun ToggleRow(title:String,checked:Boolean,onChange:(Boolean)->Unit){
 Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text(title,modifier=Modifier.weight(1f));Switch(checked=checked,onCheckedChange=onChange)}
}
