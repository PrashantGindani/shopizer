package com.salesmanager.core.model.catalog.product.extradetail;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.reference.language.Language;

@Entity
@Table(name = "PRODUCT_EXTRADETAILS",  
		uniqueConstraints = {@UniqueConstraint(columnNames = { "PRODUCT_ID", "LANGUAGE_ID" })}
		//,indexes = {@Index(name = "PRODUCT_DESCRIPTION_SEF_URL", columnList = "SEF_URL")}
)
@TableGenerator(name = "extradetails_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductExtraDetails implements Auditable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EXTRADETAIL_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "extradetails_gen")
	private Long id;
	
	@JsonIgnore
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "LANGUAGE_ID")
	private Language language;

	@Column(name="RETURN_DAYS", nullable = false, length=120)
	private Integer returnInDays = 0;
	
	@NotEmpty
	@Type(type = "org.hibernate.type.TextType")
	@Column(name="RETURN_TEXT", nullable = false, length=120)
	private String returnText;
	
	@NotEmpty
	@Column(name="WARRANTY_PERIOD_TEXT", length=20)
	private String warrantyPeriodText;
	
	
	@Column(name="WARRANTY_SUMMARY", length=100)
	@Type(type = "org.hibernate.type.TextType")
	private String warrantySummary;
	
	@JsonIgnore
	@Column(name="WARRANTY_DAYS")
	private Integer warrantyInDays = 0;

	@JsonIgnore
	@Column(name="WARRANTY_MONTHS")
	private Integer warrantyInMonths = 0;

	@JsonIgnore
	@Column(name="WARRANTY_YEARS")
	private Integer warrantyInYears = 0;

    @Column(name = "COD_AVAILABLE")
    private boolean cod;

    @Column(name = "GST_AVAILABLE")
    private boolean gst;
	
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	public ProductExtraDetails() {
	}
	
	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Integer getReturnInDays() {
		return returnInDays;
	}

	public void setReturnInDays(Integer returnInDays) {
		this.returnInDays = returnInDays;
	}

	public String getReturnText() {
		return returnText;
	}

	public void setReturnText(String returnText) {
		this.returnText = returnText;
	}

	public String getWarrantyPeriodText() {
		return warrantyPeriodText;
	}

	public void setWarrantyPeriod(int days,int months,int years) {
		StringBuilder sb= new StringBuilder();
		if(years>1) {
			warrantyPeriodText = ""+years+" Years";
		}
		else if(years==1) {
			warrantyPeriodText = ""+years+" Year";
		}
		else if(months>1) {
			warrantyPeriodText = ""+months+" Months";
		}
		else if(months==1) {
			warrantyPeriodText = ""+months+" Month";
		}
		else if(days>1) {
			warrantyPeriodText = ""+days+" Days";
		}
		else if(days==1) {
			warrantyPeriodText = ""+days+" Day";
		}
		else {
			warrantyPeriodText = null;
		}
		
		this.warrantyInDays = days;
		this.warrantyInMonths = months;
		this.warrantyInYears = years;
		  
	}
	
	public void setWarrantyPeriodText(String warrantyPeriodText) {
		this.warrantyPeriodText = warrantyPeriodText;
	}

	public String getWarrantySummary() {
		return warrantySummary;
	}

	public void setWarrantySummary(String warrantySummary) {
		this.warrantySummary = warrantySummary;
	}

	public Integer getWarrantyInDays() {
		return warrantyInDays;
	}

	public void setWarrantyInDays(Integer warrantyInDays) {
		this.warrantyInDays = warrantyInDays;
	}

	public Integer getWarrantyInMonths() {
		return warrantyInMonths;
	}

	public void setWarrantyInMonths(Integer warrantyInMonths) {
		this.warrantyInMonths = warrantyInMonths;
	}

	public Integer getWarrantyInYears() {
		return warrantyInYears;
	}

	public void setWarrantyInYears(Integer warrantyInYears) {
		this.warrantyInYears = warrantyInYears;
	}

	public boolean isCod() {
		return cod;
	}

	public void setCod(boolean cod) {
		this.cod = cod;
	}

	public boolean isGst() {
		return gst;
	}

	public void setGst(boolean gst) {
		this.gst = gst;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

