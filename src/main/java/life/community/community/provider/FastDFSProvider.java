package life.community.community.provider;


import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class FastDFSProvider {

    private Logger log = LoggerFactory.getLogger(FastDFSProvider.class);

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;


    /**
     * 上传文件到FastDFS服务器
     * @param multipartFile     spring MVC的上传文件对象
     * @return                  文件访问的URI
     */
    public String uploadFile(MultipartFile multipartFile) {
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(),
                    multipartFile.getSize(),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()),
                    null);
            return storePath.getFullPath();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
