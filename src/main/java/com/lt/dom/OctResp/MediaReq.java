package com.lt.dom.OctResp;

public class MediaReq {

  private String theatreImageIcon;//"theatreImageIcon": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/StoreIcon/Thumb/outage.jpg",
  private String  theatreImageLarge;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg",
  private String  theatreImageStandard;//          "theatreImageStandard": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Standard/outage.jpg",
  private String  theatreImageThumbnail;//          "theatreImageThumbnail": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Thumb/outage.jpg",
  private String  heroDesktopDynamic;//          "heroDesktopDynamic": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/HeroDesktopDynamic/Dynamic/outage.jpg",
  private String  heroMobileDynamic;//          "heroMobileDynamic": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/HeroMobileDynamic/Dynamic/outage.jpg",
  private String  interiorDynamic;//           "interiorDynamic": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/InteriorDynamic/Dynamic/outage.jpg",
  private String  exteriorDynamic;//          "exteriorDynamic": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/ExteriorDynamic/Dynamic/outage.jpg",
  private String  promotionDynamic;//          "promotionDynamic": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/PromotionDynamic/Dynamic/outage.jpg"

  public String getTheatreImageIcon() {
    return theatreImageIcon;
  }

  public void setTheatreImageIcon(String theatreImageIcon) {
    this.theatreImageIcon = theatreImageIcon;
  }

  public String getTheatreImageLarge() {
    return theatreImageLarge;
  }

  public void setTheatreImageLarge(String theatreImageLarge) {
    this.theatreImageLarge = theatreImageLarge;
  }

  public String getTheatreImageStandard() {
    return theatreImageStandard;
  }

  public void setTheatreImageStandard(String theatreImageStandard) {
    this.theatreImageStandard = theatreImageStandard;
  }

  public String getTheatreImageThumbnail() {
    return theatreImageThumbnail;
  }

  public void setTheatreImageThumbnail(String theatreImageThumbnail) {
    this.theatreImageThumbnail = theatreImageThumbnail;
  }
}