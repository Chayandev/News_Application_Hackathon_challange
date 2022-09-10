package com.example.mynews
import android.content.ClipData
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.nio.channels.InterruptedByTimeoutException
import kotlin.jvm.Throws
import com.example.mynews.ShareItemClick as ShareItemClick

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter
    private lateinit var share: Button
    private lateinit var progressBar: ProgressBar

    // on below line we are creating variable for all
    // floating action buttons and a boolean variable.
    lateinit var addFAB: FloatingActionButton
    lateinit var modeChange: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    var fabVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter



        addFAB = findViewById(R.id.btnClicked)
        modeChange = findViewById(R.id.btnModeChange)
        fabVisible = false
        addFAB.setOnClickListener {


            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                modeChange.show()


                // on below line we are setting
                // their visibility to visible.
                modeChange.visibility = View.VISIBLE


                // on below line we are checking image icon of add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.ic_close))

                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home and settings fab
                modeChange.hide()


                // on below line we are changing the
                // visibility of home and settings fab
                modeChange.visibility = View.GONE


                // on below line we are changing image source for add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.ic_add))

                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }
        var click = true
        modeChange.setOnClickListener {
            click = if (click) {
                // setContentView(R.layout.item_news)

                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                !click
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

                !click
            }
        }


    }

    private fun fetchData() {

        val url = "https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                val newsjsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsjsonArray.length()) {
                    val newsJsonObject = newsjsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("description")

                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Log.d("Mytag", "FetchData")


            }
        )

        MySingleton.getInstance(this).addRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(Item: News) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(Item.url));

    }
//  fun share():ShareItemClick{
//    (Item: News){
//      val sendIntent:Intent = Intent().apply {
//                  action=Intent.ACTION_SEND
//                  val body:String="${Item.title}\n ${Item.url}\nShare form MyNews app"
//                  putExtra(Intent.EXTRA_TEXT,body)
//                  type="text/plain"
//              }
//              val shareIntent=Intent.createChooser(sendIntent)
//          }
//
//       }





}


