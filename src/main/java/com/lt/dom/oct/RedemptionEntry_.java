package com.lt.dom.oct;

import com.lt.dom.oct.RedemptionEntry.RedemptionStatus;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RedemptionEntry.class)
public abstract class RedemptionEntry_ {

	public static volatile SingularAttribute<RedemptionEntry, Boolean> rollback;
	public static volatile SingularAttribute<RedemptionEntry, String> code;
	public static volatile SingularAttribute<RedemptionEntry, Long> relatedObjectId;
	public static volatile SingularAttribute<RedemptionEntry, Integer> redeemed_amount;
	public static volatile SingularAttribute<RedemptionEntry, Long> voucher;
	public static volatile SingularAttribute<RedemptionEntry, Long> validatorId;
	public static volatile SingularAttribute<RedemptionEntry, LocalDateTime> created_at;
	public static volatile SingularAttribute<RedemptionEntry, String> holder;
	public static volatile SingularAttribute<RedemptionEntry, Integer> version;
	public static volatile SingularAttribute<RedemptionEntry, LocalDateTime> redeem_at;
	public static volatile SingularAttribute<RedemptionEntry, LocalDateTime> issued_at;
	public static volatile SingularAttribute<RedemptionEntry, RedemptionStatus> result;
	public static volatile SingularAttribute<RedemptionEntry, String> campaign_name;
	public static volatile SingularAttribute<RedemptionEntry, Long> redemption;
	public static volatile SingularAttribute<RedemptionEntry, Long> supplier;
	public static volatile SingularAttribute<RedemptionEntry, Long> campaign;
	public static volatile SingularAttribute<RedemptionEntry, Long> id;
	public static volatile SingularAttribute<RedemptionEntry, Integer> redeemed_quantity;
	public static volatile SingularAttribute<RedemptionEntry, EnumPublicationObjectType> relatedObjectType;
	public static volatile SingularAttribute<RedemptionEntry, Long> customer_id;
	public static volatile SingularAttribute<RedemptionEntry, Boolean> bulk;
	public static volatile SingularAttribute<RedemptionEntry, Integer> gift_amount;
	public static volatile SingularAttribute<RedemptionEntry, String> voucherCode;

	public static final String ROLLBACK = "rollback";
	public static final String CODE = "code";
	public static final String RELATED_OBJECT_ID = "relatedObjectId";
	public static final String REDEEMED_AMOUNT = "redeemed_amount";
	public static final String VOUCHER = "voucher";
	public static final String VALIDATOR_ID = "validatorId";
	public static final String CREATED_AT = "created_at";
	public static final String HOLDER = "holder";
	public static final String VERSION = "version";
	public static final String REDEEM_AT = "redeem_at";
	public static final String ISSUED_AT = "issued_at";
	public static final String RESULT = "result";
	public static final String CAMPAIGN_NAME = "campaign_name";
	public static final String REDEMPTION = "redemption";
	public static final String SUPPLIER = "supplier";
	public static final String CAMPAIGN = "campaign";
	public static final String ID = "id";
	public static final String REDEEMED_QUANTITY = "redeemed_quantity";
	public static final String RELATED_OBJECT_TYPE = "relatedObjectType";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String BULK = "bulk";
	public static final String GIFT_AMOUNT = "gift_amount";
	public static final String VOUCHER_CODE = "voucherCode";

}

