package com.example.android.miwok.pojos;

public class Word {

    private static final int HAS_NO_IMAGE = 0;

    private String mMiwokTranslation, mDefaultTranslation;
    private int mImageResourceId = HAS_NO_IMAGE;
    private int mAudioResourceId;

    public Word(String miwokTranslation, String defaultTranslation, int audioResourceId) {
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String miwokTranslation, String defaultTranslation, int audioResourceId, int imageResourceId) {
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean hasImage()
    {
        return mImageResourceId != HAS_NO_IMAGE;
    }
}
