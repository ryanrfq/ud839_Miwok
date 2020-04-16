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
public class FamilyFragment extends Fragment {

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

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_word, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> familyMemberList = new ArrayList<Word>();
        familyMemberList.add(new Word("әpә", "father", R.raw.family_father, R.drawable.family_father));
        familyMemberList.add(new Word("әṭa", "mother", R.raw.family_mother, R.drawable.family_mother));
        familyMemberList.add(new Word("angsi", "son", R.raw.family_son, R.drawable.family_son));
        familyMemberList.add(new Word("tune", "daughter", R.raw.family_daughter, R.drawable.family_daughter));
        familyMemberList.add(new Word("taachi", "older brother", R.raw.family_older_brother, R.drawable.family_older_brother));
        familyMemberList.add(new Word("chalitti", "younger brother", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        familyMemberList.add(new Word("teṭe", "older sister", R.raw.family_older_sister, R.drawable.family_older_sister));
        familyMemberList.add(new Word("kolliti", "younger sister", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        familyMemberList.add(new Word("ama", "grandmother", R.raw.family_grandmother, R.drawable.family_grandmother));
        familyMemberList.add(new Word("paapa", "grandfather", R.raw.family_grandfather, R.drawable.family_grandfather));

        WordAdapter adapter = new WordAdapter(getActivity(), familyMemberList, R.color.category_family);
        ListView lv = rootView.findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentWord = familyMemberList.get(position);

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
