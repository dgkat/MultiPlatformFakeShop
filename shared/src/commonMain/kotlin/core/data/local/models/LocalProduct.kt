package core.data.local.models

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class LocalProduct : RealmObject {
    @PrimaryKey
    var _id: Int = 0
    var category: String = ""
    var description: String = ""
    var image: String = ""
    var price: Double = 0.0
    //TODO init rating?
    var rating: LocalProductRating? = null
    var name: String = ""
    var productType: String = ""
}

class LocalProductRating : EmbeddedRealmObject {
    var count: Int = 0
    var rate: Double = 0.0
}