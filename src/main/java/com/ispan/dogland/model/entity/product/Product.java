package com.ispan.dogland.model.entity.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotBlank
    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ProductCategory category;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//在JAVA環境內的時間格式(輸入時調整，輸出為另一種)，EE為星期幾
    @Column(name = "listing_date")
    private Date listingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//在JAVA環境內的時間格式(輸入時調整，輸出為另一種)，EE為星期幾
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "discount_id")
    private Integer discountId;



    /////////////////////////////////////////

    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(listingDate==null && modifiedDate==null) {
            listingDate=new Date();
            modifiedDate=new Date();
        }
    }

    @PreUpdate
    public void onUpdate(){
        modifiedDate = new Date();
    }

    /////////////////////////////////////////

    @OneToMany(mappedBy = "product",
            fetch = FetchType.LAZY ,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ProductGallery> productGalleries;

    @OneToMany(mappedBy = "product",
            fetch = FetchType.LAZY ,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ProductGalleryCloud>productGalleryClouds;

    ///////////////////////////////////
    public Product() {
    }

    public Product(Integer productId, String productName, Employee employee, ProductCategory category, Integer unitPrice, String productDescription, Integer stock, Integer reservedQuantity, Date listingDate, Date modifiedDate) {
        this.productId = productId;
        this.productName = productName;
        this.employee = employee;
        this.category = category;
        this.unitPrice = unitPrice;
        this.productDescription = productDescription;
        this.stock = stock;
        this.reservedQuantity = reservedQuantity;
        this.listingDate = listingDate;
        this.modifiedDate = modifiedDate;
    }

    ///////////////////////////////////

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Date getListingDate() {
        return listingDate;
    }

    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public List<ProductGallery> getProductGalleries() {
        return productGalleries;
    }

    public void setProductGalleries(List<ProductGallery> productGalleries) {
        this.productGalleries = productGalleries;
    }

    public List<ProductGalleryCloud> getProductGalleryClouds() {
        return productGalleryClouds;
    }

    public void setProductGalleryClouds(List<ProductGalleryCloud> productGalleryClouds) {
        this.productGalleryClouds = productGalleryClouds;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
//////////////////////////////////

}
