package com.lt.dom.otcenum;

public enum EnumCardPersonalized {
    NonPersonalized("Created"),
    Personalized("Ordered"),

/*    Created
    On card creation, the fulfillment status is set as created
            Ordered
    When embossing request for the card is generated, the status is updated as ordered
            Printed
    When the card is printed the fulfillment status is set as printed
    Dispatched
    When the card is dispatched for delivery then the status is set as dispatched
    Delivered
    This status is set when card is delivered to the destination
    */
/*    active
    The card can approve authorizations.

            inactive
    The card will decline authorizations with the card_inactive reason.

            canceled
    The card will decline authorization, and no authorization object will be recorded. This status is permanent.
   */

    ;
    public String name;

    EnumCardPersonalized(String name) {

        this.name = name;
    }

/*
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;


    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.status."
                + this.name());
        return displayStatusString;
    }*/

}
