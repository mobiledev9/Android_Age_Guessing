package technologies.akkas.ageguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import technologies.akkas.ageguess.GlobalClass;
import technologies.akkas.ageguess.R;

public class YouAreActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    FloatingActionButton floatingActionButton;
    ImageView imgLogo;
    TextView tvYouAre;
    CardView cardYear;
    CardView cardMonth;
    CardView cardDays;
    CardView cardCorrectBirthday;
    TextView tvIsTheInformationOk;
    GlobalClass globalClass;
    TextView tvYear;
    TextView tvMonth;
    TextView tvDays;
    TextView tvCorrectAgeShouldActual;
    TextInputLayout inputLayoutPressYorN;
    LinearLayout linearLayoutNoYMD;
    LinearLayout linearLayoutWithYMD;
    TextView tvNoYMD;
    TextInputEditText inputEditTextYorN;
    String stringBufferPossibleAge = "";
    CardView cardTwoPossiblities;
    CardView cardRecyclerPossibleBirthdays;
    TextInputEditText inputEditTextYearOfBirth;
    TextInputLayout inputLayoutYearOfBirth;
    boolean isRefreshIconShow = false;
    private ImageView imgShare;
    ArrayList<Integer> dataListNew = new ArrayList<>();
    ArrayList<Integer> dataListNew2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_youare);
        init();
    }

    private void init() {


        globalClass = (GlobalClass) getApplicationContext();
        cardTwoPossiblities = findViewById(R.id.cardTwoPossibilites);
        cardRecyclerPossibleBirthdays = findViewById(R.id.cardRecyclerPossibleBirthday);
        inputEditTextYearOfBirth = findViewById(R.id.inputEditYearBirth);
        inputLayoutYearOfBirth = findViewById(R.id.inputEditLayoutYearBirth);
        inputEditTextYorN = findViewById(R.id.inputEditYorN);
        linearLayoutNoYMD = findViewById(R.id.linear_noyear);
        linearLayoutWithYMD = findViewById(R.id.linear_withyear);
        tvYear = findViewById(R.id.tv_card_year);
        tvMonth = findViewById(R.id.tv_card_month);
        tvDays = findViewById(R.id.tv_card_days);
        tvCorrectAgeShouldActual = findViewById(R.id.tv_correct_age_should_actual);
        imgLogo = findViewById(R.id.imgLogo);
        tvYouAre = findViewById(R.id.tv_youare);
        cardYear = findViewById(R.id.card_year);
        cardMonth = findViewById(R.id.card_month);
        cardDays = findViewById(R.id.card_days);
        cardCorrectBirthday = findViewById(R.id.card_correct_birthday);
        tvIsTheInformationOk = findViewById(R.id.tv_isthe_information_ok);
        inputLayoutPressYorN = findViewById(R.id.inputlayout_y_n);
        floatingActionButton = findViewById(R.id.floating_youare_next);
        tvNoYMD = findViewById(R.id.tv_noYMD);

        imgShare = findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.shareApp(YouAreActivity.this);
            }
        });

        findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouAreActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouAreActivity.this, AskUserForAgeActivity.class));
                finish();
            }
        });


        papulateData();

        inputEditTextYorN.addTextChangedListener(this);


        floatingActionButton.setOnClickListener(this);

        inputEditTextYearOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkDataOnTextChange(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkDataOnTextChange(CharSequence s) {
        try {
            try {
                AgeGuru.birthYear = Integer.parseInt(s.toString());
                int[] birth = new int[4];
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please try a valid input. You entered: " + AgeGuru.birthYear);
            }

            int oldDay2 = globalClass.getDayEntered();
            int year = AgeGuru.birthYear;

            String oldDate2 = "" + (globalClass.getMonthEntered()+1) + "/" + (oldDay2) + "/" + AgeGuru.getYear(globalClass.getMonthEntered(),  globalClass.getDayEntered(),  globalClass.getGuessingAge());
            String birthDisplay;

            System.out.println();

            int[] selectedBirth = new int[3];

            /*ArrayList<Integer> dataSet = AgeGuru.possibleAge(globalClass.getMonthEntered(), globalClass.getDayEntered(),
                    AgeGuru.getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getAnswer()));
*/
            if (dataListNew.size() > 0) {
                selectedBirth = AgeGuru.selectAge(dataListNew, globalClass.getMonthEntered()+1, globalClass.getDayEntered(), AgeGuru.getYear(globalClass.getMonthEntered(),  globalClass.getDayEntered(),  globalClass.getGuessingAge()));
            }else{
                selectedBirth = AgeGuru.selectAge(dataListNew2, globalClass.getMonthEntered()+1, globalClass.getDayEntered(), AgeGuru.getYear(globalClass.getMonthEntered(),  globalClass.getDayEntered(),  globalClass.getGuessingAge()));
            }
            Calendar birthdaySelected = Calendar.getInstance();
            birthdaySelected.set(selectedBirth[2], selectedBirth[1], selectedBirth[0]);

            LocalDate selectedToday = LocalDate.now();

            LocalDate birthdayFormatSelected = LocalDate.of(selectedBirth[2], selectedBirth[0], selectedBirth[1]);

            Period pSelected = Period.between(birthdayFormatSelected, selectedToday);

            long p2Selected = ChronoUnit.DAYS.between(birthdayFormatSelected, selectedToday);

            birthDisplay = "   The birthdate should be:   " + selectedBirth[0] + "/" + selectedBirth[1] + "/" + selectedBirth[2];

            System.out.println();

            System.out.println("The age should be: " + pSelected.getYears() + " years, " + pSelected.getMonths() +
                    " months, and " + pSelected.getDays() +
                    " days old. (" + p2Selected + " days total)");


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("You are ").append(pSelected.getYears()).append(" years, ").append(pSelected.getMonths()).append(" months, and ").append(pSelected.getDays()).append(" days old. (").append(p2Selected).append(" days total)\n");
            birthDisplay = "\n\nThe birthdate should be:   " + selectedBirth[0] + "/" + selectedBirth[1] + "/" + selectedBirth[2];
            stringBuilder.append("\n\nThe correct age is:   ").append(pSelected.getYears()).append("   ").append(birthDisplay).append("\n\nThe birthdate you entered was: ").append(oldDate2).append("\n");


            ((TextView) findViewById(R.id.how_old_tv)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.how_old_tv)).setText(stringBuilder);

            System.out.println();

            System.out.println("The age should be: " + pSelected.getYears() + birthDisplay + "   The birthdate you entered was: " + oldDate2);

            System.out.println();

            System.out.println("Please enter expected birthday: ");

            System.out.println();



            /*int birthYear = Integer.parseInt(s.toString());
            int[] birth = new int[4];

            try {
                birth = AgeGuru.expectedBirth(globalClass.getMonthEntered() + 1, globalClass.getDayEntered(), birthYear);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            Calendar birthday = Calendar.getInstance();
            birthday.set(birth[2], birth[0], birth[1]);
            int ageYear = 0;

            LocalDate today = LocalDate.now();
            LocalDate birthdayFormat = LocalDate.of(birth[2], birth[0], birth[1]);

            Period p = Period.between(birthdayFormat, today);
            long p2 = ChronoUnit.DAYS.between(birthdayFormat, today);

            ageYear = p.getYears();

            int oldDay2 = globalClass.getDayEntered();
            int year = birthYear;

            String oldDate2 = "" + (globalClass.getMonthEntered() + 1) + "/" + (oldDay2) + "/" + (getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getGuessingAge()));
            String birthDisplay;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("You are ").append(p.getYears()).append(" years, ").append(p.getMonths()).append(" months, and ").append(p.getDays()).append(" days old. (").append(p2).append(" days total)\n");
            birthDisplay = "\n\nThe birthdate should be:   " + birth[0] + "/" + birth[1] + "/" + birth[2];
            stringBuilder.append("\n\nThe correct age is:   ").append(ageYear).append("   ").append(birthDisplay).append("\n\nThe birthdate you entered was: ").append(oldDate2).append("\n");
            (findViewById(R.id.how_old_tv)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.how_old_tv)).setText(stringBuilder);
            */

        } catch (NumberFormatException e) {
            (findViewById(R.id.how_old_tv)).setVisibility(View.GONE);
            System.out.println("try a valid input birthYear");
            e.printStackTrace();
        }
    }

    private void papulateData() {
        if (globalClass.getSingleAgeNotYMD().equals("")) {
            linearLayoutWithYMD.setVisibility(View.VISIBLE);
            linearLayoutNoYMD.setVisibility(View.GONE);
            tvYear.setText("" + globalClass.getYearYouAre());
            tvMonth.setText("" + globalClass.getMonthYouAre());
            tvDays.setText("" + globalClass.getDaysYouAre());
            tvCorrectAgeShouldActual.setText(""+globalClass.getBirthGuessDate());

        } else {
            linearLayoutWithYMD.setVisibility(View.GONE);
            linearLayoutNoYMD.setVisibility(View.VISIBLE);
            tvNoYMD.setText("" + globalClass.getSingleAgeNotYMD());
        }

        ((TextView) findViewById(R.id.textView11)).setText("The correct Birthday should be:" + globalClass.getCorrectAgeShouldBe());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_youare_next: {

                Intent intent = new Intent(YouAreActivity.this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                /*if (inputEditTextYorN.getText().toString().trim().length() == 0) {
                    inputEditTextYorN.setError("Please fill the required field first");
                    return;
                }
                if (isRefreshIconShow) {
                    Intent intent = new Intent(YouAreActivity.this, WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    if (inputEditTextYearOfBirth.getText().toString().trim().length() == 0) {
                        inputEditTextYearOfBirth.setError("Please fill the required field first");
                        return;
                    } else {
                        yearBithAfterNProcedure();
                    }
                }*/

                break;
            }
        }
    }

    private void yearBithAfterNProcedure() {
        String yearPrev = inputEditTextYearOfBirth.getText().toString();

        try {
            int birthYear = Integer.parseInt(yearPrev);
            String birthYearString = yearPrev.toString();
            //  addTextViewHeading3("You Entered: " + birthYear);


            int ageYear = 0;

            int[] birth = expectedBirth(globalClass.getMonthEntered() + 1, globalClass.getDayEntered(), birthYear);
            Calendar birthday = Calendar.getInstance();
            birthday.set(birth[2], birth[0], birth[1]);
            LocalDate today = LocalDate.now();

            LocalDate birthdayFormat = LocalDate.of(birth[2], birth[0], birth[1]);

            Period p = Period.between(birthdayFormat,
                    today);

            long p2 = ChronoUnit.DAYS.between(birthdayFormat, today);
            ageYear = p.getYears();
            int oldDay2 = globalClass.getDayEntered();
            int year = birthYear;
            String oldDate2 = "" + (globalClass.getMonthEntered() + 1) + "/" +
                    (oldDay2) + "/" + (birthYearString);

            String birthDisplay;

            globalClass.setYouAreYMDEndScreen("You are " + p.getYears() +
                    " years, " + p.getMonths() + " months, and " + p.getDays() + " days old");
            globalClass.setDaysTotalEndScreen("(" + p2 + " Days Total)");

            birthDisplay = "\nThe Birthdate should be: " + birth[0]
                    + "/" + birth[1] + "/" + birth[2];
            globalClass.setTheCorrectAgeAndShouldbeisEndScreenTop("The Correct age is: " + ageYear + "" + birthDisplay);
            ;
            Intent intent = new Intent(YouAreActivity.this, ExpectedBirthdayActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
/*


                tvAgeCalculation.setVisibility(View.VISIBLE);
                tvAgeCalculation.setText(""+stringBuffer);*/

              /*  addTextViewHeading3("Please enter expected birthday:");
            //    nestedscroll.fullScroll(View.FOCUS_DOWN);
                String monthString = null;
                String dayString = null;
                String yearString = null;
                addTextViewHeading3("Enter Month:");
                addEditTextWithBtn("Enter Month", R.id.edt_month_birth_afterN, R.id.btn_month_birth_afterN);
                // nestedscroll.fullScroll(View.FOCUS_DOWN);*/

        } catch (Exception e) {
            // edtMonth.setError("Please enter valid year");
            return;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        isRefreshIconShow = false;
        AnimationOnLogo();
        SlideInputLayout();

    }

    private void AnimationOnLogo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        imgLogo.startAnimation(animation);
    }

    private void SlideInputLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transalate_fromx);
        tvYouAre.startAnimation(animation);
        cardYear.startAnimation(animation);
        cardMonth.startAnimation(animation);
        cardDays.startAnimation(animation);
        cardCorrectBirthday.startAnimation(animation);
        tvIsTheInformationOk.startAnimation(animation);
        inputLayoutPressYorN.startAnimation(animation);
        floatingActionButton.startAnimation(animation);
        linearLayoutNoYMD.startAnimation(animation);
        linearLayoutWithYMD.startAnimation(animation);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().toLowerCase().equals("n") || s.toString().toLowerCase().equals("y")) {
            if (onAdsLoaded()) {
                showAds(() -> onYN(s));
            } else {
                onYN(s);
            }
        }
    }

    private void onYN(Editable s) {
        if (s.toString().toLowerCase().equals("n")) {
            isRefreshIconShow = false;
            floatingActionButton.setImageResource(R.drawable.ic_next_arrow);

            stringBufferPossibleAge = "";
            floatingActionButton.setImageResource(R.drawable.ic_next_arrow);
            //for not radio options
//            if (globalClass.getSingleAgeNotYMD().equals("")) {
            cardRecyclerPossibleBirthdays.setVisibility(View.VISIBLE);
            cardTwoPossiblities.setVisibility(View.GONE);
//                possibleBirthdays();


            /*if (globalClass.getMonthEntered() == 0) {
                list = possibleAgeFromTheCode(globalClass.getMonthEntered() + 1, globalClass.getDayEntered(), getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getGoodAnswer()));
            } else
                list = possibleAgeFromTheCode(globalClass.getMonthEntered(), globalClass.getDayEntered(), getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getGoodAnswer()));*/



            if (globalClass.getMonthEntered() == 0) {
                dataListNew = AgeGuru.possibleAge(globalClass.getMonthEntered(), globalClass.getDayEntered(), AgeGuru.getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getGuessingAge()));
            } else
                dataListNew = AgeGuru.possibleAge(globalClass.getMonthEntered(), globalClass.getDayEntered(), AgeGuru.getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getGuessingAge()));

            for (int i = 0; i < dataListNew.size(); i++) {
                String birthAllYearsDisplay2 = String.format("%02d", dataListNew.get(i)) + "/" + String.format("%02d", dataListNew.get(i + 1)) + "/" + dataListNew.get(i + 2);
                i += 2;
                if (!stringBufferPossibleAge.contains(birthAllYearsDisplay2))
                    stringBufferPossibleAge += "\n\n" + birthAllYearsDisplay2;
            }

            TextView tvPossibleBirthdayText = findViewById(R.id.tv_actual_possiblebirthday);
            if (stringBufferPossibleAge.equals("")) {
                stringBufferPossibleAge = "No Possible Birthday Combination ";
            }
            tvPossibleBirthdayText.setText("" + stringBufferPossibleAge);
            inputLayoutYearOfBirth.setVisibility(View.VISIBLE);

//            } else {
//                cardRecyclerPossibleBirthdays.setVisibility(View.GONE);
//                inputLayoutYearOfBirth.setVisibility(View.GONE);
//                cardTwoPossiblities.setVisibility(View.VISIBLE);
//                papulateRadioGroupData();
//            }

//            inputEditTextYearOfBirth.setVisibility(View.GONE);
            isRefreshIconShow = true;
            floatingActionButton.setImageResource(R.drawable.ic_refresh);
        } else {
            cardRecyclerPossibleBirthdays.setVisibility(View.GONE);
            inputEditTextYearOfBirth.setVisibility(View.GONE);
            cardTwoPossiblities.setVisibility(View.GONE);
            isRefreshIconShow = true;
            floatingActionButton.setImageResource(R.drawable.ic_refresh);
        }
    }

    private void papulateRadioGroupData() {

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton radioBtnFirst = findViewById(R.id.radiobtnFirst);
        final RadioButton radioBtnSecond = findViewById(R.id.radiobtn2);

        LocalDate todayNoYear = LocalDate.now();
        String[] splitAgeOption1 = globalClass.getAgeShouldbeTwoOption1().split("/");
        LocalDate birthdayFormat2 = LocalDate.of(Integer.parseInt(splitAgeOption1[2]), Integer.parseInt(splitAgeOption1[0]),
                Integer.parseInt(splitAgeOption1[1]));

        Period pNoYear = Period.between(birthdayFormat2, todayNoYear);


        String[] splitAgeOption2 = globalClass.getAgeShouldbeTwoOption2().split("/");
        LocalDate birthdayFormat3 = LocalDate.of(Integer.parseInt(splitAgeOption2[2]), Integer.parseInt(splitAgeOption2[0]),
                Integer.parseInt(splitAgeOption2[1]));

        Period pNoYear2 = Period.between(birthdayFormat3, todayNoYear);

        radioBtnFirst.setText("" + pNoYear.getYears());
        radioBtnSecond.setText("" + pNoYear2.getYears());


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                cardRecyclerPossibleBirthdays.setVisibility(View.VISIBLE);
                inputLayoutYearOfBirth.setVisibility(View.VISIBLE);


                stringBufferPossibleAge = "";
                switch (checkedId) {

                    case R.id.radiobtnFirst: {
                        possibleBirthdays();
                        break;
                    }

                    case R.id.radiobtn2: {
                        possibleBirthdays();
                        break;
                    }
                }
            }
        });

    }

    public ArrayList<String> possibleAge(int month, int day, int year) {

        ArrayList<String> arrli = new ArrayList<String>();

        int start = 0;

        int end = 0;

        int startYear = year - 4;

        int endYear = year + 4;

        int end2 = 0;

        int end3 = 0;

        int pMonth = month;

        int pDay = day;

        int pYear = year;

        int[] possibleAge;

        if (day <= 24) {
            end = day + 4;
        }
        if (day >= 5) {
            start = day - 4;

        } else if (day == 4) {
            start = day - 3;
            end3 = 1;
        } else if (day == 3) {
            start = day - 2;
            end3 = 2;
        } else if (day == 2) {
            start = day - 1;
            end3 = 3;
        } else if (day == 1) {
            start = day;
            end3 = 4;
        } else if (((pYear % 400 == 0) || ((pYear % 4 == 0) &&
                (pYear % 100 != 0))) && pDay > 24 && pMonth == 1) // For leapyears
        {
            if (pDay == 25) {
                end = pDay + 4;
            } else if (pDay == 26) {
                end = pDay + 3;
                end2 = 1;
            } else if (pDay == 27) {
                end = pDay + 2;
                end2 = 2;
            } else if (pDay == 28) {
                end = pDay + 1;
                end2 = 3;
            } else if (pDay == 29) {
                end = pDay;
                end2 = 4;
            }
        }
        if (((pMonth == 10 || pMonth == 8 || pMonth == 5 ||
                pMonth == 3)) && pDay > 24) {
            if (pDay == 25 || pDay == 26) {
                end = pDay + 4;
            } else if (pDay == 27) {
                end = pDay + 3;
                end2 = 1;
            } else if (pDay == 28) {
                end = pDay + 2;
                end2 = 2;
            } else if (pDay == 29) {
                end = pDay + 1;
                end2 = 3;
            } else if (pDay == 30) {
                end = pDay;
                end2 = 4;
            }


        }
        if (((pMonth == 11 || pMonth == 9 || pMonth == 7 ||
                pMonth == 6 || pMonth == 4 || pMonth == 2 || pMonth == 0)) &&
                pDay > 24) {
            if (pDay == 25 || pDay == 26 || pDay == 27) {

                end = pDay + 4;

            } else if (pDay == 28) {
                end = pDay + 3;
                end2 = 1;

            } else if (pDay == 29) {
                end = pDay + 2;
                end2 = 2;

            } else if (pDay == 30) {
                end = pDay + 1;
                end2 = 3;

            } else if (pDay == 31) {
                end = pDay;
                end2 = 4;
            }
        }
      /*  addTextViewHeading3("Start = " + start + " End = " +
                end + " End2 = " + end2 + " End3 = " + end3);

        addTextViewHeading3(("pYear = " + pYear));
*/
        pYear = startYear;

        for (int k = startYear; k <= endYear; k++) {
            for (int x = start; x <= end; x++) {
                possibleAge = expectedBirth(pMonth + 1, x, pYear);

                arrli.add("" + new Integer(possibleAge[0]));

                arrli.add("" + new Integer(possibleAge[1]));

                arrli.add("" + new Integer(possibleAge[2]));
            }
            pYear++;
        }

        if (end2 > 0) {
            for (int k = startYear; k <= endYear; k++) {
                //faisal here was crash pMonth
                for (int x = start; x <= end2; x++) {
                    possibleAge = expectedBirth(pMonth + 1, x, pYear);
                    arrli.add("" + new Integer(possibleAge[0]));
                    arrli.add("" + new Integer(possibleAge[1]));
                    arrli.add("" + new Integer(possibleAge[2]));
                }
                pYear++;
            }

            //faisal here was crash pMonth
        } else if (end3 > 0) {
            for (int k = startYear; k <= endYear; k++) {
                for (int x = start; x <= end3; x++) {
                    possibleAge = expectedBirth(pMonth + 1, x, pYear);
                    arrli.add("" + new Integer(possibleAge[0]));
                    arrli.add("" + new Integer(possibleAge[1]));
                    arrli.add("" + new Integer(possibleAge[2]));
                }
                pYear++;
            }
        }

        // addTextViewHeading3("arrli size = " + arrli.size());
        return arrli;


    }

    public static int[] expectedBirth(int month, int day, int year) {
        int answer[] = new int[3];
        int answer2[] = new int[3];
        LocalDate today2 = LocalDate.now();
        LocalDate birthdayFormat = LocalDate.of((year % 7) + 7,
                month, day);
        Period p = Period.between(birthdayFormat, today2);
        int value = p.getYears();
        int value1 = value - 5;
        int value2 = value + 5;
        int ageNoYear = calculateAge(month - 1, day, value1, value2);
        int ageYear = p.getYears();
        int fixMonthUp = month;
        int expectedDayUp = day;
        int yearUp = year;
        int a = 0;
        int b = 0;
        if (ageNoYear == ageYear) {
            answer[0] = month;
            answer[1] = day;
            answer[2] = year;
            return answer;
        }
        while (ageNoYear != ageYear) {
            if (day == 0) {
                if (month == 11 || month == 9 || month == 8 ||
                        month == 6 || month == 4 || month == 2 || month == 1) {
                    day = 31;
                    if (month == 1) {
                        month = 13;
                        year--;
                    }
                    month--;
                }

// 30 days months
                else if (month == 12 || month == 10 || month == 7
                        || month == 5) {
                    day = 30;
                    month--;
                }


// February
                else if (((year % 400 == 0) || ((year % 4 == 0)
                        && (year % 100 != 0))) && month == 3) // For leap years
                {
                    day = 29;
                    month--;
                } else if (month == 3 && ((year % 400 == 0) || (year
                        % 4 == 0) && (year % 100 != 0)) == false) {
                    day = 28;
                    month--;
                }
            }
            ageNoYear = calculateAge(month, day, value1,
                    value2);
            if (ageNoYear == ageYear || (ageNoYear - 7) ==
                    ageYear) {
                answer[0] = month;
                answer[1] = day;
                answer[2] = year;
                break;
            }
            day--;
            a++;
        } // ends while


// check upward direction
        ageNoYear = calculateAge(fixMonthUp, expectedDayUp,
                value1, value2); // reset ageNoYear
        while (ageNoYear != ageYear) {
            ageNoYear = calculateAge(fixMonthUp, expectedDayUp,
                    value1, value2);
            if (ageNoYear == ageYear || (ageNoYear - 7) ==
                    ageYear) {

                answer2[0] = fixMonthUp;
                answer2[1] = expectedDayUp;
                answer2[2] = yearUp;
                break;
            }
            if (((yearUp % 400 == 0) || ((yearUp % 4 == 0) && (yearUp
                    % 100 != 0))) && expectedDayUp == 29 && month == 2) // For leap years

            {
                expectedDayUp = 0;
                fixMonthUp++;
            } else if (month == 2 && expectedDayUp == 28 && ((yearUp % 400 == 0) || (yearUp % 4 == 0) &&
                    (yearUp % 100 != 0)) == false) {
                expectedDayUp = 0;
                fixMonthUp++;
            } else if (((month == 11 || month == 9 || month == 6 ||
                    month == 4)) && expectedDayUp == 30) {
                expectedDayUp = 0;
                fixMonthUp++;
            } else if (((month == 12 || month == 10 || month == 8 ||
                    month == 7 || month == 5 || month == 3 || month == 1)) &&
                    expectedDayUp == 31) {
                expectedDayUp = 0;
                if (month == 12) {
                    fixMonthUp = 1;
                    yearUp++;
                } else {
                    fixMonthUp++;
                }
            }
            expectedDayUp++;
            b++;
        } // ends while
        if (b < a) {
            return answer2;
        }
        return answer;
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
                todayDayOfMonth)) {

            answer--;

        }


        return answer;


    }

    public static int getYear(int month, int day, int age) {
        int answer = -1;
        int thisYear = new Date().getYear() + 1900;
        answer = thisYear - age;
        Calendar today = Calendar.getInstance();
        int todayMonth = today.get(Calendar.MONTH) + 1; //Javamonths go from 0 to 11

        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);


        if ((month > todayMonth)) {

            answer--;


        } else if ((month == todayMonth) && (day >
                todayDayOfMonth)) {

            answer--;

        }

        return answer;


    }

    private void possibleBirthdays() {
        //integer.parse.valueof() ignore leading zero
        ArrayList<String> dataSet = possibleAge(globalClass.getMonthEntered(), globalClass.getDayEntered(),
                getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getAnswer()));
        // NumberFormat decimalFormat=new DecimalFormat("00");
        //set decimal format with 2 digit by faisal waris
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).length() == 1) {
                dataSet.set(i, "0" + dataSet.get(i));
            }
        }
        // addTextViewHeading3("value1 = " + value1 + " value2 = " + value2);

        if (globalClass.getGoodAnswer() >= globalClass.getValue1()) {
            ArrayList<String> dataSet2 =
                    possibleAge(globalClass.getMonthEntered(), globalClass.getDayEntered(),
                            getYear(globalClass.getMonthEntered(), globalClass.getDayEntered(), globalClass.getGoodAnswer()));

            for (int k = 0; k < dataSet2.size(); k++) {

                if (dataSet2.get(k).length() == 1) {
                    dataSet2.set(k, "0" + dataSet2.get(k));
                }
            }

            //filter array by faisal waris
            ArrayList<String> dataSet2ArraylistUniq = new ArrayList<>();
            for (int i = 0; i < dataSet2.size(); i++) {
                String birthAllYearsDisplay3 = dataSet2.get(i) + "/" + dataSet2.get(i + 1) +
                        "/" + dataSet2.get(i + 2);
                dataSet2ArraylistUniq.add(birthAllYearsDisplay3);

                i += 2;
            }
            Set<String> uniqList = new HashSet<String>(dataSet2ArraylistUniq);
            List<String> uniqListDataset2 = new ArrayList<String>(uniqList);

            Collections.sort(uniqListDataset2, new Comparator<String>() {
                DateFormat f = new SimpleDateFormat("MM/dd/yyyy");

                @Override
                public int compare(String o1, String o2) {
                    try {
                        return f.parse(o1).compareTo(f.parse(o2));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });

            //if two options age
            if (!globalClass.getSingleAgeNotYMD().equals("")) {
                for (int i = 0; i < uniqListDataset2.size(); i++) {
                    String birth = uniqListDataset2.get(i).toString();
                    if (birth.contains(globalClass.getAgeShouldbeTwoOption1()) || birth.contains(globalClass.getAgeShouldbeTwoOption2())) {
                        stringBufferPossibleAge += "\n\n" + uniqListDataset2.get(i);
                    }
                }
            } else {
                for (int i = 0; i < uniqListDataset2.size(); i++) {
                    stringBufferPossibleAge += "\n\n" + uniqListDataset2.get(i);
                }
            }
        }
        if (globalClass.getAnswer() <= globalClass.getValue2()) {/*
 addTextViewHeading3("Here dataSet size = " +
                            dataSet.size());*/


            //filter array by faisal waris
            ArrayList<String> dataSetArraylistUniq = new ArrayList<>();
            for (int i = 0; i < dataSet.size(); i++) {
                String birthAllYearsDisplay2 = dataSet.get(i) + "/" + dataSet.get(i + 1) +
                        "/" + dataSet.get(i + 2);
                dataSetArraylistUniq.add(birthAllYearsDisplay2);
                i += 2;
            }
            Set<String> uniqList = new HashSet<String>(dataSetArraylistUniq);
            List<String> uniqListDataset2 = new ArrayList<String>(uniqList);

            Collections.sort(uniqListDataset2, new Comparator<String>() {
                DateFormat f = new SimpleDateFormat("MM/dd/yyyy");

                @Override
                public int compare(String o1, String o2) {
                    try {
                        return f.parse(o1).compareTo(f.parse(o2));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });


            //if two options age
            if (!globalClass.getSingleAgeNotYMD().equals("")) {
                for (int i = 0; i < uniqListDataset2.size(); i++) {
                    String birth = uniqListDataset2.get(i).toString();
                    if (birth.contains(globalClass.getAgeShouldbeTwoOption1()) || birth.contains(globalClass.getAgeShouldbeTwoOption2())) {

                        stringBufferPossibleAge += "\n\n" + uniqListDataset2.get(i);
                    }
                }
            } else {
                for (int i = 0; i < uniqListDataset2.size(); i++) {

                    stringBufferPossibleAge += "\n\n" + uniqListDataset2.get(i);
                }
            }


        }

        //  nestedScrollView.fullScroll(View.FOCUS_DOWN);
        TextView tvPossibleBirthdayText = findViewById(R.id.tv_actual_possiblebirthday);
        if (stringBufferPossibleAge.equals("")) {
            stringBufferPossibleAge = "No Possible Birthday Combination ";
        }
        tvPossibleBirthdayText.setText("" + stringBufferPossibleAge);
        // nestedScrollView.fullScroll(View.FOCUS_DOWN);

        //frameLayout.setVisibility(View.VISIBLE);
    }

    public static ArrayList<Integer> possibleAgeFromTheCode(int month, int day, int year) {
        ArrayList<Integer> arrli = new ArrayList<Integer>();
        int start = 0;
        int end = 0;
        int startYear = year - 4;
        int endYear = year + 4;
        int end2 = 0;
        int end3 = 0;
        int pMonth = month;
        int pDay = day;
        int pYear = year;
        int[] possibleAge;

        if (day <= 24) {
            end = day + 4;
        }

        if (day >= 5) {
            start = day - 4;
        } else if (day == 4) {
            start = day - 3;

            end3 = 1;
        } else if (day == 3) {
            start = day - 2;

            end3 = 2;
        } else if (day == 2) {
            start = day - 1;

            end3 = 3;
        } else if (day == 1) {
            start = day;

            end3 = 4;
        } else if (((pYear % 400 == 0) || ((pYear % 4 == 0) && (pYear % 100 != 0))) && pDay > 24 && pMonth == 1)  // For leap years
        {
            if (pDay == 25) {

                end = pDay + 4;
            } else if (pDay == 26) {
                end = pDay + 3;

                end2 = 1;
            } else if (pDay == 27) {
                end = pDay + 2;

                end2 = 2;
            } else if (pDay == 28) {
                end = pDay + 1;

                end2 = 3;
            } else if (pDay == 29) {
                end = pDay;

                end2 = 4;
            }
        }

        if (((pMonth == 10 || pMonth == 8 || pMonth == 5 || pMonth == 3)) && pDay > 24) {

            if (pDay == 25 || pDay == 26) {
                end = pDay + 4;
            } else if (pDay == 27) {
                end = pDay + 3;

                end2 = 1;
            } else if (pDay == 28) {
                end = pDay + 2;

                end2 = 2;
            } else if (pDay == 29) {
                end = pDay + 1;

                end2 = 3;
            } else if (pDay == 30) {
                end = pDay;

                end2 = 4;
            }
        }

        if (((pMonth == 11 || pMonth == 9 || pMonth == 7 || pMonth == 6 || pMonth == 4 || pMonth == 2 || pMonth == 0)) && pDay > 24) {

            if (pDay == 25 || pDay == 26 || pDay == 27) {
                end = pDay + 4;
            } else if (pDay == 28) {
                end = pDay + 3;

                end2 = 1;
            } else if (pDay == 29) {
                end = pDay + 2;

                end2 = 2;
            } else if (pDay == 30) {
                end = pDay + 1;

                end2 = 3;
            } else if (pDay == 31) {
                end = pDay;

                end2 = 4;
            }

        }
        System.out.println("Start = " + start + " End = " + end + " End2 = " + end2 + " End3 = " + end3);
        System.out.println(("pYear = " + pYear));
        pYear = startYear;

        for (int k = startYear; k <= endYear; k++) {

            for (int x = start; x <= end; x++) {
                possibleAge = expectedBirth(pMonth + 1, x, pYear);
                arrli.add(new Integer(possibleAge[0]));
                arrli.add(new Integer(possibleAge[1]));
                arrli.add(new Integer(possibleAge[2]));

            }

            pYear++;

        }

        if (end2 > 0) {

            for (int k = startYear; k <= endYear; k++) {

                for (int x = start; x <= end2; x++) {
                    possibleAge = expectedBirth(pMonth, x, pYear);
                    arrli.add(new Integer(possibleAge[0]));
                    arrli.add(new Integer(possibleAge[1]));
                    arrli.add(new Integer(possibleAge[2]));

                }

                pYear++;

            }
        } else if (end3 > 0) {

            for (int k = startYear; k <= endYear; k++) {

                for (int x = start; x <= end3; x++) {
                    possibleAge = expectedBirth(pMonth, x, pYear);
                    arrli.add(new Integer(possibleAge[0]));
                    arrli.add(new Integer(possibleAge[1]));
                    arrli.add(new Integer(possibleAge[2]));

                }

                pYear++;

            }
        }

        System.out.println("arrli size = " + arrli.size());

        return arrli;

    }
}
