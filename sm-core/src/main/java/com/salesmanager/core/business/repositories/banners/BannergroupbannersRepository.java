package com.salesmanager.core.business.repositories.banners;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.banner.Bannergroupbanners;
import com.salesmanager.core.model.banner.BannergroupbannersId;

public interface BannergroupbannersRepository extends JpaRepository<Bannergroupbanners, BannergroupbannersId> {

}
