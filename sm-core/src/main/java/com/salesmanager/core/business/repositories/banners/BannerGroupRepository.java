package com.salesmanager.core.business.repositories.banners;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.banner.Bannergroups;

public interface BannerGroupRepository extends JpaRepository<Bannergroups, Integer> {

}
