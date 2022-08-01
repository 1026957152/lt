package com.lt.dom.RealNameAuthentication;


import com.lt.dom.oct.MemberCertification;
import com.lt.dom.repository.MemberCertificationRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RealNameAuthenticationServiceImpl {


    @Autowired
    private MemberCertificationRepository memberCertificationRepository;


    UserService service
            = GitHubServiceGenerator.createService(UserService.class);


    public boolean checkRealname(PhoneAuth phoneAuth) {


        Optional<MemberCertification> memberCertificationOptional = memberCertificationRepository.findByNameAndIdCardNumber(phoneAuth.getIdCardName(),phoneAuth.getPhoneNo());

        if(memberCertificationOptional.isPresent()){
            return true;
        }

        Call<ResultPhoneAuth> callAsync = service.getUser("eugenp",phoneAuth);

        callAsync.enqueue(new Callback<ResultPhoneAuth>() {
            @Override
            public void onResponse(Call<ResultPhoneAuth> call, Response<ResultPhoneAuth> response) {

                ResultPhoneAuth user = response.body();

                System.out.println("dddddddddddddddddddd"+response.errorBody());
                if(Integer.getInteger(user.getResultcode()) == Resultcode._0.getCode()){
                    MemberCertification memberCertification = new MemberCertification();
                    memberCertification.setName(user.getIdCardName());
                    memberCertification.setIdCardNumber(user.getIdCardNo());
                    memberCertification.setUpdate_at(LocalDate.now());
                    memberCertification = memberCertificationRepository.save(memberCertification);
                }



            }

            @Override
            public void onFailure(Call<ResultPhoneAuth> call, Throwable throwable) {
                System.out.println("错误了啊"+throwable);
            }
        });
        return true;
       // throw new RuntimeException();


    }


    public Pair<List<PhoneAuth>, List<PhoneAuth>> bulkCheckRealname(List<PhoneAuth> travelerReqs) {

        List<PhoneAuth> real = new ArrayList<>();
        List<PhoneAuth> notReal = new ArrayList<>();
        travelerReqs.forEach(x->{

            if(checkRealname(x)){
                real.add(x);
            }else{
                notReal.add(x);
            }
        });
        return Pair.with(real,notReal);
    }
}
