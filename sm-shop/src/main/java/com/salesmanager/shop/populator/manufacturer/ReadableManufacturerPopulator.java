package com.salesmanager.shop.populator.manufacturer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;
import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturerFull;
import com.salesmanager.shop.utils.ImageFilePath;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReadableManufacturerPopulator extends
    AbstractDataPopulator<com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer, ReadableManufacturer> {

	private ImageFilePath imageUtils;

  public ReadableManufacturerPopulator(ImageFilePath imageUtils) {
		this.imageUtils=imageUtils;
	}

@Override
  public ReadableManufacturer populate(
      com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer source,
      ReadableManufacturer target, MerchantStore store, Language language)
      throws ConversionException {


    if (language == null) {
      target = new ReadableManufacturerFull();
    }
    target.setOrder(source.getOrder());
    target.setId(source.getId());
    target.setImageUrl(imageUtils.buildOtherAssetFilePath(store, Constants.MANUFACTURER_URI, source.getImageName()));
    target.setCode(source.getCode());
    if (source.getDescriptions() != null && source.getDescriptions().size() > 0) {

      List<com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription> fulldescriptions =
          new ArrayList<com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription>();

      Set<ManufacturerDescription> descriptions = source.getDescriptions();
      ManufacturerDescription description = null;
      for (ManufacturerDescription desc : descriptions) {
        if (language != null && desc.getLanguage().getCode().equals(language.getCode())) {
          description = desc;
          break;
        } else {
          fulldescriptions.add(populateDescription(desc));
        }
      }



      if (description != null) {
        com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription d =
            populateDescription(description);
        target.setDescription(d);
      }

      if (target instanceof ReadableManufacturerFull) {
        ((ReadableManufacturerFull) target).setDescriptions(fulldescriptions);
      }

    }



    return target;
  }

  @Override
  protected ReadableManufacturer createTarget() {
    return null;
  }

  com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription populateDescription(
      ManufacturerDescription description) {
    if (description == null) {
      return null;
    }
    com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription d =
        new com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription();
    d.setName(description.getName());
    d.setDescription(description.getDescription());
    d.setId(description.getId());
    d.setTitle(description.getTitle());
    if (description.getLanguage() != null) {
      d.setLanguage(description.getLanguage().getCode());
    }
    return d;
  }

}
