package technologies.akkas.ageguess.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.GlobalClass;

public class FragmentWelcome extends Fragment {
    ImageView imgLogo;
    private ImageView imgShare;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome,container,false);

        init(view);
        return view;
    }

    private void init(View view) {
        imgLogo=view.findViewById(R.id.imglogo);

        AnimationLogo();

        imgShare = view.findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.shareApp(getContext());
            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser&&imgLogo!=null)
        {
            AnimationLogo();
        }
    }

    private void AnimationLogo() {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_animation);
        imgLogo.startAnimation(animation);

    }
}
