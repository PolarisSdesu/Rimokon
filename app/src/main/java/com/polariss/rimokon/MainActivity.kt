package com.polariss.rimokon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polariss.rimokon.ui.theme.RimokonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RimokonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier
                            .padding(innerPadding)
                    )

                }
            }
        }
    }
}

val N57Sans = FontFamily(
    Font(R.font.n57, FontWeight.Normal)
)

val PixelSans = FontFamily(
    Font(R.font.pixel_cjk, FontWeight.Normal)
)

@Composable
fun App(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),

        ) {
        Text(
            text = "リモコン",
            modifier = Modifier
                .padding(top = 120.dp, start = 20.dp),
            fontFamily = PixelSans,
            fontSize = 32.sp,
        )
        IconView()
        ControlArea()
    }
}

@Composable
fun IconView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(260.dp)
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bulb),
            contentDescription = "一个像素的灯泡图标。",
            modifier = Modifier.size(44.dp),
            tint = Color.Black
        )
    }
}

@Composable
fun ControlArea(modifier: Modifier = Modifier) {
    // 定义3x3网格的按钮配置 (使用密封类区分空按钮和普通按钮)
    val buttonConfigs = listOf(
        ButtonConfig.Normal(
            iconRes = R.drawable.power,
            color = Color(0xFFFF2E5B),
            label = "ON/OFF"
        ),
        ButtonConfig.Normal(iconRes = R.drawable.up, color = Color.Black, label = "BRIGHTNESS +"),
        ButtonConfig.Normal(
            iconRes = R.drawable.night_mode,
            color = Color(0xFF2E31FF),
            label = "NIGHTMODE"
        ),
        ButtonConfig.Empty,
        ButtonConfig.Normal(iconRes = R.drawable.down, color = Color.Black, label = "BRIGHTNESS -"),
        ButtonConfig.Empty,
        ButtonConfig.Normal(iconRes = R.drawable.cold, color = Color.Black, label = "COLD"),
        ButtonConfig.Normal(
            iconRes = R.drawable.cold_warm,
            color = Color.Black,
            label = "COLD + WARM"
        ),
        ButtonConfig.Normal(iconRes = R.drawable.warm, color = Color.Black, label = "WARM"),
    )

    // 网格布局
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .border(.4.dp, Color(0xFFDADADA)),

        columns = GridCells.Fixed(3),
    ) {
        items(buttonConfigs.size) { index ->
            when (val config = buttonConfigs[index]) { // 赋值给局部变量 config
                is ButtonConfig.Normal -> {
                    ControlButton(
                        modifier = Modifier
                            .aspectRatio(1f),
                        onClick = { /* 处理按钮点击 */ },
                        id = config.iconRes, // 传递图标资源
                        color = config.color,  // 传递颜色
                        label = config.label
                    )
                }

                else -> {
                    EmptyButton(
                        modifier = Modifier
                            .aspectRatio(1f)
                    )
                }
            }
        }

    }
}

@Composable
fun ControlButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    id: Int, // 图标资源
    color: Color, // 颜色
    label: String = "",
) {
    val haptic = LocalHapticFeedback.current

    Box(

        modifier = modifier
            .drawBehind {
                val strokeWidth = 0.4.dp.toPx()
                // 绘制四周的边框
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth
                ) // 顶部
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth
                ) // 左侧
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth
                ) // 右侧
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth
                ) // 底部
            }) {


        Column(
            modifier = modifier
                .clickable(onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                })
                .fillMaxWidth() // 让 Column 占满宽度
                .wrapContentSize(Alignment.Center), // 让内容居中
            horizontalAlignment = Alignment.CenterHorizontally // 让子元素水平居中
        ) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = color
            )
            Text(
                text = label,
                color = color,
                modifier = Modifier
                    .padding(top = 4.dp),
                fontFamily = N57Sans,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// 按钮配置密封类
sealed class ButtonConfig {
    data class Normal(val iconRes: Int, val color: Color, val label: String) : ButtonConfig()
    object Empty : ButtonConfig()
}

@Composable
fun EmptyButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.Transparent)
            .drawBehind {
                val strokeWidth = 0.4.dp.toPx()
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth
                ) // 顶部
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth
                ) // 左侧
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth
                ) // 右侧
                drawLine(
                    Color(0xFFDADADA),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth
                ) // 底部
            }
    ) {

    }
}