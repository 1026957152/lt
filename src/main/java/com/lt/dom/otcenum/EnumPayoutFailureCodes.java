package com.lt.dom.otcenum;

public enum EnumPayoutFailureCodes {

    account_closed("The bank account has been closed."),

    account_frozen("The bank account has been frozen."),

    bank_account_restricted("The bank account has restrictions on either the type, or the number, of payouts allowed. This normally indicates that the bank account is a savings or other non-checking account."),

    bank_ownership_changed("The destination bank account is no longer valid because its branch has changed ownership."),

    could_not_process("The bank could not process this payout."),

    debit_not_authorized("Debit transactions are not approved on the bank account. (Stripe requires bank accounts to be set up for both credit and debit payouts.)"),

    declined("The bank has declined this transfer. Please contact the bank before retrying."),

    insufficient_funds("Your Stripe account has insufficient funds to cover the payout."),

    invalid_account_number("The routing number seems correct, but the account number is invalid."),

    incorrect_account_holder_name("Your bank notified us that the bank account holder name on file is incorrect."),

    incorrect_account_holder_address("Your bank notified us that the bank account holder address on file is incorrect."),

    incorrect_account_holder_tax_id("Your bank notified us that the bank account holder tax ID on file is incorrect."),

            invalid_currency("The bank was unable to process this payout because of its currency. This is probably because the bank account cannot accept payments in that currency."),

            no_account("The bank account details on file are probably incorrect. No bank account could be located with those details."),

            unsupported_card("The bank no longer supports payouts to this card."),;

    EnumPayoutFailureCodes(String barcode) {

    }
}
