package com.example.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webview.ui.theme.WebViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebViewTheme {
                // Scaffold를 사용하여 기본 레이아웃 구조를 설정
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // WebViewScreen을 호출하여 화면에 웹뷰를 표시
                    WebViewScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient() // 링크 클릭 시 외부 브라우저가 아닌 WebView에서 열리도록 설정
                webChromeClient = WebChromeClient() // JavaScript의 alert을 처리할 수 있도록 설정
                settings.apply {
                    javaScriptEnabled = true // JavaScript 사용 가능
                    domStorageEnabled = true // DOM Storage 사용 가능
                    cacheMode = WebSettings.LOAD_NO_CACHE // 캐시 사용 안 함
                }
                addJavascriptInterface(WebAppInterface(context, this), "Android")
                loadUrl("https://www.naver.com") // 로드할 웹 페이지의 URL
            }
        },
        modifier = modifier.fillMaxSize() // 화면 전체를 채우도록 설정
    )
}


@Preview(showBackground = true)
@Composable
fun WebViewScreenPreview() {
    WebViewTheme {
        WebViewScreen()
    }
}
