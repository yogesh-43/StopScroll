package org.openfocus.reelguard
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
class BlockedActivity:ComponentActivity(){
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{MaterialTheme{BlockedScreen{
 startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));finish()
 }}}}
}
@Composable fun BlockedScreen(onHome:()->Unit){
 Column(Modifier.fillMaxSize().padding(32.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){
 Text("Short-video feed blocked",style=MaterialTheme.typography.headlineMedium,textAlign=TextAlign.Center)
 Spacer(Modifier.height(20.dp));Text("ReelGuard detected a Reels or Shorts feed. You can still use other parts of the app.",textAlign=TextAlign.Center)
 Spacer(Modifier.height(28.dp));Button(onClick=onHome){Text("Go Home")}
 }
}
