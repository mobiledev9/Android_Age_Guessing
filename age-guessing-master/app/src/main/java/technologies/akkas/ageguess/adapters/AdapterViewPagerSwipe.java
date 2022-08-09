package technologies.akkas.ageguess.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import technologies.akkas.ageguess.ui.TypeSelectorFragment;
import technologies.akkas.ageguess.ui.FragmentWelcome;

public class AdapterViewPagerSwipe extends FragmentPagerAdapter {
    public AdapterViewPagerSwipe(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
            {
                return new FragmentWelcome();
            }

            case 1:
            {
                return new TypeSelectorFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }



}
