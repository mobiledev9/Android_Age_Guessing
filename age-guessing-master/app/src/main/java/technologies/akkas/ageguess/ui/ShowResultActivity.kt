package technologies.akkas.ageguess.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_show_result.*
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import technologies.akkas.ageguess.GlobalClass
import technologies.akkas.ageguess.R
import java.text.SimpleDateFormat
import java.util.*

class ShowResultActivity : BaseActivity(), View.OnClickListener {

    private lateinit var expected: String
    private var days = 0
    var months = 0
    var dates = ""
    var shareText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        expected = intent.getStringExtra("birthAllYearsDisplay")
        tv_card_month.text = intent.getStringExtra("months")

        days = intent.getStringExtra("days").toInt()
        months = intent.getStringExtra("months").toInt()
        dates = intent.getStringExtra("dates")

        days -= (months * 30)


        var startDate = LocalDate.parse(intent.getStringExtra("dates"), DateTimeFormatter.ofPattern("M/d/yyyy"))

        val period: Period = Period.between(LocalDate.now(), startDate)

        Log.d("VVV", "${period.months} Months ----------- \"${period.days}"
        )


        if (days < 0 && months == 0) {
            tv_card_days.text = days.toString()

            val now = Calendar.getInstance()
            now.add(Calendar.DAY_OF_MONTH, days.toString().toInt())
            val df = SimpleDateFormat("MM/dd/yyyy")
            println(df.format(now.time))
            days *= -1
            expected_tv.text = "You entered\n ${intent.getStringExtra("enteredDate")}\n$days days have passed since your baby's birthday "+df.format(now.time)
        } else {
//            days *= -1
            expected_tv.text = "You entered\n" + " ${intent.getStringExtra("enteredDate")}\n$expected"
            tv_card_days.text = days.toString()
        }

        tv_card_month.text = period.months.toString()
        tv_card_days.text = period.days.toString()

        shareText = "$months Months and $days Days left in your baby's birthday.\nExpected birthday should be $dates\n\nShare the app with your friends\n\nhttps://play.google.com/store/apps/details?id=technologies.akkas.ageguess"

        img_share.setOnClickListener {
            GlobalClass.shareApp(this@ShowResultActivity)
        }
        card_final_birthday.setOnClickListener(this)
        floating_refresh.setOnClickListener(this)

        imageView4.setOnClickListener {
            startActivity(Intent(this, ExpectedBirthdayActivity::class.java))
            finish()
        }

        imageView6.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_share -> {
                GlobalClass.shareApp(this)
            }
            R.id.card_final_birthday -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, shareText)
                startActivity(intent)
            }
            R.id.floating_refresh -> {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }
    }
}
