package com.example.android.miwok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.pojos.Word;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

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

    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mMpCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            nullifyMediaPlayer();
            mAudioManager.abandonAudioFocus(mAfChangeListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        mAudioManager = (AudioManager) PhrasesActivity.this.getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> phraseList = new ArrayList<Word>();
        phraseList.add(new Word("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going));
        phraseList.add(new Word("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name));
        phraseList.add(new Word("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        phraseList.add(new Word("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        phraseList.add(new Word("kuchi achit", "I'm feeling good.", R.raw.phrase_im_feeling_good));
        phraseList.add(new Word("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        phraseList.add(new Word("hәә’ әәnәm", "Yes, I'm coming.", R.raw.phrase_yes_im_coming));
        phraseList.add(new Word("әәnәm", "I'm coming.", R.raw.phrase_im_coming));
        phraseList.add(new Word("yoowutis", "Let's go.", R.raw.phrase_lets_go));
        phraseList.add(new Word("әnni'nem", "Come here.", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, phraseList, R.color.category_phrases);
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentWord = phraseList.get(position);

                nullifyMediaPlayer();


                int afResult = mAudioManager.requestAudioFocus(mAfChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (afResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, currentWord.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mMpCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        nullifyMediaPlayer();
        mAudioManager.abandonAudioFocus(mAfChangeListener);
    }

    private void nullifyMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
