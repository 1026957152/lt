package com.lt.dom.otcenum;

public enum EnumProductCategory {
    Base_Products("barcode"),
    Add_On_Services("Add On Services"),
    Miscellaneous_Products("Barcode scan"),



    ;


  //  Base Products: The main list of products that you offer.
  //  Add On Services: This can include setup or consulting fees.
  //  Miscellaneous Products: This can include delinquent payment charges.

    EnumProductCategory(String barcode) {

    }
}
