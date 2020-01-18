package life.community.community.controllers;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import life.community.community.dto.FileDto;
import life.community.community.provider.FastDFSProvider;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUploadController {

    @Autowired
    private FastDFSProvider fastDFSProvider;

    @Value("${remote.image.url}")
    private String url;

    @Value("${fdfs.web-server-url}")
    private String imageServerUrl;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto fileUploadHandle(@RequestParam("guid") String guid,
                                    @RequestParam("editormd-image-file") MultipartFile multipartFile) throws IOException {
        FileDto fileDto = new FileDto();

//        使用本地存储，PUT方式保存，WebResource才要打开
//        String filename = UUID.randomUUID().toString().replaceAll("-", "_")
//                + "__"
//                + multipartFile.getOriginalFilename();


//      本地上传 (dev)
//        File file = new File(ResourceUtils.getURL("classpath:static/upload").getPath()  + "/" + filename);
//        try {
//            multipartFile.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//            fileDto.setSuccess(0);
//            fileDto.setMessage("IO fail");
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            fileDto.setSuccess(0);
//            fileDto.setMessage("Status error");
//        }
//
//        fileDto.setSuccess(1);
//        fileDto.setMessage("success");
//        fileDto.setUrl("/upload/" + filename);

//        远程服务器上传
//        try {
//            Client client = Client.create();
//            WebResource resource = client.resource(url + filename);
//            resource.put(multipartFile.getBytes());
//
//            fileDto.setSuccess(1);
//            fileDto.setUrl(url + filename);
//            fileDto.setMessage("upload success");
//        } catch (UniformInterfaceException e) {
//            e.printStackTrace();
//            fileDto.setSuccess(0);
//            fileDto.setMessage("upload fail: Uniform Interface");
//        } catch (ClientHandlerException e) {
//            e.printStackTrace();
//            fileDto.setSuccess(0);
//            fileDto.setMessage("upload fail: cannot connect to image server");
//        } catch (IOException e) {
//            e.printStackTrace();
//            fileDto.setSuccess(0);
//            fileDto.setMessage("upload fail: cannot write image object");
//        }

//        OkHttpClient client = new OkHttpClient();
//        MediaType type = MediaType.get("application/octet-stream");
//
//        RequestBody requestBody = RequestBody.create(multipartFile.getBytes(), type);
//        Request request = new Request.Builder()
//                .url(url + filename)
//                .put(requestBody)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            fileDto.setSuccess(1);
//            fileDto.setMessage("success");
//            fileDto.setUrl(url + filename);
//        } catch (Exception ex) {
//            fileDto.setSuccess(0);
//            fileDto.setMessage(ex.getMessage());
//        }

        String resourceUrl = fastDFSProvider.uploadFile(multipartFile);
        if (StringUtils.isBlank(resourceUrl)) {
            fileDto.setSuccess(0);
            fileDto.setMessage("upload to fastDFS fail");
            fileDto.setUrl(null);
        } else {
            fileDto.setSuccess(1);
            fileDto.setMessage("success");
            fileDto.setUrl(imageServerUrl + resourceUrl);
        }

        return fileDto;
    }

}
