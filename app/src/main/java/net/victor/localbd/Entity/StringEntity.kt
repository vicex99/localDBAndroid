package net.victor.localbd.Entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "task")
class StringEntity(

    @PrimaryKey(autoGenerate = false)
    var document: String,

    var name: String
)