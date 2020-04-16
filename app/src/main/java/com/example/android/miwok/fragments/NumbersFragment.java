package com.example.android.miwok.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.pojos.Word;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mAfChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    nullifyMediaPlayer();
                    mAudioManager.abandonAudioFocus(mAfChangeListener);
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                default:
                    break;
            }
        }
    };

    MediaPlayer mMediaPlayer;
    MediaPlayer.OnCompletionListener mMpCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            nullifyMediaPlayer();
            mAudioManager.abandonAudioFocus(mAfChangeListener);
        }
    };

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_word, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> numberList = new ArrayList<Word>();
        numberList.add(new Word("lutti", "one", R.raw.number_one, R.drawable.number_one));
        numberList.add(new Word("otiiko", "two", R.raw.number_two, R.drawable.number_two));
        numberList.add(new Word("tolookosu", "three", R.raw.number_three, R.drawable.number_three));
        numberList.add(new Word("oyyisa", "four", R.raw.number_four, R.drawable.number_four));
        numberList.add(new Word("massokka", "five", R.raw.number_five, R.drawable.number_five));
        numberList.add(new Word("temmokka", "six", R.raw.number_six, R.drawable.number_six));
        numberList.add(new Word("kenekaku", "seven", R.raw.number_seven, R.drawable.number_seven));
        numberList.add(new Word("kawinta", "eight", R.raw.number_eight, R.drawable.number_eight));
        numberList.add(new Word("wo'e", "nine", R.raw.number_nine, R.drawable.number_nine));
        numberList.add(new Word("na'aacha", "ten", R.raw.number_ten, R.drawable.number_ten));

        WordAdapter adapter = new WordAdapter(getActivity(), numberList, R.color.category_numbers);
        ListView lv = rootView.findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentWord = numberList.get(position);

                nullifyMediaPlayer();

                int afResult = mAudioManager.requestAudioFocus(mAfChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (afResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getContext(), currentWord.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mMpCompletionListener);
                } else if (afResult == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                    nullifyMediaPlayer();
                    mAudioManager.abandonAudioFocus(mAfChangeListener);
                }
            }
        });

        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
        nullifyMediaPlayer();
        mAudioManager.abandonAudioFocus(mAfChangeListener);
    }

    private void nullifyMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
