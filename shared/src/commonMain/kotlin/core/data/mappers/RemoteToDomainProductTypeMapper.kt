package core.data.mappers

import core.domain.models.ProductType

class RemoteToDomainProductTypeMapper {
    fun map(remoteProductType: String): ProductType {
        return when (remoteProductType) {
            "LAPTOPS" -> ProductType.LAPTOPS
            "PHONES" -> ProductType.PHONES
            "TABLETS" -> ProductType.TABLETS
            else -> {
                ProductType.MISC
            }
        }
    }
}