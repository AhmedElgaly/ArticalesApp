package com.example.articalesapp.ui.articledetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.articalesapp.Data.models.Article;
import com.example.articalesapp.MainActivity;
import com.example.articalesapp.R;

public class DetailsFragment extends Fragment {

    private ImageView urlToImage;
    private TextView article_title,article_author,description;
    private Button btn_link;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.details_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("LINK DEVELOPMENT");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        urlToImage =view.findViewById(R.id.urlToImage);
        article_title= view.findViewById(R.id.article_title);
        article_author =view.findViewById(R.id.article_author);
        description= view.findViewById(R.id.description);
        btn_link= view.findViewById(R.id.btn_link);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Article article = bundle.getParcelable("articleInfo");
            article_title.setText(article.getTitle());
            article_author.setText("By "+article.getAuthor());
            description.setText(article.getDescription());
            loadImage(article.getUrlToImage());
            btn_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWesite(article.getUrl());
                }
            });


        }
    }
    private void loadImage(String urlImage){
        Glide.with(urlToImage.getContext())
                .load(urlImage)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.favoriteprofile)
                .into(urlToImage);
    }
    private void openWesite(String link){
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        getActivity().startActivity(intent);
    }

}
