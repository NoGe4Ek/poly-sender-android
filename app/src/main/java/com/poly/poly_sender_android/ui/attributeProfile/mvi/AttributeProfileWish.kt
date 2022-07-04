package com.poly.poly_sender_android.ui.attributeProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.Wish

sealed interface AttributeProfileWish : Wish {
    data class SetAttribute(val attribute: Attribute) : AttributeProfileWish
    data class DeleteAttribute(val attribute: Attribute) : AttributeProfileWish
}
