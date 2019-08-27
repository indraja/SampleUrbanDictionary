package com.test.nike.dictionary;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.test.nike.dictionary.databinding.MainActivityBinding;
import com.test.nike.dictionary.database.DefinitionDbManager;
import com.test.nike.dictionary.database.DefinitionEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel = null;
    private DefinitionListAdapter definitionListAdapter = null;

    private MainActivityBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    private void initializeData() {
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mainViewModel = new MainViewModel(this);
        mDataBinding.setViewModel(mainViewModel);

        setSupportActionBar(mDataBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        EditText editText = findViewById(R.id.edit_text_search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (mainViewModel != null) {
                        mainViewModel.makeWordSearchApiCall();
                    }
                    handled = true;
                }
                return handled;
            }
        });

        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = mDataBinding.rvDefinitions;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        definitionListAdapter = new DefinitionListAdapter(this, DefinitionDbManager.getInstance().getDefinitionByMostThumbsUpOrder());
        recyclerView.setAdapter(definitionListAdapter);
    }

    public void refreshDefinitionsAdapter(@Nullable List<DefinitionEntity> definitionEntities) {
        if (definitionEntities == null || definitionEntities.isEmpty()) {
            return;
        }

        if (definitionListAdapter != null) {
            definitionListAdapter.refreshAdapter(DefinitionDbManager.getInstance().getDefinitionByMostThumbsUpOrder());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Disable user interaction while ProgressBar is visible
     */
    public void setUntouchableWindowFlag() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * enable user interaction while ProgressBar is visible
     */
    public void clearWindowFlags() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Utils.hideKeyboard(this);
    }

    public void getDefinitionsByMostThumbsUpOrder() {
        RefreshAdapterAsyncTask refreshAdapterAsyncTask = new RefreshAdapterAsyncTask(true);
        refreshAdapterAsyncTask.execute();
    }

    public void getDefinitionsByLeastThumbsUpOrder() {
        RefreshAdapterAsyncTask refreshAdapterAsyncTask = new RefreshAdapterAsyncTask(false);
        refreshAdapterAsyncTask.execute();
    }

    private class RefreshAdapterAsyncTask extends AsyncTask<String, String, String> {

        private boolean sortMostThumbsUp;
        private List<DefinitionEntity> definitions;

        RefreshAdapterAsyncTask(boolean sortMostThumbsUp) {
            this.sortMostThumbsUp = sortMostThumbsUp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mainViewModel != null) {
                mainViewModel.showLoader.set(true);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            if (sortMostThumbsUp) {
                definitions = DefinitionDbManager.getInstance().getDefinitionByLeastThumbsUpOrder();
            } else {
                definitions = DefinitionDbManager.getInstance().getDefinitionByLeastThumbsUpOrder();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
            if (mainViewModel != null) {
                mainViewModel.showLoader.set(false);
            }

            refreshDefinitionsAdapter(definitions);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
