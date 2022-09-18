package com.lt.dom.thiirdAli.idcard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

@NoArgsConstructor
@Data
public class IdcardFaceVo {


    @JsonProperty("address")
    private String address;
    @JsonProperty("config_str")
    private String configStr;
    @JsonProperty("face_rect")
    private FaceRectDTO faceRect;
    @JsonProperty("card_region")
    private List<CardRegionDTO> cardRegion;
    @JsonProperty("face_rect_vertices")
    private List<FaceRectVerticesDTO> faceRectVertices;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("num")
    private String num;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("birth")
    private String birth;
    @JsonProperty("success")
    private Boolean success;

    public IdcardFaceVoSimple toSimple() {
        IdcardFaceVoSimple idcardFaceVoSimple = new IdcardFaceVoSimple();
        idcardFaceVoSimple.setName(this.getName());
        idcardFaceVoSimple.setNum(this.getNum());
        return idcardFaceVoSimple;
    }
    public class IdcardFaceVoSimple {
        @JsonProperty("name")
        private String name;
        @JsonProperty("num")
        private String num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
    @NoArgsConstructor
    @Data
    public static class FaceRectDTO {
        @JsonProperty("angle")
        private Integer angle;
        @JsonProperty("center")
        private CenterDTO center;
        @JsonProperty("size")
        private SizeDTO size;

        public Integer getAngle() {
            return angle;
        }

        public void setAngle(Integer angle) {
            this.angle = angle;
        }

        public CenterDTO getCenter() {
            return center;
        }

        public void setCenter(CenterDTO center) {
            this.center = center;
        }

        public SizeDTO getSize() {
            return size;
        }

        public void setSize(SizeDTO size) {
            this.size = size;
        }

        @NoArgsConstructor
        @Data
        public static class CenterDTO {
            @JsonProperty("x")
            private Integer x;
            @JsonProperty("y")
            private Double y;

            public Integer getX() {
                return x;
            }

            public void setX(Integer x) {
                this.x = x;
            }

            public Double getY() {
                return y;
            }

            public void setY(Double y) {
                this.y = y;
            }
            @Override
            public String toString() {
                return ReflectionToStringBuilder.toString(this);
            }
        }

        @NoArgsConstructor
        @Data
        public static class SizeDTO {
            @JsonProperty("height")
            private Double height;
            @JsonProperty("width")
            private Double width;

            public Double getHeight() {
                return height;
            }

            public void setHeight(Double height) {
                this.height = height;
            }

            public Double getWidth() {
                return width;
            }

            public void setWidth(Double width) {
                this.width = width;
            }
            @Override
            public String toString() {
                return ReflectionToStringBuilder.toString(this);
            }
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    @NoArgsConstructor
    @Data
    public static class CardRegionDTO {
        @JsonProperty("x")
        private Integer x;
        @JsonProperty("y")
        private Integer y;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    @NoArgsConstructor
    @Data
    public static class FaceRectVerticesDTO {
        @JsonProperty("x")
        private Double x;
        @JsonProperty("y")
        private Double y;

        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConfigStr() {
        return configStr;
    }

    public void setConfigStr(String configStr) {
        this.configStr = configStr;
    }

    public FaceRectDTO getFaceRect() {
        return faceRect;
    }

    public void setFaceRect(FaceRectDTO faceRect) {
        this.faceRect = faceRect;
    }

    public List<CardRegionDTO> getCardRegion() {
        return cardRegion;
    }

    public void setCardRegion(List<CardRegionDTO> cardRegion) {
        this.cardRegion = cardRegion;
    }

    public List<FaceRectVerticesDTO> getFaceRectVertices() {
        return faceRectVertices;
    }

    public void setFaceRectVertices(List<FaceRectVerticesDTO> faceRectVertices) {
        this.faceRectVertices = faceRectVertices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
