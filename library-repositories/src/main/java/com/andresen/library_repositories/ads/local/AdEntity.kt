package com.andresen.library_repositories.ads.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Entity(tableName = "ads")
data class AdEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val price: Int?,
    @ColumnInfo val imageUrl: String?,
    @ColumnInfo val isFavourite: Boolean,
    @ColumnInfo val location: String?
) : Parcelable

