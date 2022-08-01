package com.lt.dom.otcenum;

public enum EnumInvitationStatus {

    pending("pending have not received any response from the recipient"),
    accepted("accepted the recipient has accepted the invitation"),
    tentative("tentative the recipient has tentatively accepted the invitation"),
    declined("declined the recipient has declined the invitation"),
    ;

    EnumInvitationStatus(String s) {

    }
}
