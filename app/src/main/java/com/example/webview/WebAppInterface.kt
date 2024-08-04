package com.example.webview

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.webkit.JavascriptInterface
import android.webkit.WebView

class WebAppInterface(private val context: Context, private val webView: WebView) {

    // 받아서 앱이 처리
    @JavascriptInterface
    fun moveTest() {
        val intent = Intent(context, SecondActivity::class.java)
        context.startActivity(intent)
    }

    // 보내서 웹이 처리
    @JavascriptInterface
    fun callTest(name: String, phoneNumber: String, identity: String, agency: String) {
        // 데이터를 JavaScript로 보내는 메서드 호출
        sendDataToJavaScript(name, phoneNumber, identity, agency)
    }

    private fun sendDataToJavaScript(name: String, phoneNumber: String, identity: String, agency: String) {
        // JavaScript 함수 호출
        val script = """
            if (typeof receiveDataFromAndroid === 'function') {
                receiveDataFromAndroid('$name', '$phoneNumber', '$identity', '$agency');
            }
        """.trimIndent()
        webView.evaluateJavascript(script, null)
    }


    @JavascriptInterface
    fun sendAppVersion() {
        val version = getAppVersion()
        sendDataToJavaScript(version)
    }

    private fun getAppVersion(): String {
        return try {
            val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }

    private fun sendDataToJavaScript(version: String) {
        val script = """
            if (typeof receiveAppVersion === 'function') {
                receiveAppVersion('$version');
            }
        """.trimIndent()
        webView.evaluateJavascript(script, null)
    }
}