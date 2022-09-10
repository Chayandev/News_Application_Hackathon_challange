package com.example.mynews



import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsListAdapter(private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
     val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       val currentItem=items[position]
        holder.titleView.text=currentItem.title
        holder.description.text=currentItem.description
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageView)
//        holder.share.setOnClickListener{
//            Log.d("Mytag","CkiclShare")
//            share(currentItem.title,currentItem.url)
//
//       }

    }
//   inner class ViewHolder(holder: NewsViewHolder,position: Int){
//       val currentItem=items[position]
//       share
//   }


    private fun share(title:String,url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val body="${title}\n${url}\nShared by MyNews App"
            putExtra(Intent.EXTRA_TEXT, body)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Send With:")

    }


    override fun getItemCount(): Int {
        return items.size
    }
 fun updateNews(updatedNews:ArrayList<News>)
 {
     items.clear()
     items.addAll(updatedNews)
     notifyDataSetChanged()   /* all hte items will caledd again */
 }

}


class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val titleView:TextView=itemView.findViewById(R.id.title)
    val imageView:ImageView=itemView.findViewById(R.id.image)
    val description:TextView=itemView.findViewById(R.id.description)
    val author:TextView=itemView.findViewById(R.id.author)
    val share:Button=itemView.findViewById(R.id.btnShare)

}
interface NewsItemClicked{
    fun onItemClicked(Item:News)
//    abstract fun JsonObjectRequest(get: Int, url: String, nothing: Nothing?, listener: Any, errorListener: Response.ErrorListener, function: () -> Unit): JsonObjectRequest
//    //abstract fun JsonObjectRequest(get: Int, url: String, nothing: Nothing?, listener: Response.ErrorListener?): JsonObjectRequest
}
interface ShareItemClick{
    open fun onShareclick(Item: News){

    }
}
