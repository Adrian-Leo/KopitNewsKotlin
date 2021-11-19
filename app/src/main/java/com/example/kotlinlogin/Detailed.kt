package com.example.kotlinlogin

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class Detailed : AppCompatActivity() {
    var tvTitle: TextView? = null
    var tvSource: TextView? = null
    var tvDate: TextView? = null
    var tvDesc: TextView? = null
    var imageView: ImageView? = null

    //    CardView cardView;
    var webView: WebView? = null
    var loader: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        tvTitle = findViewById<View>(R.id.tvTitle) as TextView
        tvSource = findViewById<View>(R.id.tvSource) as TextView
        tvDate = findViewById<View>(R.id.tvDate) as TextView
        tvDesc = findViewById<View>(R.id.tvDesc) as TextView
        loader = findViewById<View>(R.id.webViewLoader) as ProgressBar
//        loader.setVisibility(View.VISIBLE)
        imageView = findViewById<View>(R.id.tvImage) as ImageView
        webView = findViewById(R.id.webView) as WebView
        val intent = intent
        val title = intent.getStringExtra("title")
        val source = intent.getStringExtra("source")
        val time = intent.getStringExtra("time")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")
        tvTitle!!.setText(title)
        tvSource!!.setText(source)
        tvDate!!.setText(time)
        tvDesc!!.setText(description)
        Picasso.with(this@Detailed).load(imageUrl).into(imageView)
        webView!!.getSettings().domStorageEnabled = true
        webView!!.getSettings().javaScriptEnabled = true
        webView!!.getSettings().loadsImagesAutomatically = true
        webView!!.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        webView!!.setWebViewClient(WebViewClient())
        webView!!.loadUrl(url!!)
        if (webView!!.isShown()) {
            loader!!.setVisibility(View.INVISIBLE)
        }
    }
}