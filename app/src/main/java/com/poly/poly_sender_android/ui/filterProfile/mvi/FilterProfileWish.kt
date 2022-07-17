package com.poly.poly_sender_android.ui.filterProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.mvi.Wish

sealed interface FilterProfileWish : Wish {
    data class SetFilter(val filter: Filter) : FilterProfileWish
    data class DeleteFilter(val filter: Filter) : FilterProfileWish
    data class GetEmails(val filter: Filter) : FilterProfileWish
}
