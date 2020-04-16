package com.example.android.miwok.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.pojos.Word;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_word, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> colorList = new ArrayList<Word>();
        colorList.add(new Word("weṭeṭṭi", "red", R.raw.color_red, R.drawable.color_red));
        colorList.add(new Word("chokokki", "green", R.raw.color_green, R.drawable.color_green));
        colorList.add(new Word("ṭakaakki", "brown", R.raw.color_brown, R.drawable.color_brown));
        colorList.add(new Word("ṭopoppi", "gray", R.raw.color_gray, R.drawable.color_gray));
        colorList.add(new Word("kululli", "black",  R.raw.color_black, R.drawable.color_black));
        colorList.add(new Word("kelelli", "white", R.raw.color_white, R.drawable.color_white));
        colorList.add(new Word("ṭopiisә", "dusty yellow", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        colorList.add(new Word("chiwiiṭә", "mustard yellow", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(getActivity(), colorList, R.color.category_colors);
        ListView lv = rootView.findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentWord = colorList.get(position);

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
