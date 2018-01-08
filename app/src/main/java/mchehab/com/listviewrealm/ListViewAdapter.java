package mchehab.com.listviewrealm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import mchehab.com.listviewrealm.databinding.RowListviewBinding;

/**
 * Created by muhammadchehab on 1/6/18.
 */

public class ListViewAdapter extends RealmBaseAdapter {

    private Activity activity;
    private OrderedRealmCollection<Person> realmCollection;

    public ListViewAdapter(Activity activity, OrderedRealmCollection<Person> realmCollection){
        super(realmCollection);
        this.realmCollection = realmCollection;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowListviewBinding rowListviewBinding;
        if(convertView == null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.row_listview, null);
            rowListviewBinding = DataBindingUtil.bind(convertView);
            convertView.setTag(rowListviewBinding);
        }else{
            rowListviewBinding = (RowListviewBinding)convertView.getTag();
        }
        rowListviewBinding.setPerson(realmCollection.get(position));
        return rowListviewBinding.getRoot();
    }
}