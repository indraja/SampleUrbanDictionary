package com.test.nike.dictionary;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.util.Log;

import com.test.nike.dictionary.database.DefinitionDbManager;
import com.test.nike.dictionary.network.Dictionary;
import com.test.nike.dictionary.network.ApiClientListener;
import com.test.nike.dictionary.network.NetworkModule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel class for main activity
 */
public class MainViewModel extends BaseObservable {
    private static final String TAG = MainViewModel.class.getSimpleName();

    public ObservableBoolean showLoader = new ObservableBoolean(false);
    public ObservableBoolean noDefinitions = new ObservableBoolean(false);

    public ObservableField<String> searchData = new ObservableField<>("");

    private MainActivity mainActivity;

    MainViewModel(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Load definitions from api call
     */
    public void makeWordSearchApiCall() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "on search click");
        }

        ApiClientListener apiClientListener = NetworkModule.getRetrofitInstance().create(ApiClientListener.class);
        String searchData = this.searchData.get();
        if (searchData == null || searchData.isEmpty()) {
            return;
        }

        mainActivity.setUntouchableWindowFlag();
        Call<Dictionary> imageSearchResponse = apiClientListener.makeWordSearchApiCall(searchData);
        showLoader.set(true);
        imageSearchResponse.enqueue(new Callback<Dictionary>() {
            @Override
            public void onResponse(Call<Dictionary> call, Response<Dictionary> response) {
                if (response == null || response.body() == null || (response != null && response.errorBody() != null)) {
                    onFailure(call, null);
                    return;
                }
                mainActivity.clearWindowFlags();
                showLoader.set(false);
                noDefinitions.set(false);
                Dictionary wordSearchApiResponse = response.body();
                DefinitionDbManager.getInstance().insertDefinitions(wordSearchApiResponse.getWordDefinitions());
                mainActivity.refreshDefinitionsAdapter(DefinitionDbManager.getInstance().getAllDefinitions());
            }

            @Override
            public void onFailure(Call<Dictionary> call, Throwable t) {
                mainActivity.clearWindowFlags();
                showLoader.set(false);
                noDefinitions.set(true);
            }
        });
    }

    public void getDefinitionsByMostThumbsUpOrder() {
        mainActivity.getDefinitionsByMostThumbsUpOrder();
    }

    public void getDefinitionsByLeastThumbsUpOrder() {
        mainActivity.getDefinitionsByLeastThumbsUpOrder();
    }

    /**
     * After edit text changed
     *
     * @param s
     */
    public void afterTextChanged(Editable s) {
        searchData.set(s.toString());
    }
}
