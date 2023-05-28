package com.salesmanager.core.business.modules.cms.product.aws;

import java.io.ByteArrayOutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.impl.CMSManager;
import com.salesmanager.core.business.modules.cms.product.OtherAssetsManager;
import com.salesmanager.core.business.modules.cms.product.ProductAssetsManager;
import com.salesmanager.core.model.banner.Banners;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.file.ProductImageSize;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;

/**
 * Product content file manager with AWS S3
 * 
 * @author carlsamson
 *
 */
public class S3ProductContentFileManager
    implements ProductAssetsManager {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  private static final Logger LOGGER = LoggerFactory.getLogger(S3ProductContentFileManager.class);



  private static S3ProductContentFileManager fileManager = null;

  private static String DEFAULT_BUCKET_NAME = "shopizer-content";
  private static String DEFAULT_REGION_NAME = "us-east-1";
  private static final String ROOT_NAME = "products";

  private static final char UNIX_SEPARATOR = '/';
  private static final char WINDOWS_SEPARATOR = '\\';


  private final static String SMALL = "SMALL";
  private final static String LARGE = "LARGE";

  private CMSManager cmsManager;

  public static S3ProductContentFileManager getInstance() {

    if (fileManager == null) {
      fileManager = new S3ProductContentFileManager();
    }

    return fileManager;

  }

  @Override
  public List<OutputContentFile> getImages(String merchantStoreCode,
      FileContentType imageContentType) throws ServiceException {
    try {
      // get buckets
      String bucketName = bucketName();



      ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
          .withBucketName(bucketName).withPrefix(nodePath(merchantStoreCode));

      List<OutputContentFile> files = null;
      final AmazonS3 s3 = s3Client();
      ListObjectsV2Result results = s3.listObjectsV2(listObjectsRequest);
      List<S3ObjectSummary> objects = results.getObjectSummaries();
      for (S3ObjectSummary os : objects) {
        if (files == null) {
          files = new ArrayList<OutputContentFile>();
        }
        String mimetype = URLConnection.guessContentTypeFromName(os.getKey());
        if (!StringUtils.isBlank(mimetype)) {
          S3Object o = s3.getObject(bucketName, os.getKey());
          byte[] byteArray = IOUtils.toByteArray(o.getObjectContent());
          ByteArrayOutputStream baos = new ByteArrayOutputStream(byteArray.length);
          baos.write(byteArray, 0, byteArray.length);
          OutputContentFile ct = new OutputContentFile();
          ct.setFile(baos);
          files.add(ct);
        }
      }

      return files;
    } catch (final Exception e) {
      LOGGER.error("Error while getting files", e);
      throw new ServiceException(e);

    }
  }

  @Override
  public void removeImages(String merchantStoreCode) throws ServiceException {
    try {
      // get buckets
      String bucketName = bucketName();

      final AmazonS3 s3 = s3Client();
      s3.deleteObject(bucketName, nodePath(merchantStoreCode));

      LOGGER.info("Remove folder");
    } catch (final Exception e) {
      LOGGER.error("Error while removing folder", e);
      throw new ServiceException(e);

    }

  }

  @Override
  public void removeProductImage(ProductImage productImage) throws ServiceException {
    try {
      // get buckets
      String bucketName = bucketName();

      final AmazonS3 s3 = s3Client();
      s3.deleteObject(bucketName, nodePath(productImage.getProduct().getMerchantStore().getCode(),
          productImage.getProduct().getSku()) + productImage.getProductImage());

      LOGGER.info("Remove file");
    } catch (final Exception e) {
      LOGGER.error("Error while removing file", e);
      throw new ServiceException(e);

    }

  }

  @Override
  public void removeProductImages(Product product) throws ServiceException {
    try {
      // get buckets
      String bucketName = bucketName();

      final AmazonS3 s3 = s3Client();
      s3.deleteObject(bucketName, nodePath(product.getMerchantStore().getCode(), product.getSku()));

      LOGGER.info("Remove file");
    } catch (final Exception e) {
      LOGGER.error("Error while removing file", e);
      throw new ServiceException(e);

    }

  }

  @Override
  public OutputContentFile getProductImage(String merchantStoreCode, String productCode,
      String imageName) throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OutputContentFile getProductImage(String merchantStoreCode, String productCode,
      String imageName, ProductImageSize size) throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OutputContentFile getProductImage(ProductImage productImage) throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<OutputContentFile> getImages(Product product) throws ServiceException {
    return null;
  }

  @Override
  public void addProductImage(ProductImage productImage, ImageContentFile contentImage)
      throws ServiceException {


    try {
      // get buckets
      String bucketName = bucketName();
      final AmazonS3 s3 = s3Client();

      String nodePath = this.nodePath(productImage.getProduct().getMerchantStore().getCode(),
          productImage.getProduct().getSku(), contentImage);


      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentImage.getMimeType());

      PutObjectRequest request = new PutObjectRequest(bucketName,
          nodePath + productImage.getProductImage(), contentImage.getFile(), metadata);
      request.setCannedAcl(CannedAccessControlList.PublicRead);


      s3.putObject(request);


      LOGGER.info("Product add file");

    } catch (final Exception e) {
      LOGGER.error("Error while removing file", e);
      throw new ServiceException(e);

    }


  }


  private Bucket getBucket(String bucket_name) {
    final AmazonS3 s3 = s3Client();
    Bucket named_bucket = null;
    List<Bucket> buckets = s3.listBuckets();
    for (Bucket b : buckets) {
      if (b.getName().equals(bucket_name)) {
        named_bucket = b;
      }
    }

    if (named_bucket == null) {
      named_bucket = createBucket(bucket_name);
    }

    return named_bucket;
  }

  private Bucket createBucket(String bucket_name) {
    final AmazonS3 s3 = s3Client();
    Bucket b = null;
    if (s3.doesBucketExistV2(bucket_name)) {
      System.out.format("Bucket %s already exists.\n", bucket_name);
      b = getBucket(bucket_name);
    } else {
      try {
        b = s3.createBucket(bucket_name);
      } catch (AmazonS3Exception e) {
        System.err.println(e.getErrorMessage());
      }
    }
    return b;
  }

  /**
   * Builds an amazon S3 client
   * 
   * @return
   */
  private AmazonS3 s3Client() {

    return AmazonS3ClientBuilder.standard().withRegion(regionName()) // The first region to
                                                                            // try your request
                                                                            // against
        .build();
  }

  private String bucketName() {
    String bucketName = getCmsManager().getRootName();
    if (StringUtils.isBlank(bucketName)) {
      bucketName = DEFAULT_BUCKET_NAME;
    }
    return bucketName;
  }

  private String regionName() {
    String regionName = getCmsManager().getLocation();
    if (StringUtils.isBlank(regionName)) {
      regionName = DEFAULT_REGION_NAME;
    }
    return regionName;
  }

  private String nodePath(String store) {
    return new StringBuilder().append(ROOT_NAME).append(Constants.SLASH).append(store)
        .append(Constants.SLASH).toString();
  }

  private String nodePath(String store, String product) {

    StringBuilder sb = new StringBuilder();
    // node path
    String nodePath = nodePath(store);
    sb.append(nodePath);

    // product path
    sb.append(product).append(Constants.SLASH);
    return sb.toString();

  }

  private String nodePath(String store, String product, ImageContentFile contentImage) {

    StringBuilder sb = new StringBuilder();
    // node path
    String nodePath = nodePath(store, product);
    sb.append(nodePath);

    // small large
    if (contentImage.getFileContentType().name().equals(FileContentType.PRODUCT.name())) {
      sb.append(SMALL);
    } else if (contentImage.getFileContentType().name().equals(FileContentType.PRODUCTLG.name())) {
      sb.append(LARGE);
    }

    return sb.append(Constants.SLASH).toString();


  }

  public static String getName(String filename) {
    if (filename == null) {
      return null;
    }
    int index = indexOfLastSeparator(filename);
    return filename.substring(index + 1);
  }

  public static int indexOfLastSeparator(String filename) {
    if (filename == null) {
      return -1;
    }
    int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
    int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
    return Math.max(lastUnixPos, lastWindowsPos);
  }



  public CMSManager getCmsManager() {
    return cmsManager;
  }

  public void setCmsManager(CMSManager cmsManager) {
    this.cmsManager = cmsManager;
  }

	@Override
	public void addCategoryImage(Category category, ImageContentFile contentImage) throws ServiceException {
	
	    try {
	      // get buckets
	      String bucketName = bucketName();
	      final AmazonS3 s3 = s3Client();
	
	      String nodePath = new StringBuilder().append(CATEGORY_ROOT).append(Constants.SLASH).append(category.getMerchantStore().getCode())
	    	        .append(Constants.SLASH).toString(); 
	
	      ObjectMetadata metadata = new ObjectMetadata();
	      metadata.setContentType(contentImage.getMimeType());
	
	      PutObjectRequest request = new PutObjectRequest(bucketName,
	          nodePath + category.getCategoryImage(), contentImage.getFile(), metadata);
	      request.setCannedAcl(CannedAccessControlList.PublicRead);
	
	
	      s3.putObject(request);
	
	
	      LOGGER.info("Catgeory add file");
	
	    } catch (final Exception e) {
	      LOGGER.error("Error while removing file", e);
	      throw new ServiceException(e);
	
	    }
	
	}
	
	@Override
	public void removeCategoryImage(Category category) throws ServiceException {
		
		try {
		      // get buckets
		      String bucketName = bucketName();
	
		      final AmazonS3 s3 = s3Client();
		      String nodePath = new StringBuilder().append(CATEGORY_ROOT).append(Constants.SLASH).append(category.getMerchantStore().getCode())
		    	        .append(Constants.SLASH).append(category.getCategoryImage()).toString(); 
		      s3.deleteObject(bucketName, nodePath);
	
		      LOGGER.info("Remove file");
		    } catch (final Exception e) {
		      LOGGER.error("Error while removing file", e);
		      throw new ServiceException(e);
	
		    }
	}

	@Override
	public void addManufacturerImage(Manufacturer manufacturer, ImageContentFile contentImage) throws ServiceException {
	
	    try {
	      // get buckets
	      String bucketName = bucketName();
	      final AmazonS3 s3 = s3Client();
	
	      String nodePath = new StringBuilder().append(MANUFACTURER_ROOT).append(Constants.SLASH).append(manufacturer.getMerchantStore().getCode())
	    	        .append(Constants.SLASH).toString(); 
	
	      ObjectMetadata metadata = new ObjectMetadata();
	      metadata.setContentType(contentImage.getMimeType());
	
	      PutObjectRequest request = new PutObjectRequest(bucketName,
	          nodePath + manufacturer.getImageName(), contentImage.getFile(), metadata);
	      request.setCannedAcl(CannedAccessControlList.PublicRead);
	
	
	      s3.putObject(request);
	
	
	      LOGGER.info("Catgeory add file");
	
	    } catch (final Exception e) {
	      LOGGER.error("Error while removing file", e);
	      throw new ServiceException(e);
	
	    }
	
	}
	
	@Override
	public void removeManufacturerImage(Manufacturer manufacturer) throws ServiceException {
		
		try {
		      // get buckets
		      String bucketName = bucketName();
	
		      final AmazonS3 s3 = s3Client();
		      String nodePath = new StringBuilder().append(MANUFACTURER_ROOT).append(Constants.SLASH).append(manufacturer.getMerchantStore().getCode())
		    	        .append(Constants.SLASH).append(manufacturer.getImageName()).toString(); 
		      s3.deleteObject(bucketName, nodePath);
	
		      LOGGER.info("Remove file");
		    } catch (final Exception e) {
		      LOGGER.error("Error while removing file", e);
		      throw new ServiceException(e);
	
		    }
	}

	@Override
	public void addBannerImage(Banners banner, ImageContentFile contentImage) throws ServiceException {

	    try {
	      // get buckets
	      String bucketName = bucketName();
	      final AmazonS3 s3 = s3Client();
	
	      String nodePath = new StringBuilder().append(BANNER_ROOT).append(Constants.SLASH).toString(); 
	
	      ObjectMetadata metadata = new ObjectMetadata();
	      metadata.setContentType(contentImage.getMimeType());
	
	      PutObjectRequest request = new PutObjectRequest(bucketName,
	          nodePath + banner.getImageName(), contentImage.getFile(), metadata);
	      request.setCannedAcl(CannedAccessControlList.PublicRead);
	
	
	      s3.putObject(request);
	
	
	      LOGGER.info("Catgeory add file");
	
	    } catch (final Exception e) {
	      LOGGER.error("Error while removing file", e);
	      throw new ServiceException(e);
	
	    }
	
	}

	@Override
	public void removeBannerImage(Banners banner) throws ServiceException {
		
		try {
		      // get buckets
		      String bucketName = bucketName();
	
		      final AmazonS3 s3 = s3Client();
		      String nodePath = new StringBuilder().append(BANNER_ROOT).append(Constants.SLASH)
		    		  .append(banner.getImageName()).toString(); 
		      s3.deleteObject(bucketName, nodePath);
	
		      LOGGER.info("Remove file");
		    } catch (final Exception e) {
		      LOGGER.error("Error while removing file", e);
		      throw new ServiceException(e);
	
		    }
	}

}
