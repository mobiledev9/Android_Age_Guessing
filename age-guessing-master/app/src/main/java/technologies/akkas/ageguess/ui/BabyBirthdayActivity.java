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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.temporal.ChronoUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.GlobalClass;

import static technologies.akkas.ageguess.ui.YouAreActivity.expectedBirth;

public class BabyBirthdayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BabyBirthdayActivity.class.getSimpleName();
    FloatingActionButton floatingActionButtonNext;
    GlobalClass globalVariable;
    TextInputLayout inputLayoutYourAge;
    TextInputLayout inputLayoutEnterMonth;
    TextInputLayout inputLayoutEnterDay;
    TextInputEditText inputAge;
    TextInputEditText inputMonth;
    TextInputEditText inputDay;
    TextView tvTitleGuess;
    ImageView imgLogo;
    private ImageView imgShare;
    private ConstraintLayout guessPersonAgeLayout;
    private ScrollView youAreLayout;
    public static int guessingAge = -1; //added a


    //you are layout views

    FloatingActionButton floatingActionButton;
    TextView tvYouAre;
    CardView cardYear;
    CardView cardMonth;
    CardView cardDays;
    CardView cardCorrectBirthday;
    TextView tvIsTheInformationOk;
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
    public static ArrayList<Integer> dataSet=new
            ArrayList<Integer>();

    public static ArrayList<Integer> dataSet2 = new
            ArrayList<Integer>();

    boolean isRefreshIconShow = false;

    public static int birthYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_birth_day);

        init();
        initView();
    }

    private void init() {
        globalVariable = (GlobalClass) getApplicationContext();
        inputAge = findViewById(R.id.input_age);
        inputDay = findViewById(R.id.input_day);
        inputMonth = findViewById(R.id.input_month);
        tvTitleGuess = findViewById(R.id.tv_guess_title);
        floatingActionButtonNext = findViewById(R.id.floating_askuser_age);
        imgLogo = findViewById(R.id.img_logo);
        inputLayoutYourAge = findViewById(R.id.inputLayoutYourAge);
        inputLayoutEnterMonth = findViewById(R.id.inputlayoutMonth);
        inputLayoutEnterDay = findViewById(R.id.inputlayoutDay);
        imgShare = findViewById(R.id.img_share);

        //you are class views

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
        tvYouAre = findViewById(R.id.tv_youare);
        cardYear = findViewById(R.id.card_year);
        cardMonth = findViewById(R.id.card_month);
        cardDays = findViewById(R.id.card_days);
        cardCorrectBirthday = findViewById(R.id.card_correct_birthday);
        tvIsTheInformationOk = findViewById(R.id.tv_isthe_information_ok);
        inputLayoutPressYorN = findViewById(R.id.inputlayout_y_n);
        floatingActionButton = findViewById(R.id.floating_youare_next);
        tvNoYMD = findViewById(R.id.tv_noYMD);

        ifTheUserEntersNo();

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.shareApp(BabyBirthdayActivity.this);
            }
        });

        floatingActionButtonNext.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    private void ifTheUserEntersNo() {
        inputEditTextYorN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                {

                    if (s.toString().equals("n")) {


                        isRefreshIconShow = false;
                        floatingActionButton.setImageResource(R.drawable.ic_next_arrow);

                        stringBufferPossibleAge = "";
                        floatingActionButton.setImageResource(R.drawable.ic_next_arrow);
                        //for not radio options
                        if (globalVariable.getSingleAgeNotYMD().equals("")) {
                            cardRecyclerPossibleBirthdays.setVisibility(View.VISIBLE);
                            inputLayoutYearOfBirth.setVisibility(View.VISIBLE);
                            cardTwoPossiblities.setVisibility(View.GONE);
                            possibleBirthdays();
                        } else {
                            cardRecyclerPossibleBirthdays.setVisibility(View.GONE);
                            inputLayoutYearOfBirth.setVisibility(View.GONE);
                            cardTwoPossiblities.setVisibility(View.VISIBLE);
                            papulateRadioGroupData();
                        }

                    } else {
                        cardRecyclerPossibleBirthdays.setVisibility(View.GONE);
                        inputEditTextYearOfBirth.setVisibility(View.GONE);
                        cardTwoPossiblities.setVisibility(View.GONE);
                        isRefreshIconShow = true;
                        floatingActionButton.setImageResource(R.drawable.ic_refresh);

                    }
                }
            }
        });
    }

    private void papulateRadioGroupData() {

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton radioBtnFirst = findViewById(R.id.radiobtnFirst);
        final RadioButton radioBtnSecond = findViewById(R.id.radiobtn2);

        LocalDate todayNoYear = LocalDate.now();
        String[] splitAgeOption1 = globalVariable.getAgeShouldbeTwoOption1().split("/");
        LocalDate birthdayFormat2 = LocalDate.of(Integer.parseInt(splitAgeOption1[2]), Integer.parseInt(splitAgeOption1[0]),
                Integer.parseInt(splitAgeOption1[1]));

        Period pNoYear = Period.between(birthdayFormat2, todayNoYear);


        String[] splitAgeOption2 = globalVariable.getAgeShouldbeTwoOption2().split("/");
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


    private void possibleBirthdays() {
        //integer.parse.valueof() ignore leading zero
        /*ArrayList<String> dataSet = possibleAge(globalVariable.getMonthEntered(), globalVariable.getDayEntered(),
                getYear(globalVariable.getMonthEntered(), globalVariable.getDayEntered(), globalVariable.getAnswer()));*/

        ArrayList<Integer> dataSet = possibleAge(globalVariable.getMonthEntered(), globalVariable.getDayEntered(),
                getYear(globalVariable.getMonthEntered(), globalVariable.getDayEntered(), globalVariable.getAnswer()));

        // NumberFormat decimalFormat=new DecimalFormat("00");
        //set decimal format with 2 digit by faisal waris
        for (int i = 0; i < dataSet.size(); i++) {
            /*if (dataSet.get(i).length() == 1) {
                dataSet.set(i, "0" + dataSet.get(i));
            }*/

            dataSet.set(i,  dataSet.get(i));
        }
        // addTextViewHeading3("value1 = " + value1 + " value2 = " + value2);

        if (globalVariable.getGoodAnswer() >= globalVariable.getValue1()) {
            /*ArrayList<String> dataSet2 =
                    possibleAge(globalVariable.getMonthEntered(), globalVariable.getDayEntered(),
                            getYear(globalVariable.getMonthEntered(), globalVariable.getDayEntered(), globalVariable.getGoodAnswer()));*/

            ArrayList<Integer> dataSet2 =
                    possibleAge(globalVariable.getMonthEntered(), globalVariable.getDayEntered(),
                            getYear(globalVariable.getMonthEntered(), globalVariable.getDayEntered(), globalVariable.getGoodAnswer()));

            for (int k = 0; k < dataSet2.size(); k++) {
                dataSet2.set(k,dataSet2.get(k));
                /*if (dataSet2.get(k).length() == 1) {

                }*/
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
            if (!globalVariable.getSingleAgeNotYMD().equals("")) {
                for (int i = 0; i < uniqListDataset2.size(); i++) {
                    String birth = uniqListDataset2.get(i).toString();
                    if (birth.contains(globalVariable.getAgeShouldbeTwoOption1()) || birth.contains(globalVariable.getAgeShouldbeTwoOption2())) {
                        stringBufferPossibleAge += "\n\n" + uniqListDataset2.get(i);
                    }
                }
            } else {
                for (int i = 0; i < uniqListDataset2.size(); i++) {
                    stringBufferPossibleAge += "\n\n" + uniqListDataset2.get(i);
                }
            }
        }
        if (globalVariable.getAnswer() <= globalVariable.getValue2()) {

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
            if (!globalVariable.getSingleAgeNotYMD().equals("")) {
                for (int i = 0; i < uniqListDataset2.size(); i++) {
                    String birth = uniqListDataset2.get(i).toString();
                    if (birth.contains(globalVariable.getAgeShouldbeTwoOption1()) || birth.contains(globalVariable.getAgeShouldbeTwoOption2())) {

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

    public ArrayList<String> possibleAge_(int month, int day, int year) {

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


    public static ArrayList<Integer> possibleAge(int month, int day, int year)
 {
         ArrayList<Integer> arrli = new
                ArrayList<Integer>();
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

         if (day <= 24)
             {
             end = day + 4;
             }

         if (day >= 5)
             {
             start = day - 4;
             }

         else if (day == 4)
             {
             start = day - 3;

             end3 = 1;
             }

         else if (day == 3)
             {
             start = day - 2;

             end3 = 2;
             }

         else if (day == 2)
             {
             start = day - 1;

             end3 = 3;
             }

         else if (day == 1)
             {
             start = day;

             end3 = 4;
             }

         else if (isLeapYear(pYear) && pDay > 24 &&
                pMonth == 1)
             // For leap years
         {
             if (pDay == 25)
                 {

                 end = pDay + 4;
                 }
             else if (pDay == 26)
                 {
                 end = pDay + 3;

                 end2 = 1;
                 }

             else if (pDay == 27)
                 {
                 end = pDay + 2;

                 end2 = 2;
                 }

             else if (pDay == 28)
                 {
                 end = pDay + 1;

                 end2 = 3;
                 }

             else if (pDay == 29)
                 {
                 end = pDay;

                 end2 = 4;
                 }
             }

         if (((pMonth == 10 || pMonth == 8 || pMonth
                == 5 || pMonth == 3)) && pDay > 24)
             {

             if (pDay == 25 || pDay == 26)
                 {
                 end = pDay + 4;
                 }

             else if (pDay == 27)
                 {
                 end = pDay + 3;

                 end2 = 1;
                 }

             else if (pDay == 28)
                 {
                 end = pDay + 2;

                 end2 = 2;
                 }

             else if (pDay == 29)
                 {
                 end = pDay + 1;

                 end2 = 3;
                 }

             else if (pDay == 30)
                 {
                 end = pDay;

                 end2 = 4;
                 }
             }

         if (((pMonth == 11 || pMonth == 9 || pMonth
                == 7 || pMonth == 6 || pMonth == 4 || pMonth == 2 ||
                pMonth == 0)) && pDay > 24)
         if (((pMonth == 11 || pMonth == 9 || pMonth
                == 7 || pMonth == 6 || pMonth == 4 || pMonth == 2 ||
                pMonth == 0)) && pDay > 24)
         {

             if (pDay == 25 || pDay == 26 || pDay ==
                    27)
                 {
                 end = pDay + 4;
                 }


             else if (pDay == 28)
                 {
                 end = pDay + 3;

                 end2 = 1;
                 }

             else if (pDay == 29)
                 {
                 end = pDay + 2;

                 end2 = 2;
                 }

             else if (pDay == 30)
                 {
                 end = pDay + 1;

                 end2 = 3;

                 }

             else if (pDay == 31)
                 {
                 end = pDay;

                 end2 = 4;
                 }

             }

         pYear = startYear;

         for (int k = startYear; k <= endYear; k++)
             {

             for (int x = start; x <= end; x++)
                 {
                 possibleAge = expectedBirth(pMonth + 1, x,
                        pYear);
                 int[] test = expectedBirth(possibleAge[0],
                        possibleAge[1], possibleAge[2]);

                 Calendar birthday =
                        Calendar.getInstance();
                 birthday.set(test[2], test[0], test[1]);

                 LocalDate today = LocalDate.now();
                 LocalDate birthdayFormat =
                        LocalDate.of(test[2], test[0], test[1]);

                 Period p =
                        Period.between(birthdayFormat, today);

                 int ageYear = p.getYears();

                 if (guessingAge >= ageYear - 4 &&
                        guessingAge <= ageYear + 4) {

                     arrli.add(new Integer (test[0]));
                     arrli.add(new Integer (test[1]));
                     arrli.add(new Integer (test[2]));

                     }

                 }

             pYear++;

             }

         if (end2 > 0)
             {

             for (int k = startYear; k <= endYear; k++)
                 {

                 for (int x = start; x <= end2; x++)
                 {
                     possibleAge = expectedBirth(pMonth + 1,
                            x, pYear);
                     int[] test2 =
                            expectedBirth(possibleAge[0], possibleAge[1],
                                    possibleAge[2]);

                     Calendar birthday =
                            Calendar.getInstance();
                     birthday.set(test2[2], test2[0],
                            test2[1]);

                     LocalDate today = LocalDate.now();
                     LocalDate birthdayFormat =
                            LocalDate.of(possibleAge[2], possibleAge[0],
                                    possibleAge[1]);

                     Period p =
                            Period.between(birthdayFormat, today);

                     int ageYear = p.getYears();

                     if (guessingAge >= ageYear - 4 &&
                            guessingAge <= ageYear + 4) {

                         arrli.add(new Integer (test2[0]));
                         arrli.add(new Integer (test2[1]));
                         arrli.add(new Integer (test2[2]));

                         }

                     }

                 pYear++;

                 }
             }

         else if (end3 > 0)
             {

             for (int k = startYear; k <= endYear; k++)
                 {

                 for (int x = start; x <= end3; x++)
                     {
                     possibleAge = expectedBirth(pMonth + 1,
                            x, pYear);
                     int[] test3 =
                            expectedBirth(possibleAge[0], possibleAge[1],
                                    possibleAge[2]);

                     Calendar birthday =
                            Calendar.getInstance();
                     birthday.set(test3[2], test3[0],
                            test3[1]);

                     LocalDate today = LocalDate.now();
                     LocalDate birthdayFormat =
                            LocalDate.of(test3[2], test3[0], test3[1]);

                     Period p =
                            Period.between(birthdayFormat, today);

                     int ageYear = p.getYears();

                     if (guessingAge >= ageYear - 4 &&
                            guessingAge <= ageYear + 4) {

                         arrli.add(new Integer (test3[0]));
                         arrli.add(new Integer (test3[1]));
                         arrli.add(new Integer (test3[2]));

                         }

                     }

                 pYear++;

                 }

             }

         return removeDuplicates(arrli);

         }


    public static boolean isLeapYear(int year) {
         if (year % 4 != 0) {
             return false;
             } else if (year % 400 == 0) {
             return true;
             } else if (year % 100 == 0) {
             return false;
             } else {
             return true;
            }
   }

    public static ArrayList<Integer>  removeDuplicates(ArrayList<Integer> list)
 {

         ArrayList<String> oldlist = new
                ArrayList<>();
         int count=0;

         for(int i=0;i<list.size()/3;i++)
             {
             int val1=(int) list.get(count);
             int val2=(int) list.get(++count);
             int val3=(int) list.get(++count);
             count++;
             String dateString=(String)
                    (val1+"/"+val2+"/"+val3);

             oldlist.add(dateString);
             }

         // Create a new ArrayList
         ArrayList<String> newList = new
                ArrayList<>();
         ArrayList<Integer> arl = new ArrayList<>();

         // Traverse through the first list
         for (String element : oldlist) {


             if (!newList.contains(element)) {
                 newList.add(element);
                 }
             }

         for (int i=0;i<newList.size();i++)
             {
             String firstDate=newList.get(i);

             String [] token=firstDate.split("/");


             arl.add(Integer.valueOf(token[0]));
             arl.add(Integer.valueOf(token[1]));
             arl.add(Integer.valueOf(token[2]));

             }

         // return the new list
         return arl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_askuser_age: {
                checkFields();
            }
            case R.id.floating_youare_next: {

                if (inputEditTextYorN.getText().toString().trim().length() == 0) {
                    inputEditTextYorN.setError("Please fill the required field first");
                    return;
                }
                if (isRefreshIconShow) {
                    Intent intent = new Intent(BabyBirthdayActivity.this, WelcomeActivity.class);
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
                }
                break;
            }

        }
    }

    private void yearBithAfterNProcedure() {
        String yearPrev = inputEditTextYearOfBirth.getText().toString();

        try {
            int birthYear = Integer.parseInt(yearPrev);
            String birthYearString = yearPrev.toString();

            int ageYear = 0;

            int[] birth = expectedBirth(globalVariable.getMonthEntered() + 1, globalVariable.getDayEntered(), birthYear);
            Calendar birthday = Calendar.getInstance();
            birthday.set(birth[2], birth[0], birth[1]);
            LocalDate today = LocalDate.now();

            LocalDate birthdayFormat = LocalDate.of(birth[2], birth[0], birth[1]);

            Period p = Period.between(birthdayFormat, today);
            long p2 = ChronoUnit.DAYS.between(birthdayFormat, today);
            ageYear = p.getYears();
            int oldDay2 = globalVariable.getDayEntered();
            int year = birthYear;
            String oldDate2 = "" + (globalVariable.getMonthEntered() + 1) + "/" +
                    (oldDay2) + "/" + (birthYearString);

            String birthDisplay;

            globalVariable.setYouAreYMDEndScreen("You are " + p.getYears() +
                    " years, " + p.getMonths() + " months, and " + p.getDays() + " days old");
            globalVariable.setDaysTotalEndScreen("(" + p2 + " Days Total)");

            birthDisplay = "\nThe Birthdate should be: " + birth[0]
                    + "/" + birth[1] + "/" + birth[2];
            globalVariable.setTheCorrectAgeAndShouldbeisEndScreenTop("The Correct age is: " + ageYear + "" + birthDisplay);
            ;
            Intent intent = new Intent(BabyBirthdayActivity.this, ExpectedBirthdayActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
            // edtMonth.setError("Please enter valid year");
            return;
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
                            onCompleteInitialInput();
                        }
                    }
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onCompleteInitialInput() {
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
            int month = Integer.parseInt(inputMonth.getText().toString());
            globalVariable.setMonthEntered(month - 1);
            // monthEntered = month;

            int day = Integer.parseInt(inputDay.getText().toString());
            globalVariable.setDayEntered(day);
            calculate(globalVariable.getMonthEntered(), globalVariable.getDayEntered(), globalVariable.getValue1(), globalVariable.getValue2());

            guessPersonAgeLayout.setVisibility(View.GONE);
            youAreLayout.setVisibility(View.VISIBLE);

            papulateData();
//            Intent intent = new Intent(this, YouAreActivity.class);
//            startActivity(intent);
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void papulateData() {
        if (globalVariable.getSingleAgeNotYMD().equals("")) {
            linearLayoutWithYMD.setVisibility(View.VISIBLE);
            linearLayoutNoYMD.setVisibility(View.GONE);
            tvYear.setText("" + globalVariable.getYearYouAre());
            tvMonth.setText("" + globalVariable.getMonthYouAre());
            tvDays.setText("" + globalVariable.getDaysYouAre());
        } else {
            linearLayoutWithYMD.setVisibility(View.GONE);
            linearLayoutNoYMD.setVisibility(View.VISIBLE);
            tvNoYMD.setText("" + globalVariable.getSingleAgeNotYMD());
        }

        tvCorrectAgeShouldActual.setText("" + globalVariable.getCorrectAgeShouldBe());
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
        int todayMonth = today.get(Calendar.MONTH) + 1; //Java months go from 0 to 11

        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);

        if ((month > todayMonth)) {
            answer--;
        } else if ((month == todayMonth) && (day > todayDayOfMonth)) {
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
        globalVariable.setGoodAnswer(globalVariable.getAnswer() - whattoSub);
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

        LocalDate birthdayFormat3 = LocalDate.of(getYear(month + 1, day, globalVariable.getAnswer()), month + 1, day);

        Period pNoYear2 = Period.between(birthdayFormat3,
                todayNoYear2);

        long p2NoYear2 =
                ChronoUnit.DAYS.between(birthdayFormat3, todayNoYear2);

        if ((globalVariable.getAnswer() <= value2) && (globalVariable.getAnswer() >= value1)) {


            if (pNoYear.getYears() == 0) {

                globalVariable.setYearYouAre("" + pNoYear.getYears());
                globalVariable.setMonthYouAre("" + pNoYear.getMonths());
                globalVariable.setDaysYouAre("" + pNoYear.getDays());
                globalVariable.setSingleAgeNotYMD("");

                globalVariable.setAgeShouldbeTwoOption1("" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer()));
                globalVariable.setAgeShouldbeTwoOption2("" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

                globalVariable.setCorrectAgeShouldBe("    " + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer())
                        + "\nor " +
                        (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

            } else {

                // i have to change it
                globalVariable.setSingleAgeNotYMD("Your age is: " +
                        (globalVariable.getGoodAnswer()) + " or " + globalVariable.getAnswer());

                globalVariable.setAgeShouldbeTwoOption1("" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer()));
                globalVariable.setAgeShouldbeTwoOption2("" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));
                globalVariable.setCorrectAgeShouldBe("    " + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getGoodAnswer())
                        + "\nor " + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

            }

        } else if (globalVariable.getAnswer() <= value2) {
            globalVariable.setAgeShouldbeTwoOption1("");
            globalVariable.setAgeShouldbeTwoOption2("");
            globalVariable.setYearYouAre("" + pNoYear2.getYears());
            globalVariable.setMonthYouAre("" + pNoYear2.getMonths());
            globalVariable.setDaysYouAre("" + pNoYear2.getDays());
            globalVariable.setSingleAgeNotYMD("");


            //   globalVariable.setStringBufferguessAge(globalVariable.getStringBufferguessAge() + "You are " + pNoYear2.getYears() + " years, " + pNoYear2.getMonths() + "months, and " + pNoYear2.getDays() + " days old. (" + p2NoYear2 + " days total)");

            globalVariable.setCorrectAgeShouldBe("" + (month + 1) + "/" + day + "/" + getYear(month + 1, day, globalVariable.getAnswer()));

        } else if (globalVariable.getGoodAnswer() >= value1) {


            globalVariable.setAgeShouldbeTwoOption1("");
            globalVariable.setAgeShouldbeTwoOption2("");
            globalVariable.setYearYouAre("" + pNoYear.getYears());
            globalVariable.setMonthYouAre("" + pNoYear.getMonths());
            globalVariable.setDaysYouAre("" + pNoYear.getDays());
            globalVariable.setSingleAgeNotYMD("");

            globalVariable.setCorrectAgeShouldBe("" + (month + 1) + "/" + day + "/" + getYear(month
                    + 1, day, globalVariable.getGoodAnswer()));

        }


    }


    /*public static void calculate(int month, int day, int value1,int value2)
 {
         int GoodAnswer = 0;

         int whattoSub = 7;

         int answer = calculateAge(month + 1,
                day, value1, value2);

         GoodAnswer = answer - whattoSub;

         int[] testAge = expectedBirth(month + 1,
                day, getYear(month + 1, day, guessingAge));

         Calendar birthdayNoYear =
                Calendar.getInstance();
         birthdayNoYear.set(testAge[2],
                testAge[0], testAge[1]);

         LocalDate todayNoYear = LocalDate.now();
         LocalDate birthdayFormat2 =
                LocalDate.of(testAge[2], testAge[0], testAge[1]);

         Period pNoYear =
                Period.between(birthdayFormat2, todayNoYear);

         Calendar birthdayNoYear3 =
                Calendar.getInstance();
         birthdayNoYear3.set(getYear(month + 1,
                day, answer), month + 1, day);

         LocalDate todayNoYear3 =
                LocalDate.now();
         LocalDate birthdayFormat3 =
                LocalDate.of(getYear(month + 1, day, answer), month
                        + 1, day );


         Period pNoYear3 =
                Period.between(birthdayFormat3, todayNoYear3);

         Calendar birthdayNoYear4 =
                Calendar.getInstance();
         birthdayNoYear4.set(getYear(month + 1,
                day, GoodAnswer), month + 1, day);

         LocalDate todayNoYear4 =
                LocalDate.now();
         LocalDate birthdayFormat4 =
                LocalDate.of(getYear(month + 1, day, GoodAnswer),
                        month + 1, day );

         Period pNoYear4 =
                Period.between(birthdayFormat4, todayNoYear4);

         if (guessingAge <= 7)
             {

             System.out.println("The age should be: " + pNoYear.getYears() + " years, " +
                    pNoYear.getMonths() + " months, and " +
                            pNoYear.getDays() + " days old.");
             System.out.println();
             System.out.println("The birthdate should be: " + testAge[0] + "/" + testAge[1] + "/" +
            testAge[2]);

             }

         // a = answer b = GoodAnswer and c =

         else if (Math.abs(guessingAge - answer)
                < Math.abs(guessingAge - GoodAnswer))
             {
             System.out.println("The age should be: " + pNoYear3.getYears() + " years, " +
            pNoYear3.getMonths() + " months, and " +
                    pNoYear3.getDays() + " days old.");
             System.out.println();
             System.out.println("The birthdate should be: " + (month + 1) + "/" + day + "/" +
            getYear(month + 1, day, answer ));

             }

         else
         {
             System.out.println("The age should be: " + pNoYear4.getYears() + " years, " +
            pNoYear4.getMonths() + " months, and " +
                    pNoYear4.getDays() + " days old.");
             System.out.println();
             System.out.println("The birthdate should be: " + (month + 1) + "/" + day + "/" +
            getYear(month + 1, day, GoodAnswer ));

             }

         //ask

         String userConfirmationString = null;
         System.out.println();
         System.out.println("Is the information  ok? (y/n)");

                 try{
             BufferedReader bufferRead = new
                    BufferedReader(new InputStreamReader(System.in));
             String s = bufferRead.readLine();
             userConfirmationString = s;

             }
         catch(IOException e)
         {
             e.printStackTrace();
             }

         if (userConfirmationString.equals("n"))
             {

             dataSet = possibleAge(month, day,
                    getYear(month, day, guessingAge));

             if (GoodAnswer >= value1)

             {
                 dataSet2 = possibleAge(month,
                        day, getYear(month, day, guessingAge));

                 for (int i=0; i<dataSet2.size();
                         i++)
                     {


                     String birthAllYearsDisplay3 =
                            "Possible birthdates: " + dataSet2.get(i) + "/" +
                                    dataSet2.get(i+1) + "/" + dataSet2.get(i+2);

                     i += 2;



                    System.out.println(birthAllYearsDisplay3);

                     }
                 }

             if (answer <= value2)
                 {
                 for (int i=0; i<dataSet.size(); i++)
                     {
                     String birthAllYearsDisplay2 =
                            "Possible birthdates: " + dataSet.get(i) + "/" +
                                    dataSet.get(i+1) + "/" + dataSet.get(i+2);

                     i += 2;
                    System.out.println(birthAllYearsDisplay2);

                     }
                 }

             String birthYearString = null;

             System.out.println();

             System.out.println("Please choose a year of birth from the list:");

             try{
                 BufferedReader bufferRead = new
                        BufferedReader(new InputStreamReader(System.in));
                 String s =
                        bufferRead.readLine();
                 birthYearString = s;

                 System.out.println("You Entered: " + s);
                         }
             catch(IOException e)

             {
                 e.printStackTrace();
                 }

             try {
                 birthYear = Integer.parseInt(birthYearString);
                 }
             catch (NumberFormatException e)
             {
                 System.out.println("Please try a valid input. You entered: " + birthYear);
                 }

             int oldDay2 = day;
             int year = birthYear;

             String oldDate2 = "" + (month+1) +
                    "/" + (oldDay2) + "/" + getYear(month, day,
                    guessingAge);
             String birthDisplay;


             int[] selectedBirth = new int[3];

             if (dataSet.size() > 0) {

                 selectedBirth = selectAge(dataSet, month+1,
                        day, getYear(month,day,guessingAge));

                 selectedBirth = selectAge(dataSet, month+1,
                        day, getYear(month,day,guessingAge));

                 }

             else {

                 selectedBirth = selectAge(dataSet2,
                        month+1, day, getYear(month,day,guessingAge));

                 }


             Calendar birthdaySelected =
                    Calendar.getInstance();
             birthdaySelected.set(selectedBirth[2],
                    selectedBirth[1], selectedBirth[0]);

             LocalDate selectedToday = LocalDate.now();

             LocalDate birthdayFormatSelected =
                    LocalDate.of(selectedBirth[2], selectedBirth[0],
                            selectedBirth[1]);

             Period pSelected =
                    Period.between(birthdayFormatSelected,
                            selectedToday);

             long p2Selected =
                    ChronoUnit.DAYS.between(birthdayFormatSelected,
                            selectedToday);

                    ChronoUnit.DAYS.between(birthdayFormatSelected,
                            selectedToday);

             birthDisplay = " The birthdate should be: " + selectedBirth[0] + "/" + selectedBirth[1] + "/"
                    + selectedBirth[2];

             System.out.println("The age should be: " +
                            pSelected.getYears() + " years, " +
                            pSelected.getMonths() +
                             " months, and " + pSelected.getDays()
                            +
                             " days old. (" + p2Selected + " days total)");


             System.out.println("The age should be: " +
                            pSelected.getYears() + birthDisplay + " The birthdate you entered was: " + oldDate2);


             System.out.println("Please enter expected birthday: ");


             String monthString = null;
             String dayString = null;
             String yearString = null;

             System.out.println("Enter Month:");

             try{
                 BufferedReader bufferRead = new
                        BufferedReader(new InputStreamReader(System.in));
                 String s = bufferRead.readLine();
                 monthString = s;

                 System.out.println("You Entered: " + s);
                 }
             catch(IOException e)
             {
                 e.printStackTrace();
                 }

             try {
                 month = Integer.parseInt(monthString);
                 }
             catch (NumberFormatException e)
             {
                 System.out.println("try a valid input monthString");
                         }

             System.out.println();

             System.out.println("Enter Day:");

             try{
                 BufferedReader bufferRead = new
                        BufferedReader(new InputStreamReader(System.in));
                 String s = bufferRead.readLine();
                 dayString = s;

                 System.out.println("You Entered: " + s);
                 }
             catch(IOException e)
             {
                 e.printStackTrace();
                 }

             try {
                 day = Integer.parseInt(dayString);
                 }
             catch (NumberFormatException e)
             {
                 System.out.println("Please try a valid input. You entered: " + dayString);
                 }

             System.out.println();

             System.out.println("Birthday entered: " +month + "/" + day);
             System.out.println("Birthday entered: " +
                    month + "/" + day);

             System.out.println();

             System.out.println("Enter Expected Year:");

             try{
                 BufferedReader bufferRead = new
                        BufferedReader(new InputStreamReader(System.in));
                 String s = bufferRead.readLine();
                 yearString = s;

                 System.out.println("You Entered: " + s);
                 }
             catch(IOException e)
             {
                 e.printStackTrace();
                 }

             try {
                 year = Integer.parseInt(yearString);
                 }
             catch (NumberFormatException e)
             {
                 System.out.println("Please try a valid input. You entered: " + dayString);
                 }

             System.out.println();
             System.out.println("You entered: " + month +
                    "/" + day + "/" + year);

             int[] birthAllYears = expectedBirth(month, day, year);
             String birthAllYearsDisplay = "The birthdate should be: " + birthAllYears[0] + "/" +
             birthAllYears[1] + "/" + birthAllYears[2];
             System.out.println(birthAllYearsDisplay);

             }

         }
*/

    public static int getYear(int month, int day, int age) {
        int answer = -1;
        int thisYear = new Date().getYear() + 1900;
        answer = thisYear - age;
        Calendar today = Calendar.getInstance();
        int todayMonth = today.get(Calendar.MONTH) + 1; //Javamonths go from 0 to 11

        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);

        if ((month > todayMonth)) {
            answer--;
        } else if ((month == todayMonth) && (day > todayDayOfMonth)) {
            answer--;
        }

        return answer;
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            AnimationOnLogo();
            SlideInputLayout();
        }
    }*/

    private void SlideInputLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transalate_fromx);
        tvTitleGuess.startAnimation(animation);
        inputLayoutYourAge.startAnimation(animation);
        inputLayoutEnterMonth.startAnimation(animation);
        inputLayoutEnterDay.startAnimation(animation);
        floatingActionButtonNext.startAnimation(animation);

    }

    private void AnimationOnLogo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        imgLogo.startAnimation(animation);
    }

    @Override
    public void onResume() {
        super.onResume();
        AnimationOnLogo();
        SlideInputLayout();
    }

    private void initView() {
        guessPersonAgeLayout = findViewById(R.id.guess_person_age_layout);
        youAreLayout = findViewById(R.id.you_are_layout);
    }
}

