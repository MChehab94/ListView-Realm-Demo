package mchehab.com.kotlinapp

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmResults
import mchehab.com.kotlinapp.databinding.ActivityMainBinding
import org.parceler.Parcels

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var realm: Realm? = null
    private var realmResultsPerson: RealmResults<Person>? = null

    private var personSelected: Person? = null

    private val list: RealmResults<Person>
        get() {
            val realm = Realm.getDefaultInstance()
            return realm.where(Person::class.java).findAll()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(applicationContext)
        realm = Realm.getDefaultInstance()
        realmResultsPerson = list
        val listViewAdapter = ListViewAdapter(this, realmResultsPerson!!)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding!!.listView.adapter = listViewAdapter
        binding!!.listView.setOnItemClickListener({ _, _, i, _ ->
            personSelected = realmResultsPerson!![i]
            val bundle = Bundle()
            bundle.putBoolean("edit", true)
            bundle.putParcelable("person", Parcels.wrap<Person>(personSelected))
            startActivity(bundle)
        })

        binding!!.fab.setOnClickListener{
            val bundle = Bundle()
            bundle.putBoolean("edit", false)
            startActivity(bundle)
        }
    }

    private fun startActivity(bundle: Bundle) {
        val intent = Intent(this, EditPersonActivity::class.java)
        intent.putExtras(bundle)
        startActivityForResult(intent, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm!!.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            val bundle = data?.extras
            if (bundle != null) {
                val isEdit = bundle.getBoolean("edit")
                val person = Parcels.unwrap<Person>(data.extras!!.getParcelable("person"))
                if (isEdit) {
                    realm!!.executeTransaction {
                        personSelected!!.copy(person)
                    }
                } else {
                    insertPerson(person)
                }
            }
        }
    }

    private fun insertPerson(person: Person?) {
        realm!!.beginTransaction()
        realm!!.insert(person!!)
        realm!!.commitTransaction()
    }
}