package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumAvailableActions {
    Campaigns_ReadCampaigns(EnumAvailableActionType.Campaigns,"Read Campaigns."),
    Campaigns_CreateCampaigns(EnumAvailableActionType.Campaigns,"Create Campaigns and related Validation Rules."),
    Campaigns_ModifyCampaigns(EnumAvailableActionType.Campaigns,"Modify Campaigns"),
    Campaigns_DeleteCampaigns(EnumAvailableActionType.Campaigns,"Delete Campaigns."),
    Campaigns_EnableCampaigns(EnumAvailableActionType.Campaigns,"Enable Campaigns."),
    Campaigns_DisableCampaigns(EnumAvailableActionType.Campaigns,"Disable Campaigns."),



    Vouchers_ReadVouchers(EnumAvailableActionType.Vouchers,"Read Vouchers."),
    Vouchers_Read_Voucher_by_code(EnumAvailableActionType.Vouchers,"Read Voucher by code."),
    Vouchers_Create_Vouchers(EnumAvailableActionType.Vouchers,"Create Vouchers"),
    Vouchers_Modify_Vouchers(EnumAvailableActionType.Vouchers,"Modify Vouchers."),
    Vouchers_Enable_Vouchers(EnumAvailableActionType.Vouchers,"Enable Vouchers"),
    Vouchers_Disable_Vouchers(EnumAvailableActionType.Vouchers,"Disable Vouchers"),

    Vouchers_Redeem_Voucher(EnumAvailableActionType.Vouchers,"Redeem Voucher."),
    Vouchers_Rollback_Redemptions(EnumAvailableActionType.Vouchers,"Rollback Redemptions (Rollback means turning back redemption once it's made)."),
    Vouchers_Publish_Voucher(EnumAvailableActionType.Vouchers,"Publish Voucher."),
    Vouchers_Import_Vouchers(EnumAvailableActionType.Vouchers," Import Vouchers."),
    Vouchers_Delete_Vouchers(EnumAvailableActionType.Vouchers," Delete Vouchers."),
    Vouchers_Add_balance_to_Gift_Vouchers(EnumAvailableActionType.Vouchers,"Add balance to Gift Vouchers."),




    Distributions_Publish_Voucher(EnumAvailableActionType.Distributions,"Read and export Distributions and Publications."),
    Distributions_Import_Vouchers(EnumAvailableActionType.Distributions,"Create and modify Distributions."),
    Distributions_Enable_Distributions(EnumAvailableActionType.Distributions,"Enable Distributions."),
    Distributions_Disable_Distributions(EnumAvailableActionType.Distributions,"Disable Distributions."),



    Redemptions_Read_Redemptions(EnumAvailableActionType.Redemptions,"Read and export Distributions and Publications."),
    Redemptions_Read_Redemptions_history_of_identified_Voucher(EnumAvailableActionType.Redemptions,"Read Redemptions history of identified Voucher"),
    Redemptions_Rollback_Redemptions(EnumAvailableActionType.Redemptions,"Rollback Redemptions."),
    Redemptions_Limit_listing_of_Redemptions_to_those_done_by_User(EnumAvailableActionType.Redemptions,"Limit listing of Redemptions to those done by User. (if you mark this box, users will see only redemptions invoked by their account)"),


    Products_Read_Products(EnumAvailableActionType.Products,"Read Products"),
    Products_Create_and_modify_Products(EnumAvailableActionType.Products,"Create and modify Products"),
    Products_Import_Products(EnumAvailableActionType.Products,"Import Products"),

    Customers_Read_Customers(EnumAvailableActionType.Customers,"Read Customers"),
    Customers_Read_a_single_Customer_by_ID_or_Source_ID(EnumAvailableActionType.Customers,"Read a single Customer by ID or Source ID"),
    Customers_Create_and_modify_Customers_and_Segments(EnumAvailableActionType.Customers,"Create and modify Customers and Segments");









    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    private String name;
    private EnumAvailableActionType type;

    public EnumAvailableActionType getType() {
        return type;
    }

    public void setType(EnumAvailableActionType type) {
        this.type = type;
    }

    @Autowired
    private MessageSource messageSource;

    EnumAvailableActions(EnumAvailableActionType ob,String name) {

        this.type = ob;
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.role.action."
                + this.name());
        return displayStatusString;
    }




}
