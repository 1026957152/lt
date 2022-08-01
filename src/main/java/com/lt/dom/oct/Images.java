package com.lt.dom.oct;

public class Images {

    private String alttext;// - is a short description of the most relevant, prominent features or general characteristics of the image
    private String  baseimageurl;// - is the primary internet address for the image and is delivered through our default image delivery service
    private String  copyright;//  - is the copyright for the image
    private String  description;// - is a long description of the contents of the image
    private String displayorder;//  - is the preferred sequence number for the image within a group of images in the context of the object record; sequencing is especially important for manuscripts and books; an image with displayorder = 1 can be thought of as the current canonical image for the object
    private String format;//  - is the media type/MIME type of the image (typically image/jpeg)
    private String height;//  - is the height of the image in pixels
    private String idsid;//  - is the unique numeric identifier for the image in the Harvard Digital Repository
    private String iiifbaseuri;//  - is the root of the address for accessing the image through our IIIF image delivery service
    private String imageid;//  - is the numeric unique identifier for the image which can be used to construct a URI to dereference it publiccaption - is a descriptive note about the content of the media often listing information about the view (recto, verso, profile, 3/4 view)
    private String renditionnumber;//  - is the unique name for the image; this often takes the form of a prefix + serial number; the prefix can provide hints as to when, why, and how we made the image
    private String date;//  - is either the date of creation of the image record in our collections management system OR the date of creation of the image itself (this is very fuzzy data)
    private String technique;//  - is the photography equipment and software used during the production of the image
    private String width;//  - is the width of the image in pixels
}
