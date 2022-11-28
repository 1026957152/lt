package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.BlogResp;
import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BlogReq;
import com.lt.dom.otcReq.CommentReq;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class BlogRestController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private OpenidRepository openidRepository;


    @GetMapping(value = "/Page_listBlog", produces = "application/json")
    public EntityModel<Media> Page_listBlog( ) {





        Map map = Map.of("text","大家秀");


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(BlogRestController.class).listBlog(null,null)).withRel("list"));


        return entityModel;

    }

    @GetMapping(value = "/blogs/{INVOICE_ID}", produces = "application/json")
    public EntityModel getblog(@PathVariable long INVOICE_ID) {

        Optional<Blog> validatorOptional = blogRepository.findById(INVOICE_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }

        Blog blog = validatorOptional.get();

        BlogResp blogResp = BlogResp.from(blog);
        User user = userRepository.findById(blog.getUser()).get();

        BlogResp.Author author = BlogResp.Author.from(user);

        PhotoResp resp = new PhotoResp();
        Optional<Openid> optional = openidRepository.findByUserId(blog.getUser()).stream().findAny();
        if(optional.isPresent()){
            Openid openid = optional.get();

            resp.setUrl(openid.getOpenid_image());
            author.setImage(resp);
            author.setDisplayName(openid.getOpenid_name());
        }else{

            author.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.user_avatar,user.getCode()));
        }





        blogResp.setAuthor(author);


        blogResp.setCoverMedia(fileStorageService.loadDocumentWithDefault(EnumDocumentType.blog_cover,blog.getCode()));


        EntityModel entityModel = EntityModel.of(blogResp);
        entityModel.add(linkTo(methodOn(BlogRestController.class).getblog(blog.getId())).withSelfRel());

        return entityModel;

    }


    @GetMapping(value = "/blogs", produces = "application/json")
    public PagedModel listBlog(Pageable pageable, PagedResourcesAssembler<EntityModel<Blog>> assembler) {


        Page<Blog> validatorOptional = blogRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{

            BlogResp invoiceResp = BlogResp.from(e);

            User user = userRepository.findById(e.getUser()).get();
            BlogResp.Author author = BlogResp.Author.from(user);

            PhotoResp resp = new PhotoResp();
            Optional<Openid> optional = openidRepository.findByUserId(e.getUser()).stream().findAny();
            if(optional.isPresent()){
                Openid openid = optional.get();

                resp.setUrl(openid.getOpenid_image());
                author.setImage(resp);
                author.setDisplayName(openid.getOpenid_name());
            }else{

                author.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.user_avatar,user.getCode()));
            }





            invoiceResp.setAuthor(author);


            invoiceResp.setCoverMedia(fileStorageService.loadDocumentWithDefault(EnumDocumentType.blog_cover,e.getCode()));

            EntityModel entityModel = EntityModel.of(invoiceResp);
            entityModel.add(linkTo(methodOn(BlogRestController.class).getblog(e.getId())).withSelfRel());
            return entityModel;
        }));

    }




    @PostMapping(value = "/blogs", produces = "application/json")
    public ResponseEntity<EntityModel> createBlog(@RequestBody @Valid BlogReq subscriptionResp) {
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);



        Blog subscription = blogService.create(userVo,subscriptionResp);


        EntityModel entityModel = EntityModel.of(subscription);


        return ResponseEntity.ok(entityModel);


    }


    @PutMapping(value = "/blogs/{INVOICE_ID}", produces = "application/json")
    public EntityModel editblog(@PathVariable long INVOICE_ID,@RequestBody @Valid BlogReq subscriptionResp) {

        Optional<Blog> validatorOptional = blogRepository.findById(INVOICE_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }

        Blog blog = validatorOptional.get();
        Blog subscription = blogService.edit(blog,subscriptionResp);



        EntityModel entityModel = EntityModel.of(subscription);
        entityModel.add(linkTo(methodOn(BlogRestController.class).editblog(blog.getId(),null)).withSelfRel());

        return entityModel;

    }



    @PostMapping(value = "/blogs/{PRODUCT_ID}/comments", produces = "application/json")
    public ResponseEntity<EntityModel> createComment(@PathVariable long PRODUCT_ID,
                                                     @RequestBody @Valid CommentReq subscriptionResp) {
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Supplier supplier = userVo.getSupplier();

        Optional<Product> optionalRatePlan = productRepository.findById(PRODUCT_ID);

        if(optionalRatePlan.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"没找到 产品");

        }
        Product product = optionalRatePlan.get();

        Comment subscription = commentService.create(product, subscriptionResp);



       // SubscriptionResp supplierResp = SubscriptionResp.from(subscription);
        EntityModel entityModel = EntityModel.of(subscription);


        return ResponseEntity.ok(entityModel);


    }



    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/blogs/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long PRODUCT_ID) {

        blogRepository.deleteById(PRODUCT_ID);

        return ResponseEntity.ok().build();
    }




}