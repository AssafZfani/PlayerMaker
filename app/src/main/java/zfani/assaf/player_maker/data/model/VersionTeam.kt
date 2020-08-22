package zfani.assaf.player_maker.data.model

import android.os.Parcel
import android.os.Parcelable

data class VersionTeam(
    var teamId: String,
    var majorVersion: String,
    var fotaEnabled: Boolean,
    var version: String,
    var binFile: String,
    var jsonFile: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(teamId)
        parcel.writeString(majorVersion)
        parcel.writeByte(if (fotaEnabled) 1 else 0)
        parcel.writeString(version)
        parcel.writeString(binFile)
        parcel.writeString(jsonFile)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VersionTeam> {
        override fun createFromParcel(parcel: Parcel): VersionTeam {
            return VersionTeam(parcel)
        }

        override fun newArray(size: Int): Array<VersionTeam?> {
            return arrayOfNulls(size)
        }
    }
}