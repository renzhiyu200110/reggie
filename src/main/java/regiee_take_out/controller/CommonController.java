package regiee_take_out.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import regiee_take_out.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

//进行文件上传下载
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
//        file是一个临时文件，需要转存到指定位置
        log.info("上传文件{}",file.toString()  ) ;
//        原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
//        使用uuid生成文件名，防止文件名称重复
        String filename= UUID.randomUUID().toString()+suffix;
//        创建目录检查是否存在
        File dir=new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try{
            file.transferTo(new File(basePath+filename));
//            file.transferTo(new File("E:\\aaa.jpg"));
        }catch (IOException e){

            e.printStackTrace();
        }
        return R.success(filename) ;
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流读取文件内容
            FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));
            //        输出流写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len=0;
            byte[]bytes=new byte[1024];
            while((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
