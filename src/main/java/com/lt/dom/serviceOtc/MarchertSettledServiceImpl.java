package com.lt.dom.serviceOtc;


import com.lt.dom.JwtUtils;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Missing_documentException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcReq.SettleAccountPojo;
import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.TempDocumentRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.SupplierPojoVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class MarchertSettledServiceImpl {


    @Autowired
    private SettleAccountServiceImpl settleAccountService;



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SupplierServiceImp supplierServiceImp;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private TempDocumentRepository tempDocumentRepository;


    @Autowired
    private OpenidRepository openidRepository;


    @Autowired
    private IdGenServiceImpl idGenService;



















    public Request merchants_settled(MerchantsSettledReq wxlinkUserReq, long owner) {


        User user = userRepository.findById(owner).get();



        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();

        EnumSupplier enumSupplier = wxlinkUserReq.getAllowed_supplier();

        if(enumSupplier.getType().equals(EnumSupplierType.TravelAgent)){
            if(nonNull(wxlinkUserReq.getBussiness_license())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.business_license,wxlinkUserReq.getBussiness_license()));
            }else{
                throw new BookNotFoundException(Enumfailures.missing_documents,"需要上传 营业执照");
            }

            if(nonNull(wxlinkUserReq.getBussiness_license())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license_for_opening_bank_account,wxlinkUserReq.getBussiness_license()));
            }else{
                throw new BookNotFoundException(Enumfailures.missing_documents,"需要上传 开户许可证");


            }

            if(nonNull(wxlinkUserReq.getLicense_image())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license,wxlinkUserReq.getLicense_image()));

                // docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.license,x)).collect(Collectors.toList()));
            }else{

                throw new BookNotFoundException(Enumfailures.missing_documents,"需要上传 责任保险");
            }
            if(nonNull(wxlinkUserReq.getLiability_insurance_image())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.liability_insurance,wxlinkUserReq.getLiability_insurance_image()));

                // docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.liability_insurance,x)).collect(Collectors.toList()));
            }else{
                throw new BookNotFoundException(Enumfailures.missing_documents,"需要上传 责任保险");

            }
        }else{
            if(nonNull(wxlinkUserReq.getBussiness_license())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.business_license,wxlinkUserReq.getBussiness_license()));

                //    docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(Collectors.toList()));
            }else{

                throw new BookNotFoundException(Enumfailures.missing_documents,"需要上传营业执照");

            }

        }




        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(Collectors.toList()));


        System.out.println("------------------------------------------------"+tempDocuments.toString());


        Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(), x->x));

        List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
            TempDocument tempDocument = documentMap.get(x.getValue1());
            return Pair.with(x.getValue0(),tempDocument);
        }).collect(Collectors.toList());




        if(docTypeWithTempDocPairList.isEmpty()){
            throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要附上申请相关文档");
        }



        SupplierPojo supplierPojo = new SupplierPojo();
        supplierPojo.setDesc(wxlinkUserReq.getDesc());
        supplierPojo.setSupplierName(wxlinkUserReq.getSupplier_name());



        supplierPojo.setType(enumSupplier.getType());



        MerchantsSettledReq.Location location = wxlinkUserReq.getLocation();

        supplierPojo.setLat(location.getLatitude());
        supplierPojo.setLng(location.getLongitude());
        supplierPojo.setLocation(location.getAddress());
        supplierPojo.setLocationName(location.getName());


        if(wxlinkUserReq.getBusiness_type() == null){
            supplierPojo.setBusiness_type(EnumBussinessType.company);
        }else{
            supplierPojo.setBusiness_type(wxlinkUserReq.getBusiness_type());
        }


        SupplierPojoVo supplierPojoVo = SupplierPojoVo.fromPojo(supplierPojo);


        Supplier supplier = supplierServiceImp.createSupplier(supplierPojoVo, EnumSupplierStatus.PendingApproval);


        // List<Document> documents = fileStorageService.saveFromTempDocumentList(supplier.getId(),docTypeWithTempDocPairList);




        supplierServiceImp.成为员工(supplier,user);






        SettleAccountPojo settleAccountPojo = new SettleAccountPojo();
        settleAccountPojo.setName(wxlinkUserReq.getAccount_name());
        settleAccountPojo.setAccount(wxlinkUserReq.getBank_account_number());
        settleAccountPojo.setOpen_bank_code(wxlinkUserReq.getBank_name());

        settleAccountService.add(supplier,settleAccountPojo);


        return null;
    }













    public MerchantsSettledReq  validat(@RequestBody @Valid MerchantsSettledReq wxlinkUserReq) {

        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();

        EnumSupplier enumSupplier = wxlinkUserReq.getAllowed_supplier();

        if(enumSupplier.getType().equals(EnumSupplierType.TravelAgent)){
            if(nonNull(wxlinkUserReq.getBussiness_license())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.business_license,wxlinkUserReq.getBussiness_license()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 营业执照");
            }

            if(nonNull(wxlinkUserReq.getLicense_for_opening_bank_account())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license_for_opening_bank_account,wxlinkUserReq.getLicense_for_opening_bank_account()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 开户许可证");
            }

            if(nonNull(wxlinkUserReq.getLicense_image())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license,wxlinkUserReq.getLicense_image()));

                // docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.license,x)).collect(Collectors.toList()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 责任保险");

            }
            if(nonNull(wxlinkUserReq.getLiability_insurance_image())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.liability_insurance,wxlinkUserReq.getLiability_insurance_image()));

                // docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.liability_insurance,x)).collect(Collectors.toList()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 责任保险");
            }
        }else{
            if(nonNull(wxlinkUserReq.getBussiness_license())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.business_license,wxlinkUserReq.getBussiness_license()));

                //    docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(Collectors.toList()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传营业执照");

            }
            if(nonNull(wxlinkUserReq.getLicense_for_opening_bank_account())){
                docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license_for_opening_bank_account,wxlinkUserReq.getLicense_for_opening_bank_account()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 开户许可证");
            }
        }


        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(Collectors.toList()));


        System.out.println("------------------------------------------------"+tempDocuments.toString());


        Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(), x->x));

        List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
            TempDocument tempDocument = documentMap.get(x.getValue1());
            return Pair.with(x.getValue0(),tempDocument);
        }).collect(Collectors.toList());


/*            if(tempDocuments.size() ==0){
                throw new BookNotFoundException(1,"找不到上传文件");
            }*/



        if(docTypeWithTempDocPairList.isEmpty()){
            throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要附上申请相关文档");
        }


        return wxlinkUserReq;

    }

}
