package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPassDorationUnit;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
//https://woocommerce.com/document/rest-api-reference/#gift_card_meta_props
@Entity
public class PassActivity {

        @Version
        private Integer version;

        @GeneratedValue(strategy = GenerationType.IDENTITY)

        @Id
        private long id;


    private String code ;//digital tickets or PDF tickets

    private String type;//	string	Activity Type	Read-only
    private String user_id;//	integer	Activity User ID	Read-only
    private String user_email;//	string	Activity User Email	Read-only
    private String object_id;//	integer	Activity Order ID or Order Item ID	Read-only
    private String gc_id;//	integer	Gift Card ID	Read-only
    private String gc_code;//	string	Gift Card Code	Read-only
    private String amount;//	number	Activity Amount	Read-only
    private String date;//	string	Activity Date	Read-only
    private String Returned;// as date in ISO8601 format Y-m-d H:i:s.
    private String note;//	string	Activity Note	Read-only

}
