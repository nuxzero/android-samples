package com.example.screenshot

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebViewClient
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val testHtml = """
        <!DOCTYPE HTML>

        <html lang="en">
        <head>
            <title>3DS - One-Time Passcode - PA</title>
            <meta charset="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1"/>

            <style>
                /* General Styling*/
                body {
                    font-family: 'Open Sans', sans-serif;
                    font-size: 11pt;
                }

                ol, ul {
                    list-style: none;
                    padding-left: 0;
                }


                summary::-webkit-details-marker {
                    display: none
                }

                summary {
                    background-color: #eeeeee;
                    color: #333;
                    padding: 7px 10px 7px 10px;
                    margin-bottom: 13px;
                }

                p.summary-p {
                    padding: 7px 10px 7px 10px;
                    background: #f4f4f4;
                    margin-top: -22px;
                    margin-bottom: 0;
                }

                .button {
                    background-color: #007987;
                    border-radius: 5px;
                    border: 0;
                    color: #ffffff;
                    cursor: pointer;
                    display: inline-block;
                    font-weight: 400;
                    height: 2.5em;
                    line-height: 2.5em;
                    text-align: center;
                    text-decoration: none;
                    white-space: nowrap;
                    width: 35%;
                    margin-bottom: 15px;
                    margin-right: 15px;
                }

                .button.primary {
                    background-color: #ef8200;
                }

                .button:hover {
                    background-color: #737373;
                }

                .button:active {
                    background-color: #595959;
                }

                .canvas {
                    padding: 15px;
                    border: 1px solid #eee;
                    margin-bottom: 15px;
                }

                #content h2 {
                    margin-top: 0;
                }

                .input-field {
                    background: #fff;
                    border: solid 1px #e0e0e0;
                    border-radius: 5px;
                    color: inherit;
                    display: block;
                    outline: 0;
                    padding: 7px 0 7px 0;
                    text-decoration: none;
                    width: 40%;
                    margin-bottom: 15px;
                }


                /*Images*/
                .logo-bank {
                    background-position: center;
                    display: inline-block;
                    width: 150px;
                    height: 35px;
                    background-size: auto 33px;
                    background-repeat: no-repeat;
                }

                .logo-emvco{
                    display:inline-block;
                    width: 140px;
                    height: 47px;
                    float:right;
                    background-size: auto 33px;
                    background-repeat: no-repeat;
                }

            </style>
        </head>
        <body class="">
        <div id="page-wrapper">
            <!-- Header Section -->
            <header id="header" class="canvas">
                <span class="logo-emvco"></span>
                <span class="logo-bank"></span>
            </header>

            <!-- Main Content Section -->
            <section id="content" class="canvas">
                <div class="row">
                    <h2>Purchase Authentication</h2>
                    <p>We have send you a text message with a code to your registered mobile number ending in ***.</p>
                    <p>You are paying Merchant ABC the amount of         xxx        .xx on mm/dd/yy.</p>
                </div>

                <div class="row">
                    <p><strong>Enter your code below:</strong></p>
                    <form action="HTTPS://EMV3DS/challenge" method="get" name="cardholderInput">
                        <label>
                            <input type="text" class="input-field" name="code" value=" Enter Code Here">
                        </label>
                        <input type="submit" class="button primary" value="SUBMIT">
                        <input type="submit" class="button" value="RESEND CODE">
                    </form>
                </div>
            </section>

            <!-- Help Section -->
            <section id="help" class="container">
                <div class="row detailed">
                    <details>
                        <summary>Learn more about authentication</summary>
                        <p class="summary-p">Authentication information will be displayed here.</p>
                    </details>
                </div>
                <div class="row">
                    <details>
                        <summary>Need some help?</summary>
                        <p class="summary-p">Help content will be displayed here.</p>
                    </details>
                </div>
            </section>

        </div>
        </body>
        </html>
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.webViewClient = WebViewClient()
//        webview.loadUrl("https://dashboard.omise.co/signup?locale=en&origin=direct")
        webview.loadDataWithBaseURL(null, testHtml, "text/html", "UTF-8", null)


        takeScreenShot.setOnClickListener {
            screenShot(window)
        }
    }

    private fun screenShot(window: Window) {
        val actionBarBitmap = takeActionBarScreenShot(window)
//        val contentBitmap = getScrollView(scrollView, scrollView.getChildAt(0).height, scrollView.getChildAt(0).width)
        val contentBitmap = getScrollView(window)

        val wholeScreenBitmap = combineBitmaps(actionBarBitmap, contentBitmap)

        resultImage.setImageBitmap(wholeScreenBitmap)
    }

    private fun takeActionBarScreenShot(window: Window): Bitmap {
        val view = window.decorView.rootView
        val statusBarView = (view as ViewGroup).getChildAt(2)
        val navigationBottomView = (view as ViewGroup).getChildAt(1)
        val actionBarView =
            ((((view as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup).getChildAt(
                0
            ) as ViewGroup).getChildAt(1)
        val contentView =
            ((((view as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup).getChildAt(
                0
            ) as ViewGroup).getChildAt(0)
        val width =
            if (view.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                view.width - navigationBottomView.width
            } else {
                view.width
            }
        val height = statusBarView.height + actionBarView.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun getScrollView(window: Window): Bitmap {
        val view = window.decorView.rootView
        val contentView =
            ((((view as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup).getChildAt(
                0
            ) as ViewGroup).getChildAt(0) as ContentFrameLayout
        val scrollView = contentView.getChildAt(0) as ScrollView
        val width = scrollView.getChildAt(0).width
        val height = scrollView.getChildAt(0).height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable: Drawable? = scrollView.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        scrollView.draw(canvas)
        return bitmap
    }

    private fun combineBitmaps(vararg bitmaps: Bitmap): Bitmap {
        val width = listOf(*bitmaps).first().width
        val height = listOf(*bitmaps).map { it.height }.reduce { acc, total -> acc + total }

        val combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(combinedBitmap)

        var top = 0f
        for (bitmap in bitmaps) {
            canvas.drawBitmap(bitmap, 0f, top, null)
            top += bitmap.height
        }

        return combinedBitmap
    }
}
