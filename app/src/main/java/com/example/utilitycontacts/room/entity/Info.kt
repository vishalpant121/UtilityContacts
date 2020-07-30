package com.example.utilitycontacts.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class Info(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "address")
    var address: String?,
    @ColumnInfo(name = "phone")
    var phone: String?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "from")
    var from: String?,
    @ColumnInfo(name = "to")
    var to: String?,
    @ColumnInfo(name = "occ")
    var occ: String?,
    @ColumnInfo(name = "alt_phone")
    var altPhone: String?,
    @ColumnInfo(name = "loc")
    var loc: String? = null

)