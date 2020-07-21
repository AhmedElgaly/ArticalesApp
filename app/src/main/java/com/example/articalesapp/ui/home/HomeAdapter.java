package com.example.articalesapp.ui.home;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.articalesapp.Data.models.Article;
import com.example.articalesapp.Data.models.dataresponse;
import com.example.articalesapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.articleViewHolder> {
    private List<Article> articles = new ArrayList<>();
    private DetailsListener detailsListener;
    private static final String TAG = "HomeAdapter";
    SimpleDateFormat format;
    public HomeAdapter() {
        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    @NonNull
    @Override
    public HomeAdapter.articleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        return new HomeAdapter.articleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.articleViewHolder holder, int position) {
        final Article article = articles.get(position);
        holder.article_title.setText(article.getTitle());
        holder.article_author.setText("By "+article.getAuthor());

        try {
            Date date = format.parse(article.getPublishedAt());
            holder.publishedAt.setText(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Glide.with(holder.urlToImage.getContext())
                .load(article.getUrlToImage())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.favoriteprofile)
                .into(holder.urlToImage);
        holder.itemView.setOnClickListener(v -> detailsListener.openDetialsFragment(article));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public void setArticles(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }
    public void setDetailsListener(DetailsListener listener){
    this.detailsListener=listener;
    }
    public static class articleViewHolder extends RecyclerView.ViewHolder{
        TextView article_title ;
        TextView article_author ;
        TextView publishedAt ;
        ImageView urlToImage;

        public articleViewHolder(@NonNull View itemView) {
            super(itemView);
            article_title = itemView.findViewById(R.id.article_title);
            article_author = itemView.findViewById(R.id.article_author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            urlToImage = itemView.findViewById(R.id.urlToImage);
        }
    }
}
