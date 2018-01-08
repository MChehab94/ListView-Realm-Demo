package mchehab.com.kotlinapp

import io.realm.PersonRealmProxy
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

/**
 * Created by muhammadchehab on 1/7/18.
 */
@Parcel(implementations = arrayOf(PersonRealmProxy::class),
        value = Parcel.Serialization.BEAN,
        analyze = arrayOf(Person::class))
@RealmClass
open class Person @ParcelConstructor
constructor(@ParcelProperty("firstName") var firstName: String,
            @ParcelProperty("lastName") var lastName: String,
            @ParcelProperty("age") var age: Int) : RealmObject() {

    constructor() : this("", "", 0)

    fun copy(person: Person){
        firstName = person.firstName
        lastName = person.lastName
        age = person.age
    }
}