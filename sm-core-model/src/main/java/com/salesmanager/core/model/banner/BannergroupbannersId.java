/*
 * Created on 2023-01-21 ( 17:18:10 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.salesmanager.core.model.banner;

import java.io.Serializable;


/**
 * Composite primary key for entity "Bannergroupbanners" ( stored in table "BannerGroupBanners" )
 *
 * @author Telosys
 *
 */
public class BannergroupbannersId implements Serializable, Comparable<BannergroupbannersId> {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private Integer    bannergroupid ;
    
    private Integer    bannerid ;
    
    /**
     * Constructor
     */
    public BannergroupbannersId() {
        super();
    }

    /**
     * Constructor with values
     * @param bannergroupid 
     * @param bannerid 
     */
    public BannergroupbannersId( Integer bannergroupid, Integer bannerid ) {
        super();
        this.bannergroupid = bannergroupid ;
        this.bannerid = bannerid ;
    }
    
    //--- GETTERS & SETTERS FOR KEY FIELDS
    public void setBannergroupid( Integer value ) {
        this.bannergroupid = value;
    }
    public Integer getBannergroupid() {
        return this.bannergroupid;
    }

    public void setBannerid( Integer value ) {
        this.bannerid = value;
    }
    public Integer getBannerid() {
        return this.bannerid;
    }


    //--- equals METHOD
	@Override
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		BannergroupbannersId other = (BannergroupbannersId) obj; 
		//--- Attribute bannergroupid
		if ( bannergroupid == null ) { 
			if ( other.bannergroupid != null ) 
				return false ; 
		} else if ( ! bannergroupid.equals(other.bannergroupid) ) 
			return false ; 
		//--- Attribute bannerid
		if ( bannerid == null ) { 
			if ( other.bannerid != null ) 
				return false ; 
		} else if ( ! bannerid.equals(other.bannerid) ) 
			return false ; 
		return true; 
	} 

    //--- hashCode METHOD
	@Override
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute bannergroupid
		result = prime * result + ((bannergroupid == null) ? 0 : bannergroupid.hashCode() ) ; 
		//--- Attribute bannerid
		result = prime * result + ((bannerid == null) ? 0 : bannerid.hashCode() ) ; 
		
		return result; 
	} 

    //--- toString METHOD
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(bannergroupid);
        sb.append("|");
        sb.append(bannerid);
        return sb.toString(); 
    }

	@Override
	public int compareTo(BannergroupbannersId o) {
		// TODO Auto-generated method stub
		return 0;
	} 

}
