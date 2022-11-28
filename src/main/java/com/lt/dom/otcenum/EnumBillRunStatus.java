package com.lt.dom.otcenum;

public enum EnumBillRunStatus {
    Pending("Pending"),
    Paused("Paused"),
    Processing("Processing"),
    Completed("Processing"),
    Error("Error"),
    Canceled("Canceled"),

    Posted("Posted"),


    ;
 /*   Pending	Immediately after a bill run is executed, the status changes to pending. Pending means that a bill run is being queued up for processing.
    Paused	If a scheduled bill run in Pending status is stopped temporarily, its status will be changed to Paused. When you resume the Paused bill run, its status will be changed to Pending or Cancel In Progress depending on whether it has the next run.
    Processing	The bill run is processing all accounts for that bill run.
            Completed
    For successful bill runs, the status is completed. Only invoices for accounts that are due to be billed are generated. An email is sent to the user who executed the bill run to notify the user that the bill run has completed.

    Although the bill run is complete, the invoices have not yet been posted. You must post the bill run, which will post the invoices and then email the invoices to complete the billing process. Typically, the Completed status for bill run is used to review the invoices for accuracy and then post/email them to your customers.

    When you create the bill run, you can select an option to automate the posting and emailing of the invoices after the bill run.

    Error	If a bill run is not successful, the bill run shows an error status. An email is sent to the user who executed the bill run to notify the user that the bill run has an error.
            Canceled
    A bill run can be canceled if it is either pending or completed. Bill runs still processing cannot be canceled.

    You can still individually cancel invoices that were all posted when an entire bill run was posted.

            Posted*/

    EnumBillRunStatus(String barcode) {

    }
}
