package com.example.android.miwok.adapters;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.pojos.Word;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mWordBackgroundColor;

    public WordAdapter(Activity context, ArrayList<Word> word, int wordBackgroundColor) {
        super(context, 0, word);
        mWordBackgroundColor = wordBackgroundColor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vListItem = convertView;

        if (vListItem == null) {
            vListItem = LayoutInflater.from(getContext()).inflate(R.layout.adapter_word_list, parent, false);
        }

        final Word currentWord = getItem(position);

        LinearLayout ll_word = vListItem.findViewById(R.id.ll_wordContainer);
        ll_word.setBackgroundResource(mWordBackgroundColor);

        TextView tvMiwok = vListItem.findViewById(R.id.word_miwok);
        tvMiwok.setText(currentWord.getMiwokTranslation());

        TextView tvDefault = vListItem.findViewById(R.id.word_english);
        tvDefault.setText(currentWord.getDefaultTranslation());

        ImageView ivImageDescription = vListItem.findViewById(R.id.iv_description);
        if (currentWord.hasImage()) {
            ivImageDescription.setImageResource(currentWord.getImageResourceId());
            ivImageDescription.setVisibility(View.VISIBLE);
        } else {
            ivImageDescription.setVisibility(View.GONE);
        }

        return vListItem;
    }
}
