package technologies.akkas.ageguess;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.splunk.mint.Mint;
import com.stripe.android.PaymentConfiguration;

public class GlobalClass extends Application {

    int value1;
    int value2;
    int monthEntered;
    int dayEntered;
    int GoodAnswer;
    int answer;
    String ageShouldbeTwoOption1 = "";
    String ageShouldbeTwoOption2 = "";
    String yearYouAre="";
    String monthYouAre="";
    String daysYouAre="";
    String correctAgeShouldBe="";
    String SingleAgeNotYMD="";
    String youAreYMDEndScreen="";
    String daysTotalEndScreen="";
    String theCorrectAgeAndShouldbeisEndScreenTop="";
    int guessingAge;
    String  birthGuessDate;



    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51IFKirFot7cN26EcQIwsjS5q8nkSGL2JbRe3agj3OlEICRS2XznruwHnxD6mRveyrz96peEzVk72W6JokVj98C3X00Z3jD2hP7"
        );

        Mint.initAndStartSession(this, "bab8cf6a");
    }


    public String getBirthGuessDate() {
        return birthGuessDate;
    }

    public void setBirthGuessDate(String birthGuessDate) {
        this.birthGuessDate = birthGuessDate;
    }

    public int getGuessingAge() {
        return guessingAge;
    }

    public void setGuessingAge(int guessingAge) {
        this.guessingAge = guessingAge;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getMonthEntered() {
        return monthEntered;
    }

    public void setMonthEntered(int monthEntered) {
        this.monthEntered = monthEntered;
    }

    public int getDayEntered() {
        return dayEntered;
    }

    public void setDayEntered(int dayEntered) {
        this.dayEntered = dayEntered;
    }

    public int getGoodAnswer() {
        return GoodAnswer;
    }

    public void setGoodAnswer(int goodAnswer) {
        GoodAnswer = goodAnswer;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getAgeShouldbeTwoOption1() {
        return ageShouldbeTwoOption1;
    }

    public void setAgeShouldbeTwoOption1(String ageShouldbeTwoOption1) {
        this.ageShouldbeTwoOption1 = ageShouldbeTwoOption1;
    }

    public String getAgeShouldbeTwoOption2() {
        return ageShouldbeTwoOption2;
    }

    public void setAgeShouldbeTwoOption2(String ageShouldbeTwoOption2) {
        this.ageShouldbeTwoOption2 = ageShouldbeTwoOption2;
    }

    public String getYearYouAre() {
        return yearYouAre;
    }

    public void setYearYouAre(String yearYouAre) {
        this.yearYouAre = yearYouAre;
    }

    public String getMonthYouAre() {
        return monthYouAre;
    }

    public void setMonthYouAre(String monthYouAre) {
        this.monthYouAre = monthYouAre;
    }

    public String getDaysYouAre() {
        return daysYouAre;
    }

    public void setDaysYouAre(String daysYouAre) {
        this.daysYouAre = daysYouAre;
    }

    public String getCorrectAgeShouldBe() {
        return correctAgeShouldBe;
    }

    public void setCorrectAgeShouldBe(String correctAgeShouldBe) {
        this.correctAgeShouldBe = correctAgeShouldBe;
    }

    public String getSingleAgeNotYMD() {
        return SingleAgeNotYMD;
    }

    public void setSingleAgeNotYMD(String singleAgeNotYMD) {
        SingleAgeNotYMD = singleAgeNotYMD;
    }

    public String getYouAreYMDEndScreen() {
        return youAreYMDEndScreen;
    }

    public void setYouAreYMDEndScreen(String youAreYMDEndScreen) {
        this.youAreYMDEndScreen = youAreYMDEndScreen;
    }

    public String getDaysTotalEndScreen() {
        return daysTotalEndScreen;
    }

    public void setDaysTotalEndScreen(String daysTotalEndScreen) {
        this.daysTotalEndScreen = daysTotalEndScreen;
    }

    public String getTheCorrectAgeAndShouldbeisEndScreenTop() {
        return theCorrectAgeAndShouldbeisEndScreenTop;
    }

    public void setTheCorrectAgeAndShouldbeisEndScreenTop(String theCorrectAgeAndShouldbeisEndScreenTop) {
        this.theCorrectAgeAndShouldbeisEndScreenTop = theCorrectAgeAndShouldbeisEndScreenTop;
    }

    public static void shareApp(Context context){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Check out the App at: https://play.google.com/store/apps/details?id="+context.getPackageName());
        context.startActivity(intent);

    }
}
