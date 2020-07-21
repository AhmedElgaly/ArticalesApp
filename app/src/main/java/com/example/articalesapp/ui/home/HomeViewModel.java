package com.example.articalesapp.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.articalesapp.API.APIService;
import com.example.articalesapp.API.ApiUtils;
import com.example.articalesapp.Data.models.Article;
import com.example.articalesapp.Data.models.dataresponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    MutableLiveData<List<Article>> articlesMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private APIService mAPIService;
    public HomeViewModel() {

        mAPIService = ApiUtils.getAPIService();
    }

    //here we use ZIP operator to combine different observables into one observable
    //arranging returned data (Parallel)
    public LiveData<List<Article>> fetchArticles(){
        Observable<dataresponse> observable1 = mAPIService.getAllData1();
        Observable<dataresponse> observable2 = mAPIService.getAllData2();
        Observable<List<Article>> result =
                Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()),
                        observable1.subscribeOn(Schedulers.io()) , (responseType1, responseType2, type) -> {
                            List<Article> list = new ArrayList();
                            list.addAll(responseType1.getArticles());
                            list.addAll(responseType2.getArticles());

                            return list;
                        });
        result.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        articlesMutableLiveData.setValue(articles);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return articlesMutableLiveData;
    }
    //here we use flatMap operator to combine different observables into one observable
    //without arranging returned data (not Parallel)
    public  LiveData<List<Article>>GetData(){
        List<Article> result = new ArrayList<>();
         disposable.add(mAPIService.getAllData1()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<dataresponse, ObservableSource<dataresponse>>) response1 -> {
                    result.addAll(response1.getArticles());
                    return mAPIService.getAllData2();
                }).map(response -> {
                    result.addAll(response.getArticles());
                    return result;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Article>>() {
                    @Override
                    public void onNext(List<Article> articles) {
                        Log.d(TAG, "rx onNext: "+ articles);
                        Log.d(TAG, "testonNext: "+"done");
                        articlesMutableLiveData.setValue(articles);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        return  articlesMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}