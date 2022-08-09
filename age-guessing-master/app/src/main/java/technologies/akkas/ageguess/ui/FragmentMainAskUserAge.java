package technologies.akkas.ageguess.ui;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.Date;

import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.GlobalClass;

public class FragmentMainAskUserAge extends Fragment implements View.OnClickListener {
    FloatingActionButton floatingActionButtonNext;
    GlobalClass globalVariable ;
    TextInputLayout inputLayoutYourAge;
    TextInputLayout inputLayoutEnterMonth;
    TextInputLayout inputLayoutEnterDay;
    TextInputEditText inputAge;
    TextInputEditText inputMonth;
    TextInputEditText inputDay;
    TextView tvTitleGuess;
    ImageView imgLogo;
    private ImageView imgShare;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_askuser_age,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
    globalVariable = (GlobalClass) getActivity().getApplicationContext();
    inputAge=view.findViewById(R.id.input_age);
    inputDay=view.findViewById(R.id.input_day);
    inputMonth=view.findViewById(R.id.input_month);
    tvTitleGuess=view.findViewById(R.id.tv_guess_title);
    floatingActionButtonNext=view.findViewById(R.id.floating_askuser_age);
    imgLogo=view.findViewById(R.id.img_logo);
    inputLayoutYourAge=view.findViewById(R.id.inputLayoutYourAge);
    inputLayoutEnterMonth=view.findViewById(R.id.inputlayoutMonth);
    inputLayoutEnterDay=view.findViewById(R.id.inputlayoutDay);
        imgShare = view.findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.shareApp(getContext());
            }
        });

    floatingActionButtonNext.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.floating_askuser_age:
            {
                checkFields();
            }
        }
    }

    private void checkFields() {
        try {


            if (inputAge.getText().toString().trim().length() == 0) {
                inputAge.setError(getString(R.string.please_enter_valid));
                return;
            }
            if (inputMonth.getText().toString().trim().length() == 0) {
                inputMonth.setError(getString(R.string.please_enter_valid));
                return;
            }
            if (inputMonth.getText().toString().trim().length() != 0) {
                int month = Integer.valueOf(inputMonth.getText().toString());
                if (month <= 0 || month >= 13) {
                    inputMonth.setError(getString(R.string.entermonth_between));
                    return;
                } else {
                    if (inputDay.getText().toString().trim().length() == 0) {
                        inputDay.setError(getString(R.string.please_enter_valid));
                        return;
                    } else {
                        int number = Integer.parseInt(inputDay.getText().toString());
                        if (number <= 0 || number >= 32) {
                            inputDay.setError(getString(R.string.enter_day_between));
                            return;
                        } else {
                            onCompleteIntialInput();
                        }
                    }
                }
            }

        }catch (Exception e)
        {
            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void onCompleteIntialInput() {
        try {
            int age = Integer.parseInt(inputAge.getText().toString());
            int value = -1;
            if (age - 3 < 0) {
                value = 0;
            } else {
                value = age - 4;
            }
            int value23 = age + 4;

            globalVariable.setValue1(value);
            globalVariable.setValue2(value23);
           /* value1 = value;
            value2 = value23;
*/
            int month = Integer.parseInt(inputMonth.getText().toString());
            globalVariable.setMonthEntered(month-1);
           // monthEntered = month;

            int day = Integer.parseInt(inputDay.getText().toString());
            globalVariable.setDayEntered(day);
//            dayEntered = day;

          //   monthEntered = monthEntered - 1;

             calculate(globalVariable.getMonthEntered(), globalVariable.getDayEntered(),
                       globalVariable.getValue1(), globalVariable.getValue2());


            Intent intent=new Intent(getContext(), YouAreActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static int calculateAge(int month, int day, int value1, int value2) {
        int counter = day % 7;
        while (counter < value1 && counter < value2) {
            counter += 7;
        }
        int thisYear = new Date().getYear() + 1900;
        int whattoAdd = thisYear - 2011;
        int answer = counter
                + whattoAdd;
        Calendar today = Calendar.getInstance();
        int todayMonth = today.get(Calendar.MONTH)
                + 1; //Java months go from 0 to 11

        int todayDayOfMonth =
                today.get(Calendar.DAY_OF_MONTH);


        if ((month > todayMonth)) {

            answer--;


        } else if ((month == todayMonth) && (day >
                todayDayOfMonth))

        {

            answer--;

        }


        return answer;


    }


    public void calculate(int month, int day, int value1, int value2) {

//        GoodAnswer = 0;
        globalVariable.setGoodAnswer(0);

        int whattoSub = 7;

        globalVariable.setAnswer(calculateAge(month + 1, day, value1, value2));

//        GoodAnswer = answer - whattoSub;
        globalVariable.setGoodAnswer(globalVariable.getAnswer()-whattoSub);
        Calendar birthdayNoYear = Calendar.getInstance();
        birthdayNoYear.set(getYear(month + 1, day, globalVariable.getGoodAnswer()), month + 1, day);
        LocalDate todayNoYear = LocalDate.now();
        LocalDate birthdayFormat2 = LocalDate.of(getYear(month + 1, day, globalVariable.getGoodAnswer()), month + 1, day);

        Period pNoYear = Period.between(birthdayFormat2, todayNoYear);

        long p2NoYear = ChronoUnit.DAYS.between(birthdayFormat2, todayNoYear);

        Calendar birthdayNoYear2 = Calendar.getInstance();

        birthdayNoYear2.set(getYear(month + 1, day,
                globalVariable.getAnswer()), month + 1, day);

        LocalDate todayNoYear2 = LocalDate.now();

        LocalDate birthdayFormat3 =
                LocalDate.of(getYear(month + 1, day, globalVariable.getAnswer()), month + 1,
                        day);

        Period pNoYear2 = Period.between(birthdayFormat3,
                todayNoYear2);

        long p2NoYear2 =
                ChronoUnit.DAYS.between(birthdayFormat3, todayNoYear2);

        if ((globalVariable.getAnswer() <= value2) && (globalVariable.getAnswer()>= value1))

        {


            if (pNoYear.getYears() == 0) {

                globalVariable.setYearYouAre(""+pNoYear.getYears());
                globalVariable.setMonthYouAre(""+pNoYear.getMonths());
                globalVariable.setDaysYouAre(""+pNoYear.getDays());
                globalVariable.setSingleAgeNotYMD("");

                globalVariable.setAgeShouldbeTwoOption1( "" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer()));
                globalVariable.setAgeShouldbeTwoOption2( "" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

                globalVariable.setCorrectAgeShouldBe("    "+(month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer())
                        + "\nor " +
                        (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

            } else {

                // i have to change it
                globalVariable.setSingleAgeNotYMD("Your age is: " +
                        (globalVariable.getGoodAnswer()) + " or " + globalVariable.getAnswer());

                globalVariable.setAgeShouldbeTwoOption1("" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer()));
                globalVariable.setAgeShouldbeTwoOption2( "" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));
                globalVariable.setCorrectAgeShouldBe("    "+ (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer())
                        + "\nor "+ (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

            }

        } else if (globalVariable.getAnswer() <= value2)
        {
            globalVariable.setAgeShouldbeTwoOption1("");
            globalVariable.setAgeShouldbeTwoOption2("");
            globalVariable.setYearYouAre(""+pNoYear2.getYears());
            globalVariable.setMonthYouAre(""+pNoYear2.getMonths());
            globalVariable.setDaysYouAre(""+pNoYear2.getDays());
            globalVariable.setSingleAgeNotYMD("");


            //   globalVariable.setStringBufferguessAge(globalVariable.getStringBufferguessAge() + "You are " + pNoYear2.getYears() + " years, " + pNoYear2.getMonths() + "months, and " + pNoYear2.getDays() + " days old. (" + p2NoYear2 + " days total)");

            globalVariable.setCorrectAgeShouldBe(""+ (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

        } else if (globalVariable.getGoodAnswer() >= value1)
        {


            globalVariable.setAgeShouldbeTwoOption1("");
            globalVariable.setAgeShouldbeTwoOption2("");
            globalVariable.setYearYouAre(""+pNoYear.getYears() );
            globalVariable.setMonthYouAre(""+pNoYear.getMonths());
            globalVariable.setDaysYouAre(""+pNoYear.getDays());
            globalVariable.setSingleAgeNotYMD("");

            globalVariable.setCorrectAgeShouldBe(""+(month + 1) + "/" + day + "/" + getYear(month
                    + 1, day, globalVariable.getGoodAnswer()));

        }



    }


    public static int getYear(int month, int day, int age) {
        int answer = -1;
        int thisYear = new Date().getYear() + 1900;
        answer = thisYear - age;
        Calendar today = Calendar.getInstance();
        int todayMonth = today.get(Calendar.MONTH) + 1; //Javamonths go from 0 to 11

        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);


        if ((month > todayMonth))

        {

            answer--;


        } else if ((month == todayMonth) && (day >
                todayDayOfMonth))

        {

            answer--;

        }


        return answer;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            AnimationOnLogo();
            SlideInputLayout();
        }
    }
    private void SlideInputLayout() {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.transalate_fromx);
        tvTitleGuess.startAnimation(animation);
        inputLayoutYourAge.startAnimation(animation);
        inputLayoutEnterMonth.startAnimation(animation);
        inputLayoutEnterDay.startAnimation(animation);
        floatingActionButtonNext.startAnimation(animation);

    }
    private void AnimationOnLogo() {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_animation);
        imgLogo.startAnimation(animation);
    }
    @Override
    public void onResume() {
        super.onResume();
        AnimationOnLogo();
        SlideInputLayout();
    }
}
