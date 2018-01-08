package mchehab.com.listviewrealm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import io.realm.Realm;
import io.realm.RealmResults;
import mchehab.com.listviewrealm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Realm realm;
    private RealmResults<Person> realmResultsPerson;

    private Person personSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        realmResultsPerson = getList();
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, realmResultsPerson);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.listView.setAdapter(listViewAdapter);
        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {
            personSelected = realmResultsPerson.get(i);
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);
            bundle.putParcelable("person", Parcels.wrap(personSelected));
            startActivity(bundle);
        });

        binding.fab.setOnClickListener(e -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", false);
            startActivity(bundle);
        });
    }

    private void startActivity(Bundle bundle){
        Intent intent = new Intent(this, EditPersonActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                boolean isEdit = bundle.getBoolean("edit");
                Person person = Parcels.unwrap(data.getExtras().getParcelable("person"));
                if(isEdit){
                    realm.executeTransaction(realm1 -> personSelected.copyPerson(person));
                }else{
                    insertPerson(person);
                }
            }
        }
    }

    private void insertPerson(Person person){
        realm.beginTransaction();
        realm.insert(person);
        realm.commitTransaction();
    }

    private RealmResults<Person> getList(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Person.class).findAll();
    }
}
