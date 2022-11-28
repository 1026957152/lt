package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CustomerResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CustomerReq;
import com.lt.dom.repository.CustomerRepository;
import com.lt.dom.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;



    public Customer create(Supplier supplier, CustomerResp theatreReq) {
        Customer theatre = new Customer();

        theatre.setName(theatreReq.getName());
        theatre.setDescription(theatreReq.getDescription());
        theatre.setPhone(theatreReq.getPhone());

        Address location = new Address();
        location.setCity(theatreReq.getCity());
        location.setState(theatreReq.getState());
        location.setAddressLine1(theatreReq.getAddress_line());


        theatre = customerRepository.save(theatre);




        return theatre;


    }

    public Customer update(Customer supplier, CustomerReq theatreReq) {

/*        supplier.setDescription_text(theatreReq.getDescription_text());
        supplier.setPreview_image_url(theatreReq.getPreview_image_url());
        supplier.setHref(theatreReq.getHref());
        supplier.setTitle(theatreReq.getTitle());*/

        supplier = customerRepository.save(supplier);
        return supplier;
    }


    public List<Customer> fromValueList(List<Long> collect) {


        List<Customer> componentRightList = customerRepository.findAllById(collect);
        return componentRightList;

    }
}
