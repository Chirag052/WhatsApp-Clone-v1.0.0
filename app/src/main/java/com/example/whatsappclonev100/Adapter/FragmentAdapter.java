package com.example.whatsappclonev100.Adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.whatsappclonev100.Fragment.CallFragment;
import com.example.whatsappclonev100.Fragment.ChatFragment;
import com.example.whatsappclonev100.Fragment.StatusFragment;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 :
                return new ChatFragment();
            case 1 :
                return new StatusFragment();
            case 2 :
                return new CallFragment();
            default:
                return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position==0)
        {
            title = "Chats";
        }
        if(position==1)
        {
            title = "Status";
        }
        if(position==2)
        {
            title = "Calls";
        }
        return title;
    }
}
