/*
 * Created on 2023-01-21 ( 17:18:10 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.salesmanager.core.model.banner;


import java.io.Serializable;
import javax.persistence.*;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

/**
 * JPA entity class for "Bannergroupbanners"
 *
 * @author Telosys
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name="BannerGroupBanners" )
@IdClass(BannergroupbannersId.class)
public class Bannergroupbanners extends SalesManagerEntity<BannergroupbannersId, Bannergroupbanners> implements Auditable{

    private static final long serialVersionUID = 1L;

    @Embedded
	private AuditSection auditSection = new AuditSection();
	
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="BannerGroupId", nullable=false)
    private Integer    bannergroupid ;

    @Id
    @Column(name="BannerId", nullable=false)
    private Integer    bannerid ;
    
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
	public BannergroupbannersId getId() {
		return new BannergroupbannersId(bannergroupid,bannerid);
	}

	@Override
	public void setId(BannergroupbannersId id) {
		this.bannergroupid = id.getBannergroupid();
		this.bannerid = id.getBannerid();		
	}

    //--- ENTITY LINKS ( RELATIONSHIP )

    /**
     * Constructor
     */
    public Bannergroupbanners() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setBannergroupid( Integer bannergroupid ) {
        this.bannergroupid = bannergroupid ;
    }
    public Integer getBannergroupid() {
        return this.bannergroupid;
    }

    public void setBannerid( Integer bannerid ) {
        this.bannerid = bannerid ;
    }
    public Integer getBannerid() {
        return this.bannerid;
    }

    //--- GETTERS FOR LINKS
    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(bannergroupid);
        sb.append("|");
        sb.append(bannerid);
        return sb.toString(); 
    } 

}