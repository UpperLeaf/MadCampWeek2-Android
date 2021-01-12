package com.wonsang.madcampweek2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wonsang.madcampweek2.fragment.Fragment1;
import com.wonsang.madcampweek2.fragment.Fragment3;
import com.wonsang.madcampweek2.fragment.GalleryFragment;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public static int CONTACT_POSITION = 0;
    public static int GALLERY_POSITION = 1;
    public static int BLOG_POSITION = 2;

    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        items = new ArrayList<Fragment>();
        items.add(new Fragment1());
        items.add(new GalleryFragment());
        items.add(new Fragment3());

        itext.add("contact");
        itext.add("gallery");
        itext.add("blog");
    }

    public ArrayList<Fragment> getItems() {
        return items;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
