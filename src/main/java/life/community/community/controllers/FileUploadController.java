package life.community.community.controllers;


import life.community.community.dto.FileDto;
import life.community.community.provider.FastDFSProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class FileUploadController {

    @Autowired
    private FastDFSProvider fastDFSProvider;

    @Value("${remote.image.url}")
    private String url;

    @Value("${fdfs.web-server-url}")
    private String imageServerUrl;


    /**
     * 处理Editor.md上传的图片
     * @param guid              由Editor.md携带
     * @param multipartFile     上传的文件对象
     * @return                  返回一个Editor.md可以解析的JSON数据
     * @throws IOException
     */
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto fileUploadHandle(@RequestParam("guid") String guid,
                                    @RequestParam("editormd-image-file") MultipartFile multipartFile) throws IOException {
        FileDto fileDto = new FileDto();


        String resourceUrl = fastDFSProvider.uploadFile(multipartFile);
        if (StringUtils.isBlank(resourceUrl)) {
            fileDto.setSuccess(0);      // FastDFS上传失败，Editor.md规定失败success设置为0
            fileDto.setMessage("upload to fastDFS fail");       // 失败信息
            fileDto.setUrl(null);
        } else {
            fileDto.setSuccess(1);      // Editor.md规定成功success设置为1
            fileDto.setMessage("success");
            fileDto.setUrl(imageServerUrl + resourceUrl);       // 设置客户端浏览器访问图片的URL
        }

        return fileDto;
    }

}
