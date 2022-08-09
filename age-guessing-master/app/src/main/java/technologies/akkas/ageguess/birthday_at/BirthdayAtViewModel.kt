package technologies.akkas.ageguess.birthday_at

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import technologies.akkas.ageguess.R
import technologies.akkas.ageguess.GlobalClass
import java.util.*
import java.util.Calendar.getInstance


class BirthdayAtViewModel : ViewModel() {
    val newDate = DateFormat.format("yyyy-MM-dd", getInstance())

    var birthday = MutableLiveData<String>("---   ")
    var dateAt = MutableLiveData<String>(newDate.toString())

    var years = MutableLiveData<String>("0")
    var months = MutableLiveData<String>("0 Months")
    var remainingDays = MutableLiveData<String>("0 Days")

    var totalMonths = MutableLiveData<String>("0")
    var weeks = MutableLiveData<String>("0")
    var days = MutableLiveData<String>("0")

    var change = MutableLiveData<Boolean>(false)

    fun showDatePicker(view: View) {
        val newCalendar = getInstance()
        val dialog = DatePickerDialog(view.context, AlertDialog.THEME_HOLO_LIGHT, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val newDate = getInstance()
            newDate[year, month] = dayOfMonth

            if (view.id == R.id.dob_tv)
                birthday.value = DateFormat.format("yyyy-MM-dd", newDate).toString()
            else
                dateAt.value = DateFormat.format("yyyy-MM-dd", newDate).toString()

            getDifference(view)
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))

        dialog.show()
        val negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negative.setBackgroundColor(Color.parseColor("#F5F5F5"))
        negative.stateListAnimator = null

        val positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positive.setBackgroundColor(Color.parseColor("#F5F5F5"))
        positive.stateListAnimator = null
    }

    private fun getDifference(view: View) {
        val validDate = LocalDate.parse(birthday.value)
        val currentDate: LocalDate = LocalDate.parse(dateAt.value)
        if (currentDate.isAfter(validDate)) {

            val f: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US)

            val startDate: LocalDate = LocalDate.parse(birthday.value, f)
            val endDate: LocalDate = LocalDate.parse(dateAt.value, f)
            val period: Period = Period.between(startDate, endDate)

            years.value = period.years.toString()
            months.value = "${period.months} Months"
            remainingDays.value = "${period.days} Days"

            totalMonths.value = "${period.toTotalMonths()}"
            weeks.value = "${ChronoUnit.WEEKS.between(startDate, endDate)}"
            days.value = "${ChronoUnit.DAYS.between(startDate, endDate)}"
        } else {
            Toast.makeText(view.context, "Starting date should be earlier", Toast.LENGTH_SHORT).show()
            remainingDays.value = "-"
            years.value = "-"
            months.value = "-"
            totalMonths.value = "-"
            weeks.value = "-"
            days.value = "-"
        }
    }

    fun goBack(view: View) {
        change.value = true
    }

    fun restart(view: View) {
        change.value = true
    }

    fun share(view: View) {
        GlobalClass.shareApp(view.context)
    }
}