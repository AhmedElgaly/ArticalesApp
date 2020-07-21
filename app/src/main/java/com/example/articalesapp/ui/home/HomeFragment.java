package com.example.articalesapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.articalesapp.Data.models.Article;
import com.example.articalesapp.R;

import java.util.List;

public class HomeFragment extends Fragment implements DetailsListener{

    private HomeViewModel homeViewModel;
    private RecyclerView articles_recycle;
    private HomeAdapter homeAdapter;
    private NavController navController;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("LINK DEVELOPMENT");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articles_recycle = view.findViewById(R.id.articles_recycle);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
         navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        homeAdapter = new HomeAdapter();
        homeAdapter.setDetailsListener(this);
        ObserveData();
        initArticlesRecyclerView();

    }

    private void initArticlesRecyclerView(){
        articles_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        articles_recycle.setAdapter(homeAdapter);
    }
    private void ObserveData(){
        homeViewModel.GetData().observe(getViewLifecycleOwner(), articles -> homeAdapter.setArticles(articles));
    }

    @Override
    public void openDetialsFragment(Article article) {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.mobile_navigation, false)
                .build();
        Bundle bundle =new Bundle();
        bundle.putParcelable("articleInfo",article);
        navController.navigate(R.id.action_nav_home_to_detailsFragment,bundle,navOptions);
    }
}
