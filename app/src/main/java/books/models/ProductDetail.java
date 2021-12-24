package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductDetail {

    @SerializedName("mainProductId")
        @Expose
        private Integer mainProductId;
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("productImage1")
        @Expose
        private String productImage1;
        @SerializedName("productImage2")
        @Expose
        private String productImage2;
        @SerializedName("productImage3")
        @Expose
        private String productImage3;
        @SerializedName("productNetPrice")
        @Expose
        private Double productNetPrice;
        @SerializedName("productSellingPrice")
        @Expose
        private Double productSellingPrice;
        @SerializedName("discountPercentage")
        @Expose
        private Double discountPercentage;
        @SerializedName("productUnit")
        @Expose
        private String productUnit;
        @SerializedName("sizeList")
        @Expose
        private ArrayList<Size> sizeList = null;
        @SerializedName("colorList")
        @Expose
        private ArrayList<Color> colorList = null;
        @SerializedName("productDescription")
        @Expose
        private String productDescription;

        public Integer getMainProductId() {
            return mainProductId;
        }

        public void setMainProductId(Integer mainProductId) {
            this.mainProductId = mainProductId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage1() {
            return productImage1;
        }

        public void setProductImage1(String productImage1) {
            this.productImage1 = productImage1;
        }

        public String getProductImage2() {
            return productImage2;
        }

        public void setProductImage2(String productImage2) {
            this.productImage2 = productImage2;
        }

        public String getProductImage3() {
            return productImage3;
        }

        public void setProductImage3(String productImage3) {
            this.productImage3 = productImage3;
        }

        public Double getProductNetPrice() {
            return productNetPrice;
        }

        public void setProductNetPrice(Double productNetPrice) {
            this.productNetPrice = productNetPrice;
        }

        public Double getProductSellingPrice() {
            return productSellingPrice;
        }

        public void setProductSellingPrice(Double productSellingPrice) {
            this.productSellingPrice = productSellingPrice;
        }

        public Double getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(Double discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public ArrayList<Size> getSizeList() {
            return sizeList;
        }

        public void setSizeList(ArrayList<Size> sizeList) {
            this.sizeList = sizeList;
        }

        public ArrayList<Color> getColorList() {
            return colorList;
        }

        public void setColorList(ArrayList<Color> colorList) {
            this.colorList = colorList;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

    }
