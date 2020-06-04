package com.mitha.argearcamera.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mitha.argearcamera.AppConfig;
import com.mitha.argearcamera.api.ContentsResponse;


public class ContentsViewModel extends AndroidViewModel {

    private MutableLiveData<ContentsResponse> mutableLiveData;
    private ContentsRepository contentsRepository;

    public ContentsViewModel(Application application) {
        super(application);
        contentsRepository = ContentsRepository.getInstance();
        mutableLiveData = contentsRepository.getContents(AppConfig.API_KEY);
    }

    public LiveData<ContentsResponse> getContents() {
        return mutableLiveData;
    }
}
