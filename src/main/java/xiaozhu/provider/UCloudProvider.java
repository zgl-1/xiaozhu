package xiaozhu.provider;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;

@Service
public class UCloudProvider {
	@Value("${ucloud.ufile.public-key}")
	private String publiceKey;
	
	@Value("${ucloud.ufile.private-key}")
	private String privateKey;
	
	@Value("${ucloud.ufile.bucket-name}")
	private String bucketName ;
	
	@Value("${ucloud.ufile.region}")
	private String region ;
	
	@Value("${ucloud.ufile.suffix}")
	private String suffix ;

	@Value("${ucloud.ufile.expires}")
	private Integer expires ;
	
	public String  upload(InputStream inputStream,String mimeType,String fileName) {
		File file = new File("your file path");

		String generateFileNameString;
		String[] fileNameSplit = fileName.split("\\.");
		if(fileNameSplit.length>1) {
			generateFileNameString=UUID.randomUUID().toString()+"."+fileNameSplit[fileNameSplit.length-1];
		}
		else {
			throw new CustomException(CustomErrorEnum.FILE_UPLOAD_FAIL);
		}
		
		try {
			ObjectAuthorization  BUCKET_AUTHORIZER = new UfileObjectLocalAuthorization(publiceKey, privateKey);
			ObjectConfig config = new ObjectConfig(region, suffix);
		    
			PutObjectResultBean response = UfileClient.object(BUCKET_AUTHORIZER, config)
		         .putObject(inputStream, mimeType)
		         .nameAs(generateFileNameString)
		         .toBucket(bucketName)
		         .setOnProgressListener(new OnProgressListener() {
		              @Override
		              public void onProgress(long bytesWritten, long contentLength) {
		                  
		              }
		         })
		         .execute();
		    if(response!=null&&response.getRetCode()==0) {
		    	 String url = UfileClient.object(BUCKET_AUTHORIZER, config)
		                    .getDownloadUrlFromPrivateBucket(generateFileNameString, bucketName, expires)
		                    .createUrl();
		    	 return url;
		    }else {
				throw new CustomException(CustomErrorEnum.FILE_UPLOAD_FAIL);
			}
		} catch (UfileClientException e) {
		    e.printStackTrace();
		    throw new CustomException(CustomErrorEnum.FILE_UPLOAD_FAIL);
		} catch (UfileServerException e) {
		    e.printStackTrace();
		    throw new CustomException(CustomErrorEnum.FILE_UPLOAD_FAIL);
		}
	}
}
