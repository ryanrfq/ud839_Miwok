package com.example.android.miwok.adapters;

import android.os.Bundle;

import com.example.android.miwok.R;
import com.example.android.miwok.fragments.ColorsFragment;
import com.example.android.miwok.fragments.FamilyFragment;
import com.example.android.miwok.fragments.NumbersFragment;
import com.example.android.miwok.fragments.PhrasesFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryAdapter extends FragmentStateAdapter {

    private static final String[] tabTitle = {
            "Numbers",
            "Family",
            "Colours",
            "Phrases"
    };

    public static String getTabTitle(int position) {
        return tabTitle[position];
    }

    public CategoryAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default: return null;
        }
    }

}
