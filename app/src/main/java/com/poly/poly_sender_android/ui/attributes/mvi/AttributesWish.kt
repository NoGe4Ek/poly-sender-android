package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish

sealed interface AttributesWish: Wish {
    object RefreshSections: AttributesWish
    data class RefreshAttributes(val selectedSearchSection: Section?, val query: String): AttributesWish
    data class RefreshSelectedSection(val section: Section?) : AttributesWish
}
