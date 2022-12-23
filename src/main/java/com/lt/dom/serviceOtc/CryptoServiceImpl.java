package com.lt.dom.serviceOtc;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lt.dom.OctResp.ClainQuotaStatisticsResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.vo.PersonBean;
import org.hashids.Hashids;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;


@Service
public class CryptoServiceImpl {

    Hashids hashids = new Hashids("this is my pepper");


    public String decode(String  code) {



        PersonBean.Person person = null;
        try {
            person = PersonBean.Person.parseFrom(Base64.getDecoder().decode(code));


            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(person.getAge()), TimeZone
                    .getDefault().toZoneId());


        } catch (InvalidProtocolBufferException e) {

            throw new RuntimeException(e);
        }catch (Exception e){

        }
        return person.getName();
    }


    public String encode(String  code) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        PersonBean.Person person = PersonBean.Person.newBuilder()
                .setAge(timestamp.getTime())
                .setName(code)
                .build();

        String encodedString =
                Base64.getEncoder().withoutPadding().encodeToString(person.toByteArray());

        return encodedString;
    }

    public String hashids_encode(String  code) {

        String hash = hashids.encodeHex(code);

        return hash;
    }

    public String hashids_encode(Long  code) {

        String hash = hashids.encode(code);

        return hash;
    }
    public String hashids_decode(String  code) {

        String numbers = hashids.decodeHex(code);

        return numbers;
    }


    public Long hashids_decode_(String  code) {

        Long numbers = hashids.decode(code)[0];

        return numbers;
    }

}
