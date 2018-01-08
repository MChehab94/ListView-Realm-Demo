package mchehab.com.kotlinapp

import android.app.Activity
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import mchehab.com.kotlinapp.databinding.RowListviewBinding

/**
 * Created by muhammadchehab on 1/7/18.
 */
class ListViewAdapter(private val activity: Activity, private val realmCollection: OrderedRealmCollection<Person>) :
        RealmBaseAdapter<Person>(realmCollection) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val rowListviewBinding: RowListviewBinding
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.row_listview, null)
            rowListviewBinding = DataBindingUtil.bind<RowListviewBinding>(convertView)
            convertView!!.tag = rowListviewBinding
        } else {
            rowListviewBinding = convertView.tag as RowListviewBinding
        }
        rowListviewBinding.setPerson(realmCollection[position])
        return rowListviewBinding.getRoot()
    }
}