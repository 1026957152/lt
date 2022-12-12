package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ComponentVounchResp;
import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ComponentVoucherReq;
import com.lt.dom.otcReq.ReportArrivalReq;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumReportBookingCompression;
import com.lt.dom.otcenum.EnumVoucherRedemptionStatus;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.QrcodeServiceImpl;
import com.lt.dom.specification.BookingSpecification;
import com.lt.dom.specification.ComponentVoucherQueryfieldsCriteria;
import com.lt.dom.specification.ComponentVoucherSpecification;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ComponentVoucherRestController {
    private static final Logger logger = LoggerFactory.getLogger(RedemptionRestController.class);

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ProductRepository productRepository;



    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    QrcodeServiceImpl qrcodeService;

    @Operation(summary = "1、获得")
    @GetMapping(value = "/component_vouchers/{VOUCHER_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<VoucherResp>> getVoucher(@PathVariable long VOUCHER_ID) {


        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUCHER_ID);
        //Optional<Voucher> optionalVoucher = voucherRepository.findOne(example);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID, "找不到优惠券");
        }

        Optional<Campaign> campaignOptional = campaignRepository.findById(optionalVoucher.get().getCampaign());

        List<Asset> assets = assetService.getQr(optionalVoucher.get().getCode());
        VoucherResp Voucher = VoucherResp.from(Triplet.with(optionalVoucher.get(), campaignOptional.get(), assets), Optional.empty());
        return ResponseEntity.ok(EntityModel.of(Voucher));
    }


    @Operation(summary = "1、获得")
    @GetMapping(value = "/component_vouchers/{VOUCHER_ID}/qr_refresh", produces = "application/json")
    public ResponseEntity<String> refresh(@PathVariable long VOUCHER_ID) {

        logger.debug("参数：", VOUCHER_ID);

        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUCHER_ID);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID, "找不到优惠券");
        }
        Voucher voucher = optionalVoucher.get();
        String crypto = qrcodeService._encode(voucher.getCode());

        String 解密后的数据 = qrcodeService._decode(crypto);
        logger.debug("参数{} {} 加密{} 解密后的数据{}", VOUCHER_ID, voucher.getCode(), crypto, 解密后的数据);
        return ResponseEntity.ok(crypto);
    }






    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/Page_component_vouchers", produces = "application/json")

    public EntityModel Page_component_vouchers(@PathVariable Long SUPPLIER_ID) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if (supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商", "没找到");
        }

        Supplier supplier = supplierOptional.get();


        List<AgentConnection> agentConnections = agentRepository.findAll();


        List<Product> productList = productRepository.findAll();
        List<Supplier> suppliers = supplierRepository.findAll();

        Map map = Map.of(
                "product_list", Product.List(productList),
                "supplier_list", Supplier.List(suppliers),
                "agent_list", AgentConnection.List(agentConnections),
                "check_in_status_list", EnumVoucherRedemptionStatus.List(),
                "voucher_status_list", EnumComponentVoucherStatus.List()



        );


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ComponentVoucherRestController.class).listVoucher(null,supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }





    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/component_vouchers", produces = "application/json")
    public PagedModel listVoucher(@Valid ComponentVoucherReq req, @PathVariable Long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ComponentVounchResp>> assembler) {

        logger.debug("参数 {} ",req);

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if (supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商", "没找到");
        }

        Supplier supplier = supplierOptional.get();


        ComponentVoucherQueryfieldsCriteria criteria = new ComponentVoucherQueryfieldsCriteria();


/*
        criteria.setStarts_at(req.getReport_data_from());
        criteria.setEnds_at(req.getReport_data_to());*/

        criteria.setVoucherRedemptionStatus(req.getCheck_in_status());
      //  criteria.setStatuses(Arrays.asList(req.getVoucherStatus()));
        criteria.setProduct(req.getProduct());
        criteria.setAgent(req.getAgent());

        criteria.setSupplier(req.getSupplier());
        criteria.setCheck_in_status(req.getCheck_in_status());

        if(req.getVoucherStatus() != null){
            criteria.setStatuses(Arrays.asList(req.getVoucherStatus()));
        }



        if(req.getCheck_date_from()!= null){
            criteria.setCreated_from(req.getCreate_date_from().atStartOfDay());

        }
        if(req.getCreate_date_to()!= null){
            criteria.setCreated_to(req.getCreate_date_to().atStartOfDay());

        }

        criteria.setCheck_in_from(req.getCheck_date_from());
        criteria.setCheck_in_to(req.getCheck_date_from());

        ComponentVoucherSpecification spec =
                new ComponentVoucherSpecification(criteria);


        Page<ComponentVounch> voucherPage = componentVounchRepository.findAll(where(spec) , pageable);


        return assembler.toModel(voucherPage.map(e -> {

            ComponentVounchResp Voucher = ComponentVounchResp.from(e);
            return EntityModel.of(Voucher);
        }));


    }


}