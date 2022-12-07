package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumIdentityLastError {


    consent_declined,
    //The user declined to be verified by Stripe. Check with your legal counsel to see if you have an obligation to offer an alternative, non-biometric means to verify, such as through a manual review.

            device_not_supported,
   // The user’s device didn’t have a camera or they declined to grant Stripe permission to access it.

    abandoned,
    //The user began the verification but didn’t submit it.

            under_supported_age,
    //Stripe does not verify users under the age of 16.

    country_not_supported,
    //Stripe does not verify users from the provided country.

            document_expired,
    //The provided identity document has expired.

    document_unverified_other,
    //Stripe couldn’t verify the provided identity document. See list of supported document types.

    document_type_not_supported,
    //The provided identity document isn’t one of the session’s allowed document types.

    selfie_document_missing_photo,
    //The provided identity document didn’t contain a picture of a face.

            selfie_face_mismatch,
    //The captured face image didn’t match with the document’s face.

    selfie_unverified_other,
    //Stripe couldn’t verify the provided selfie.

            selfie_manipulated,
    //The captured face image was manipulated.

    id_number_unverified_other,
    //The information provided couldn’t be verified. See list of supported ID numbers.

    id_number_insufficient_document_data,
    //The provided document didn’t contain enough data to match against the ID number.

    id_number_mismatch,





    ;


    EnumIdentityLastError() {

    }

    public static List List() {
        return  Arrays.stream(EnumIdentityLastError.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
}
