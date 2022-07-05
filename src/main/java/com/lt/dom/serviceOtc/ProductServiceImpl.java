package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingRuleDeparturePojo;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcReq.RoyaltyPojo;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.RoyaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BookingRuleRepository bookingRuleRepository;

    @Autowired
    private RoyaltyRepository royaltyRepository;


    @Autowired
    private ComponentRightRepository componentRightRepository;

    //product_id, compainId 和 userId
    public List<RoyaltyRuleData> redeemRoyalty(Product product, RoyaltyTemplate royaltyTemplate){


        Referral referral = new Referral();

        return null;


    }

    public List<Product> listProduct(Supplier supplier) {
        return null;
    }



    public Product createProduct(Supplier supplier,  ProductPojo pojo) {
        Product product = new Product();

        product.setSupplierId(supplier.getId());

        return productRepository.save(product);
    }



    public Optional<Product> getById(long supplier_id, long product_id) {
        Product user = new Product();
        user.setId(product_id);
        user.setSupplierId(supplier_id);
        Example<Product> example = Example.of(user);

        return productRepository.findOne(example);
    }

    public BookingRule addBookingRule(Product product, BookingRuleDeparturePojo pojo) {

        BookingRule bookingRule = new BookingRule();
        bookingRule.setProductId(product.getId());

        bookingRuleRepository.save(bookingRule);

        return bookingRuleRepository.save(bookingRule);
    }

    public Royalty addRoyalty(Product product, RoyaltyPojo pojo) {

        Royalty royalty = new Royalty();
        royalty.setProductId(pojo.getProductId());
        royalty.setParentProductId(product.getId());
        return royaltyRepository.save(royalty);
    }

    public ComponentRight addComponentRight(Product product, ComponentRightPojo pojo) {
        ComponentRight royalty = new ComponentRight();
      //  royalty.setProductId(pojo.getProductId());
      //  royalty.setParentProductId(product.getId());
        return componentRightRepository.save(royalty);
    }
}
