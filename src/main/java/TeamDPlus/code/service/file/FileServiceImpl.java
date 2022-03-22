package TeamDPlus.code.service.file;


import TeamDPlus.code.config.amazons3.AmazonS3Component;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{

    private final AmazonS3Client amazonS3Client;
    private final AmazonS3Component amazonS3Component;


    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(amazonS3Component.getBucket(), fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicReadWrite));
    }

    @Override
    public void deleteFile(String fileName) {
        int separator = fileName.lastIndexOf("/") + 1; //uuid 인덱스 번호 확인
        String substring = fileName.substring(separator);
        amazonS3Client.deleteObject(new DeleteObjectRequest(amazonS3Component.getBucket(), substring));
    }

    @Override
    public String getFileUrl(String fileName) {
        return amazonS3Client.getUrl(amazonS3Component.getBucket(), fileName).toString();

    }
}
