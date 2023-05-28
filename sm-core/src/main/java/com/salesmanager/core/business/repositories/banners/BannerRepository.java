package com.salesmanager.core.business.repositories.banners;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.banner.Banners;

public interface BannerRepository extends JpaRepository<Banners, Integer> {
	
	@Query("select b from Banners b, Bannergroups bg, Bannergroupbanners bgb where bgb.bannerid=b.id and bgb.bannergroupid=bg.bannergroupid and bg.name like ?1")
	public List<Banners> findBannersFromGroupName(String bannergroupname);

}