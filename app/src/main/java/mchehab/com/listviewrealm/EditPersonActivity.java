package mchehab.com.listviewrealm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.parceler.Parcels;

import mchehab.com.listviewrealm.databinding.ActivityEditPersonBinding;

public class EditPersonActivity extends AppCompatActivity {

    private Person person;
    private boolean isEdit = false;
    private ActivityEditPersonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_person);
        binding.setActivity(this);
        checkBundle();
    }

    private void checkBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isEdit = bundle.getBoolean("edit");
            if(isEdit){
                person = Parcels.unwrap(bundle.getParcelable("person"));
                binding.button.setText("Save");
            }else{
                person = new Person();
                binding.button.setText("Add");
            }
            binding.setPerson(person);
        }else{
            finish();
        }
    }

    public void onButtonClick(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("person", Parcels.wrap(person));
        bundle.putBoolean("edit", isEdit);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}