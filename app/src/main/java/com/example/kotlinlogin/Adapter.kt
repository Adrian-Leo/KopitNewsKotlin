package com.example.kotlinlogin
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlogin.Detailed
import com.example.kotlinlogin.Model.Articles
import com.example.kotlinlogin.R

import com.squareup.picasso.Picasso


class Adapter
//        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
//        String time = null;
//        try{
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh-mm:");
//            Date date = simpleDateFormat.parse(t);
//            time = prettyTime.format(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return time;
//    }
//
//
//    public String getCountry(){
//        Locale locale = Locale.getDefault();
//        String country = locale.getCountry();
//        return country.toLowerCase();
//    }


(var context: Context, var articles: List<Articles>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val a = articles[position]
        holder.tvTitle.text = a.title
        holder.tvSource.text = a.source!!.name
        //        holder.tvDate.setText("\u2022"+dateTime(a.getPublishedAt()));
        holder.tvDate.text = a.publishedAt
        val imageUrl = a.urlToImage
        Picasso.with(context).load(imageUrl).into(holder.imageView)
        val url = a.url
        holder.cardView.setOnClickListener {
            val intent = Intent(context, Detailed::class.java)
            intent.putExtra("title", a.title)
            intent.putExtra("source", a.source!!.name)
            intent.putExtra("time", a.publishedAt)
            intent.putExtra("description", a.description)
            intent.putExtra("imageUrl", a.urlToImage)
            intent.putExtra("url", a.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvSource: TextView
        var tvDate: TextView
        var imageView: ImageView
        var cardView: CardView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvSource = itemView.findViewById(R.id.tvSource)
            tvDate = itemView.findViewById(R.id.tvDate)
            imageView = itemView.findViewById(R.id.tvImage)
            cardView = itemView.findViewById(R.id.cardView)
        }
    } //    public String dateTime(String t){

}