package com.test.nike.dictionary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.nike.dictionary.database.DefinitionEntity;

import java.util.List;

/**
 * DefinitionListAdapter to display the list of resulting word definitions, with number of thumbs up and thumbs down votes
 */
public class DefinitionListAdapter extends RecyclerView.Adapter<DefinitionListAdapter.ViewHolder> {

    private List<DefinitionEntity> wordDefinitions;
    private Context context;

    DefinitionListAdapter(Context context, List<DefinitionEntity> definitions) {
        this.wordDefinitions = definitions;
        this.context = context;
    }

    void refreshAdapter(List<DefinitionEntity> definitions) {
        this.wordDefinitions.clear();
        this.wordDefinitions = definitions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_definition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.definitionTv.setText(wordDefinitions.get(position).getDefinition());
        holder.thumbsUpTv.setText(context.getResources().getString(R.string.thumbs_up) + wordDefinitions.get(position).getThumbsUp());
        holder.thumbsDownTv.setText(context.getResources().getString(R.string.thumbs_down) + wordDefinitions.get(position).getThumbsDown());
    }


    @Override
    public int getItemCount() {
        return wordDefinitions != null ? wordDefinitions.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView definitionTv;
        private TextView thumbsUpTv;
        private TextView thumbsDownTv;

        public ViewHolder(View view) {
            super(view);
            definitionTv = itemView.findViewById(R.id.definition);
            thumbsUpTv = itemView.findViewById(R.id.thumbs_up_count);
            thumbsDownTv = itemView.findViewById(R.id.thumbs_down_count);
        }
    }
}