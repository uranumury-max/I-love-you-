package com.ury.alameenaprivate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
            android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        )

        setContent {
            OurPrivateWorld()
        }
    }
}

@Composable
fun OurPrivateWorld() {
    var screen by remember { mutableStateOf("home") }
    var firstName by remember { mutableStateOf("أنت") }
    var secondName by remember { mutableStateOf("شخصك المميز") }
    var relationshipDate by remember { mutableStateOf(LocalDateTime.now()) }

    BackHandler(screen != "home") {
        screen = "home"
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFFFF8FB1),
            background = Color(0xFF090D1A),
            surface = Color(0xFF151B2E)
        )
    ) {
        AnimatedContent(
            targetState = screen,
            label = "screen_transition"
        ) { currentScreen ->

            when (currentScreen) {

                "home" -> HomeScreen(
                    firstName = firstName,
                    secondName = secondName,
                    onOpen = { screen = it }
                )

                "settings" -> SettingsScreen(
                    firstName = firstName,
                    secondName = secondName,
                    onFirstNameChange = { firstName = it },
                    onSecondNameChange = { secondName = it },
                    onBack = { screen = "home" }
                )

                "stars" -> StarsScreen(
                    firstName = firstName,
                    secondName = secondName
                )

                "memories" -> MemoriesScreen()

                "garden" -> GardenScreen(
                    firstName = firstName,
                    secondName = secondName
                )

                "questions" -> QuestionsScreen()

                "counter" -> CounterScreen(
                    relationshipDate = relationshipDate
                )

                "capsule" -> CapsuleScreen()

                else -> HomeScreen(
                    firstName = firstName,
                    secondName = secondName,
                    onOpen = { screen = it }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    firstName: String,
    secondName: String,
    onOpen: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF17152D),
                        Color(0xFF090D1A),
                        Color(0xFF24152D)
                    )
                )
            )
    ) {
        StarBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "عالمنا الخاص",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "$firstName  ♥  $secondName",
                color = Color(0xFFFFB6D0),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(35.dp))

            FeatureButton("✨ سماؤنا المشتركة") {
                onOpen("stars")
            }

            FeatureButton("🌷 حديقتنا") {
                onOpen("garden")
            }

            FeatureButton("💌 ذكرياتنا") {
                onOpen("memories")
            }

            FeatureButton("❓ سؤال اليوم") {
                onOpen("questions")
            }

            FeatureButton("⏳ عدّاد علاقتنا") {
                onOpen("counter")
            }

            FeatureButton("🕰️ كبسولة المستقبل") {
                onOpen("capsule")
            }

            FeatureButton("⚙️ إعداداتنا") {
                onOpen("settings")
            }
        }
    }
}

@Composable
fun FeatureButton(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(
                Color(0xFF25213D),
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(17.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 17.sp
        )
    }
}

@Composable
fun StarBackground() {
    val infiniteTransition = rememberInfiniteTransition(
        label = "stars"
    )

    val movement by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 20000,
                easing = LinearEasing
            )
        ),
        label = "star_movement"
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        repeat(100) { index ->

            val x = (index * 97f + movement) % size.width
            val y = (index * 173f) % size.height

            drawCircle(
                color = Color.White.copy(
                    alpha = 0.25f + (index % 5) * 0.12f
                ),
                radius = (index % 3 + 1).toFloat(),
                center = Offset(x, y)
            )
        }
    }
}

@Composable
fun SettingsScreen(
    firstName: String,
    secondName: String,
    onFirstNameChange: (String) -> Unit,
    onSecondNameChange: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090D1A))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "إعدادات عالمنا",
            color = Color.White,
            fontSize = 26.sp
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("اسمك") },
            singleLine = true
        )

        Spacer(Modifier.height(15.dp))

        OutlinedTextField(
            value = secondName,
            onValueChange = onSecondNameChange,
            label = { Text("اسم الشخص المميز") },
            singleLine = true
        )

        Spacer(Modifier.height(30.dp))

        FeatureButton("حفظ والعودة") {
            onBack()
        }
    }
}

@Composable
fun StarsScreen(
    firstName: String,
    secondName: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050817)),
        contentAlignment = Alignment.Center
    ) {
        StarBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "سماء $firstName و $secondName",
                color = Color.White,
                fontSize = 23.sp
            )

            Spacer(Modifier.height(30.dp))

            Canvas(
                modifier = Modifier.size(250.dp)
            ) {
                val center = Offset(
                    size.width / 2,
                    size.height / 2
                )

                drawCircle(
                    color = Color(0xFFFFB6D0),
                    radius = 15f,
                    center = center
                )

                drawCircle(
                    color = Color(0xFF9B8CFF),
                    radius = 90f,
                    center = center,
                    style = Stroke(width = 2f)
                )

                drawCircle(
                    color = Color.White,
                    radius = 8f,
                    center = Offset(
                        center.x + 90f,
                        center.y
                    )
                )

                drawCircle(
                    color = Color(0xFFFFB6D0),
                    radius = 8f,
                    center = Offset(
                        center.x - 90f,
                        center.y
                    )
                )
            }
        }
    }
}

@Composable
fun MemoriesScreen() {
    var memory by remember { mutableStateOf("") }
    var memories by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101426))
            .padding(24.dp)
    ) {

        Text(
            "ذكرياتنا",
            color = Color.White,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = memory,
            onValueChange = { memory = it },
            label = { Text("اكتب ذكرى جميلة") },
            modifier = Modifier.fillMaxWidth()
        )

        FeatureButton("إضافة الذكرى") {
            if (memory.isNotBlank()) {
                memories = memories + memory
                memory = ""
            }
        }

        memories.forEach {
            Text(
                text = "♥ $it",
                color = Color(0xFFFFB6D0),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun GardenScreen(
    firstName: String,
    secondName: String
) {
    var flowers by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF10251D))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "حديقتنا",
            color = Color.White,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "🌱",
            fontSize = 55.sp
        )

        Text(
            "$firstName ♥ $secondName",
            color = Color(0xFFFFB6D0)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "عدد الزهور: $flowers",
            color = Color.White
        )

        FeatureButton("ازرعوا زهرة جديدة 🌷") {
            flowers++
        }
    }
}

@Composable
fun QuestionsScreen() {
    val questions = listOf(
        "ما أجمل ذكرى بيننا؟",
        "ما الشيء الذي يجعلك تشعر بالراحة؟",
        "ما حلم تتمنى أن نحققه معًا؟",
        "ما أكثر شيء تحبه في لحظاتنا؟",
        "ما المكان الذي تتمنى زيارته معي؟"
    )

    var questionIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF17152D))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "سؤالنا اليوم",
            color = Color.White,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(30.dp))

        Text(
            questions[questionIndex],
            color = Color(0xFFFFB6D0),
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(30.dp))

        FeatureButton("سؤال آخر") {
            questionIndex =
                (questionIndex + 1) % questions.size
        }
    }
}

@Composable
fun CounterScreen(
    relationshipDate: LocalDateTime
) {
    var seconds by remember {
        mutableLongStateOf(0L)
    }

    LaunchedEffect(Unit) {
        while (true) {
            seconds++
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101426))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "كل لحظة تجمعنا",
            color = Color.White,
            fontSize = 27.sp
        )

        Spacer(Modifier.height(25.dp))

        Text(
            "$seconds",
            color = Color(0xFFFFB6D0),
            fontSize = 45.sp
        )

        Text(
            "ثانية منذ فتح هذه الشاشة",
            color = Color.White
        )
    }
}

@Composable
fun CapsuleScreen() {
    var message by remember { mutableStateOf("") }
    var savedMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101426))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "كبسولة المستقبل",
            color = Color.White,
            fontSize = 27.sp
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("رسالة إلى مستقبلنا") },
            modifier = Modifier.fillMaxWidth()
        )

        FeatureButton("إغلاق الكبسولة") {
            savedMessage = message
        }

        if (savedMessage.isNotEmpty()) {
            Text(
                "تم حفظ رسالتك في هذه الجلسة 💌",
                color = Color(0xFFFFB6D0)
            )
        }
    }
}
