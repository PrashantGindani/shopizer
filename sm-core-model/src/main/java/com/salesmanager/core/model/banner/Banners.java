/*
 * Created on 2023-01-21 ( 17:24:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.salesmanager.core.model.banner;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;

/**
 * JPA entity class for "Banners"
 *
 * @author Telosys
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "Banners")
public class Banners extends SalesManagerEntity<Integer, Banners> implements Auditable{

    private static final long serialVersionUID = 1L;

	@Embedded
	private AuditSection auditSection = new AuditSection();
	
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="BannerId", nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "BAN_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Integer    id ;

    //--- ENTITY DATA FIELDS 
    @Column(name="ImageLink", nullable=false, length=0)
    private String     imageName ;

    @Column(name="BannerType", length=100)
    private String     bannertype ;

    @Column(name="BannerPositions", length=250)
    private String     bannerpositions ;

    @Column(name="RelatedBrandId", length=10)
    private String     relatedbrandid ;

    @Column(name="RelatedCategoryId", length=10)
    private String     relatedcategoryid ;

    @Column(name="RelatedSubCategoryId", length=10)
    private String     relatedsubcategoryid ;

    @Column(name="OnClickResponseType", nullable=false)
    private Integer    onclickresponsetype ;

    @Column(name="WebPageLink", length=0)
    private String     webpagelink ;

    @Column(name="CustomSqlQuery", length=0)
    private String     customsqlquery ;

    @Column(name="CustomTextToSearch", length=100)
    private String     customtexttosearch ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToMany(mappedBy="listOfBanners",fetch = FetchType.LAZY)
    private List<Bannergroups> listOfBannergroups ; 

    @Transient
    private InputStream image;
    
    @Transient
	private boolean obsolete = false;//when all items are obsolete
    
	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
		
	}

    /**
     * Constructor
     */
    public Banners() {
		super();
    }

	public boolean isObsolete() {
		return obsolete;
	}
	
    //--- GETTERS & SETTERS FOR FIELDS

    public void setImageName( String imageName ) {
        this.imageName = imageName ;
    }
    public String getImageName() {
        return this.imageName;
    }

    public void setBannertype( String bannertype ) {
        this.bannertype = bannertype ;
    }
    public String getBannertype() {
        return this.bannertype;
    }

    public void setBannerpositions( String bannerpositions ) {
        this.bannerpositions = bannerpositions ;
    }
    public String getBannerpositions() {
        return this.bannerpositions;
    }

    public void setRelatedbrandid( String relatedbrandid ) {
        this.relatedbrandid = relatedbrandid ;
    }
    public String getRelatedbrandid() {
        return this.relatedbrandid;
    }

    public void setRelatedcategoryid( String relatedcategoryid ) {
        this.relatedcategoryid = relatedcategoryid ;
    }
    public String getRelatedcategoryid() {
        return this.relatedcategoryid;
    }

    public void setRelatedsubcategoryid( String relatedsubcategoryid ) {
        this.relatedsubcategoryid = relatedsubcategoryid ;
    }
    public String getRelatedsubcategoryid() {
        return this.relatedsubcategoryid;
    }

    public void setOnclickresponsetype( Integer onclickresponsetype ) {
        this.onclickresponsetype = onclickresponsetype ;
    }
    public Integer getOnclickresponsetype() {
        return this.onclickresponsetype;
    }

    public void setWebpagelink( String webpagelink ) {
        this.webpagelink = webpagelink ;
    }
    public String getWebpagelink() {
        return this.webpagelink;
    }

    public void setCustomsqlquery( String customsqlquery ) {
        this.customsqlquery = customsqlquery ;
    }
    public String getCustomsqlquery() {
        return this.customsqlquery;
    }

    public void setCustomtexttosearch( String customtexttosearch ) {
        this.customtexttosearch = customtexttosearch ;
    }
    public String getCustomtexttosearch() {
        return this.customtexttosearch;
    }

    //--- GETTERS FOR LINKS
    public List<Bannergroups> getListOfBannergroups() {
        return this.listOfBannergroups;
    } 

    public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public void setListOfBannergroups(List<Bannergroups> listOfBannergroups) {
		this.listOfBannergroups = listOfBannergroups;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}

	//--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(imageName);
        sb.append("|");
        sb.append(bannertype);
        sb.append("|");
        sb.append(bannerpositions);
        sb.append("|");
        sb.append(relatedbrandid);
        sb.append("|");
        sb.append(relatedcategoryid);
        sb.append("|");
        sb.append(relatedsubcategoryid);
        sb.append("|");
        sb.append(onclickresponsetype);
        sb.append("|");
        sb.append(webpagelink);
        sb.append("|");
        sb.append(customsqlquery);
        sb.append("|");
        sb.append(customtexttosearch);
        return sb.toString(); 
    } 

}