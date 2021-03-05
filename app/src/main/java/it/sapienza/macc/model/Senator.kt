package it.sapienza.macc.model

import android.net.Uri

class Senator {

    var name: String?
    var lastname: String?
    var id: String
    var role: String?
    var profilePictureUri: Uri?

    constructor(name: String?, lastname: String?, id: String, role: String, profilePictureUri: Uri?) {
        this.name = name
        this.lastname = lastname
        this.id = id
        this.role = role
        this.profilePictureUri = profilePictureUri
    }


}