package mchehab.com.kotlinapp

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mchehab.com.kotlinapp.databinding.ActivityEditPersonBinding
import org.parceler.Parcels

class EditPersonActivity : AppCompatActivity() {

    private var person: Person? = null
    private var isEdit = false
    private var binding: ActivityEditPersonBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)

        binding = DataBindingUtil.setContentView<ActivityEditPersonBinding>(this, R.layout.activity_edit_person)
        binding!!.activity = this
        binding!!.converter = Converter()
        checkBundle()
    }

    private fun checkBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            isEdit = bundle.getBoolean("edit")
            if (isEdit) {
                person = Parcels.unwrap<Person>(bundle.getParcelable("person"))
                binding!!.button.text = "Save"
            } else {
                person = Person()
                binding!!.button.text = "Add"
            }
            binding!!.person = person
        } else {
            finish()
        }
    }

    fun onButtonClick(view: View) {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putParcelable("person", Parcels.wrap<Person>(person))
        bundle.putBoolean("edit", isEdit)
        intent.putExtras(bundle)
        setResult(RESULT_OK, intent)
        finish()
    }
}