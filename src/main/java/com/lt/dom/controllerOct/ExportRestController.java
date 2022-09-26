package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.ExportResp;
import com.lt.dom.controllerOct.Axh.XhController;
import com.lt.dom.oct.Axh.PullFromYxdRequest;
import com.lt.dom.oct.Export;
import com.lt.dom.otcReq.ExportReq;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.repository.Axh.PullFromYxdRequestRepository;
import com.lt.dom.repository.ExportRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ExportServiceImpl;
import com.lt.dom.serviceOtc.JxlsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ExportRestController {


    @Autowired
    private PullFromYxdRequestRepository pullFromYxdRequestRepository;

    @Autowired
    private ExportRepository exportRepository;

    @Autowired
    private ExportServiceImpl exportService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "/exports/Page_getExportList", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_getExportList() {

        EntityModel entityModel = EntityModel.of(Map.of("type_list", Arrays.stream(EnumExportVoucher.values()).map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));

        entityModel.add(linkTo(methodOn(ExportRestController.class).getExportList(null,null)).withRel("upload_bussiness_license_url"));
        return entityModel;
    }




    @GetMapping(value = "/exports", produces = "application/json")
    public ResponseEntity<PagedModel> getExportList(Pageable pageable, PagedResourcesAssembler<ExportResp> assembler) {

        Page<Export> validatorOptional = exportRepository.findAll(pageable);

        return ResponseEntity.ok(assembler.toModel(validatorOptional.map(x->ExportResp.from(x))));




    }


    @GetMapping(value = "exports/{EXPORT_ID}", produces = "application/json")
    public ResponseEntity<Export> getExports(@PathVariable long EXPORT_ID) {

        Optional<Export> validatorOptional = exportRepository.findById(EXPORT_ID);
        if(validatorOptional.isPresent()){
            try {
                return ResponseEntity.ok(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/exports/{Type}", produces = "application/json")
    public ResponseEntity<ExportResp> createExport(@PathVariable(value = "Type", required = false)  EnumExportVoucher type, @RequestBody ExportReq pojo) {

        System.out.println("================"+ pojo.toString());

        pojo.getParameters();


        Export export = exportService.createExport(type,pojo);

        return ResponseEntity.ok(ExportResp.from(export));



    }




/*    @Operation(summary = "2、下单购买")
   // @PostMapping(value = "/exports_j/{name}", produces = "application/json")
    public ResponseEntity<Void> exports_j(@PathVariable(value = "name") String name) {

        try {
            JxlsServiceImpl.getAvailability(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();

    }*/

    @GetMapping(value = "/exports_j/{name}", produces = "application/json")
    public PagedModel exports_j( @PathVariable(value = "name") String name, Pageable pageable, PagedResourcesAssembler<EntityModel<PullFromYxdRequest>> assembler) {
        // queueSender.send("test message");




        Page<PullFromYxdRequest> userList = pullFromYxdRequestRepository.findAll(pageable);

        return assembler.toModel(userList.map(e->{
            EntityModel entityModel = EntityModel.of(e);

            entityModel.add(linkTo(methodOn(XhController.class).getPullReque(e.getId())).withSelfRel());

            return entityModel;

        }));
    }


}