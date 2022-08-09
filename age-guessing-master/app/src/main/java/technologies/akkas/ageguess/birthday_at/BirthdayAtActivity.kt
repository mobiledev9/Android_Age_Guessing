package technologies.akkas.ageguess.birthday_at

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import technologies.akkas.ageguess.R
import technologies.akkas.ageguess.ui.WelcomeActivity
import technologies.akkas.ageguess.databinding.ActivityBirthdayAtBinding

class BirthdayAtActivity : AppCompatActivity() {

    private lateinit var viewModel: BirthdayAtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BirthdayAtViewModel::class.java)

        val binding =
                DataBindingUtil.setContentView<ActivityBirthdayAtBinding>(this,
                        R.layout.activity_birthday_at
                )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.change.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        })
    }
}
