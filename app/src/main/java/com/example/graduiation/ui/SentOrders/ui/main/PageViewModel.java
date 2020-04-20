package com.example.graduiation.ui.SentOrders.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<String> string = new MutableLiveData<>();

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setmIndex(MutableLiveData<Integer> mIndex) {
        this.mIndex = mIndex;
    }

    public LiveData<String> getmText() {
        return mText;
    }

    public void setmText(LiveData<String> mText) {
        this.mText = mText;
    }

    public MutableLiveData<Integer> getmIndex() {
        return mIndex;
    }

    public void setString(String s) {
        string.setValue(s);
    }

    public MutableLiveData<String> getString() {
        return string;
    }


}