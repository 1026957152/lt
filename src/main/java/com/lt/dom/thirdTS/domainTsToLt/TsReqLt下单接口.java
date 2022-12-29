package com.lt.dom.thirdTS.domainTsToLt;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.thirdTS.EnumMethord;
import org.apache.commons.lang3.ObjectUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.security.core.parameters.P;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TsReqLt下单接口 extends TsReqLtBase {



    private Integer item_id;//产品ID
    private Integer id_ntype;

    public static TsReqLt下单接口 from_(MultiValueMap<String, String> ob_) {
        TsReqLt下单接口 tsReqLtBase = new TsReqLt下单接口();
        tsReqLtBase.setItem_id(Integer.valueOf(ob_.getFirst("item_id")));

        tsReqLtBase.setOrders_id(ob_.getFirst("orders_id"));
        tsReqLtBase.setSize(Integer.valueOf( ob_.getFirst("size")));
        tsReqLtBase.setIs_pay(Integer.valueOf(  ob_.getFirst("is_pay")));
        tsReqLtBase.setMobile((String)  ob_.getFirst("mobile"));
        tsReqLtBase.setName((String)  ob_.getFirst("name"));
        tsReqLtBase.setId_number((String)  ob_.getFirst("id_number"));
        tsReqLtBase.setStart_date_auto((String)  ob_.getFirst("start_date_auto"));
        tsReqLtBase.setRemark((String)  ob_.getFirst("remark"));

        tsReqLtBase.setPrice((String)  ob_.getFirst("price"));
        tsReqLtBase.setShuttle_address((String)  ob_.getFirst("shuttle_address"));
        tsReqLtBase.setPlate_number((String)  ob_.getFirst("plate_number"));
        tsReqLtBase.setPlate_number((String)  ob_.getFirst("post_address"));


        LocalDate localDate =  LocalDate.parse((String)ob_.getFirst("start_date2"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        tsReqLtBase.setStart_date2(localDate);
        LocalDateTime localDateTime =  LocalDateTime.parse((String)ob_.getFirst("start_date"), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        tsReqLtBase.setStart_date(localDateTime);
        if(ObjectUtils.isNotEmpty( ob_.getFirst("g_batch_type"))){
            tsReqLtBase.setG_batch_type(Integer.valueOf( ob_.getFirst("g_batch_type")));
        }

        if(ObjectUtils.isEmpty(tsReqLtBase.getId_number())){



            List<Player> playerList = ob_.keySet().stream().filter(e->e.startsWith("players[")).map(e->{


                String num =  e.replaceAll("[^0-9]+", " ").trim().split(" ")[0];
                System.out.println("这里是需要"+num);
                return Triplet.with(num,e,ob_.getFirst(e));
            }).collect(Collectors.groupingBy(e->e.getValue0(),Collectors.collectingAndThen(Collectors.toList(),list->{

                Optional<String> name = list.stream().filter(e->{
                    System.out.println(e.toString());
                    return e.getValue1().endsWith("name]");
                }).map(e->e.getValue2()).findAny();

                Optional<String> mobile = list.stream().filter(e->e.getValue1().endsWith("mobile]")).map(e->e.getValue2()).findAny();

                Optional<String> id_number = list.stream().filter(e->e.getValue1().endsWith("id_number]")).map(e->e.getValue2()).findAny();
                Optional<String> Id_ntype = list.stream().filter(e->e.getValue1().endsWith("id_ntype]")).map(e->e.getValue2()).findAny();

                Player player = new Player();
                player.setName(name.orElse(null));
                player.setMobile(mobile.orElse(null));
                player.setId_number(id_number.orElse(null));
                player.setId_ntype(Id_ntype.orElse(null));

                return player;
            }))).values().stream().toList();

            System.out.println("====playerList=========="+ playerList);
            tsReqLtBase.setPlayers(playerList.toArray(Player[]::new));
        }

        tsReqLtBase.setTime_slot((String) ob_.getFirst("time_slot"));


        ;
        return tsReqLtBase;
    }


    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(Integer is_pay) {
        this.is_pay = is_pay;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getStart_date_auto() {
        return start_date_auto;
    }

    public void setStart_date_auto(String start_date_auto) {
        this.start_date_auto = start_date_auto;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShuttle_address() {
        return shuttle_address;
    }

    public void setShuttle_address(String shuttle_address) {
        this.shuttle_address = shuttle_address;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getPost_address() {
        return post_address;
    }

    public void setPost_address(String post_address) {
        this.post_address = post_address;
    }

    public LocalDate getStart_date2() {
        return start_date2;
    }

    public void setStart_date2(LocalDate start_date2) {
        this.start_date2 = start_date2;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public Integer getG_batch_type() {
        return g_batch_type;
    }

    public void setG_batch_type(Integer g_batch_type) {
        this.g_batch_type = g_batch_type;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    private String orders_id;//:第三方的订单ID，最大支持32位
    private Integer size;//:订单数量
    private Integer is_pay;//:是否下单同时支付（1:同时支付，0:不支付）缺省时默认1
    private String mobile;//:手机号
    private String name;//:姓名
    private String id_number;//:身份证
    private String start_date_auto;//:是否自动设置游玩时间，1:是；0:否
    private String remark;//:订单备注信息
    private String price;//:产品单价（如果有则校验产品单价与本系统当前单价是否一致）
    private String shuttle_address;//:接送地址
    private String plate_number;//:车牌号
    private String post_address;//:邮寄地址


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")

    private LocalDate start_date2;//:游玩时间，格式为：2020-10-01
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")

    private LocalDateTime start_date;//:开始游玩时间，格式为：2020/10/01 00:00:00


    private Integer g_batch_type;//:是否多人游客，值为2时，则表示有多人游客
    private String time_slot;//:时间段库存参数，如：08:00:00-16:00:00




    private Player[] players ;//s[1][id_number];//:412825199010026766

    public Integer getId_ntype() {
        return id_ntype;
    }

    public void setId_ntype(Integer id_ntype) {
        this.id_ntype = id_ntype;
    }


    public static class Player {


        private String name;//:孙高尚
        private String mobile;//:15238089055
        private String id_ntype;//:证件类型(详情见项目文档-数据字典)
        private String id_number;//:412825198803184532

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId_ntype() {
            return id_ntype;
        }

        public void setId_ntype(String id_ntype) {
            this.id_ntype = id_ntype;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }
    }












}
