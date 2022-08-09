package technologies.akkas.ageguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.Temporal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import technologies.akkas.ageguess.GlobalClass;
import technologies.akkas.ageguess.R;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;

public class ExpectedBirthdayActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ExpectedBirthdayActivit";

    FloatingActionButton floatingRefresh;
    ImageView imgLogo;
    TextView tvTotalAgeTop;
    TextView tvTotalDays;
    TextView tvCorrectAge;
    TextView tvEnterExpectedTitle;
    TextInputLayout inputLayoutMonth;
    TextInputLayout inputLayoutDay;
    TextInputLayout inputLayoutExpectedYear;
    GlobalClass globalClass;
    TextInputEditText inputMonth;
    TextInputEditText inputDay;
    TextInputEditText inputExpectedYear;
    String birthAllYearsDisplay;

    private ImageView imgShare;
    private int months;
    private long days;
    private String dates;
    boolean isBefore = false;

    Temporal enteredTemporal;
    Temporal currentTemporal;

    Date date;
    int enteredDay = 0, enteredMonth = 0, enteredYear = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expected_birthday_acitivity);
        init();
    }

    private void init() {

        globalClass = (GlobalClass) getApplicationContext();
        imgLogo = findViewById(R.id.imgLogo);
        tvTotalAgeTop = findViewById(R.id.tv_totalage_top);
        tvTotalDays = findViewById(R.id.tv_days_total);
        tvCorrectAge = findViewById(R.id.tv_correctage);
        tvEnterExpectedTitle = findViewById(R.id.tv_enter_expected_birthday);
        inputLayoutMonth = findViewById(R.id.inputlayout_month);
        inputLayoutDay = findViewById(R.id.inputlayoutday);
        inputLayoutExpectedYear = findViewById(R.id.inputlayout_expectedyear);
        floatingRefresh = findViewById(R.id.floating_refresh);
        inputDay = findViewById(R.id.inputDay);
        inputMonth = findViewById(R.id.inputMonth);
        inputExpectedYear = findViewById(R.id.inputExpectedYear);

        imgShare = findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.shareApp(ExpectedBirthdayActivity.this);
            }
        });
        papulateTopData();
        floatingRefresh.setOnClickListener(this);

        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpectedBirthdayActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        findViewById(R.id.power_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpectedBirthdayActivity.this, WelcomeActivity.class));
                finish();
            }
        });
    }

    private void papulateTopData() {
        tvTotalAgeTop.setText("" + globalClass.getYouAreYMDEndScreen());
        tvTotalDays.setText("" + globalClass.getDaysTotalEndScreen());
        tvCorrectAge.setText("" + globalClass.getTheCorrectAgeAndShouldbeisEndScreenTop());

        inputMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputReady();
            }
        });
        inputDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputReady();

            }
        });
        inputExpectedYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputReady();

            }
        });
    }

    private void inputReady() {
        String day;
        if (Objects.requireNonNull(inputDay.getText()).toString().trim().length() == 0) {
            day = "1";
        } else {
            day = inputDay.getText().toString();
        }
        if (Objects.requireNonNull(inputMonth.getText()).toString().trim().length() > 0) {
            if (Objects.requireNonNull(inputExpectedYear.getText()).toString().trim().length() > 0) {
                textChangedAction(day, inputMonth.getText().toString(), inputExpectedYear.getText().toString());
            }
        }
    }

    private void textChangedAction(String dayValue, String monthValue, String yearValue) {
        try {
            int month = Integer.parseInt(monthValue);
            int day = Integer.parseInt(dayValue);
            int yearExpected = Integer.parseInt(yearValue);
            if (month <= 0 || month >= 13) {
                inputMonth.setError(getString(R.string.entermonth_between));
                return;
            }
            if (day <= 0 || day >= 32) {
                inputDay.setError(getString(R.string.enter_day_between));
            } else {
                expectedYearAfterN(day, month, yearExpected);
            }
        } catch (Exception e) {
            Toast.makeText(ExpectedBirthdayActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void expectedYearAfterN(int day, int month, int yearExpected) {

        enteredDay = day;
        enteredMonth = month;
        enteredYear = yearExpected;

        int[] birthAllYears = AgeGuru.expectedBirth(month, day, yearExpected); // The expected date algorithm
        Date c = Calendar.getInstance().getTime();
        String dtStart = birthAllYears[2] + "-" + birthAllYears[0] + "-" + birthAllYears[1];
//        String dtStart = yearExpected + "-" + month + "-" + day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {

            date = format.parse(dtStart);
            getDaysDifference(c, date);
            months = monthsBetween(c, date);
            dates = birthAllYears[0] + "/" + birthAllYears[1] + "/" + birthAllYears[2];
            birthAllYearsDisplay = "The expected birthday should be " + birthAllYears[0] + "/" + birthAllYears[1] + "/" + birthAllYears[2]; /*+ ") " + "(" + getDaysDifference(c, date) + ") days left.";*/

            Temporal temporal = LocalDate.now();
            Temporal temporal1 = LocalDate.of(birthAllYears[2], birthAllYears[0], birthAllYears[1]);
            days = DAYS.between(temporal, temporal1);
            isBefore = date.before(c);
            enteredTemporal = LocalDate.of(enteredYear, enteredMonth, enteredDay);
            currentTemporal = LocalDate.now();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // plus days to a date
    public static int monthsBetween(Date d1, Date d2) {
        if (d2 == null || d1 == null) {
            return -1;//Error
        }
        Calendar m_calendar = Calendar.getInstance();
        m_calendar.setTime(d1);
        int nMonth1 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        m_calendar.setTime(d2);
        int nMonth2 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        return java.lang.Math.abs(nMonth2 - nMonth1);
    }

    public static int getDaysDifference(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;
        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationOnLogo();
        SlideInputLayout();

    }

    private void AnimationOnLogo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        imgLogo.startAnimation(animation);
    }

    private void SlideInputLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transalate_fromx);
        tvTotalAgeTop.startAnimation(animation);
        tvTotalDays.startAnimation(animation);
        tvCorrectAge.startAnimation(animation);
        tvEnterExpectedTitle.startAnimation(animation);
        inputLayoutMonth.startAnimation(animation);
        inputLayoutDay.startAnimation(animation);
        inputLayoutExpectedYear.startAnimation(animation);
        floatingRefresh.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_refresh: {
                Log.d(TAG, "onClick: " + birthAllYearsDisplay);

                try{
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH)+1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    String str_date= mDay +"-"+mMonth+"-"+mYear;
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = (Date)formatter.parse(str_date);
                    System.out.println("SHUBHAM--Today is " +date.getTime());

                    long todayDate = date.getTime();


                    final Calendar c2 = Calendar.getInstance();
                    c2.add(Calendar.MONTH, 9);
                    int mYear2 = c2.get(Calendar.YEAR);
                    int mMonth2 = c2.get(Calendar.MONTH)+1;
                    int mDay2 = c2.get(Calendar.DAY_OF_MONTH);

                    String nine_date= mDay2 +"-"+mMonth2+"-"+mYear2;
                    DateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
                    Date date2 = (Date)formatter2.parse(nine_date);
                    System.out.println("SHUBHAM--After is " +date2.getTime());

                    long afterNneMonthDate = date2.getTime();


                    String user_date= inputDay.getText().toString() +"-"+inputMonth.getText().toString()+"-"+inputExpectedYear.getText().toString();
                    DateFormat formatter3 = new SimpleDateFormat("dd-MM-yyyy");
                    Date date3 = (Date)formatter3.parse(user_date);
                    System.out.println("SHUBHAM--test is " +date3.getTime());

                    long userENterDate = date3.getTime();

                    if(userENterDate > afterNneMonthDate){
                        Toast.makeText(globalClass, "Please enter a date less than 9 months", Toast.LENGTH_SHORT).show();
                        return;
                    }


                }catch (Exception e){

                }



                if (birthAllYearsDisplay != null)
                    if (DAYS.between(currentTemporal, enteredTemporal) <= -1 /*|| DAYS.between(currentTemporal, enteredTemporal) == 0*/) {
                        Toast.makeText(this, "Enter a future date", Toast.LENGTH_SHORT).show();
                    } else if (months <= 9) {
                        if (onAdsLoaded()) {
                            showAds(() -> showResult());
                        } else {
                            showResult();
                        }
                    } else {
                        Toast.makeText(this, "Please enter a date less than 9 months", Toast.LENGTH_SHORT).show();
                    }
                break;
            }
            case R.id.tv_finalbirthday: {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "" + birthAllYearsDisplay);
                startActivity(intent);
                break;
            }
        }
    }

    private void showResult() {
        Intent intent = new Intent(ExpectedBirthdayActivity.this, ShowResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("birthAllYearsDisplay", birthAllYearsDisplay);
        intent.putExtra("enteredDate", inputMonth.getText().toString() + "/" + inputDay.getText().toString() + "/" + inputExpectedYear.getText().toString());
        intent.putExtra("months", months + "");
        intent.putExtra("days", days + "");
        intent.putExtra("dates", dates + "");
        startActivity(intent);
        finish();
    }
}
