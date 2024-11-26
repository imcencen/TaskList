package paba.belajar.tasklist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var nama : String,
    var tanggal : String,
    var desc : String
): Parcelable
