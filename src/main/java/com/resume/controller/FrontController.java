package com.resume.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * TODO()
 *
 * @author: lichaohao
 * @date: 2019-08-02 10:08
 */
@Controller
@RequestMapping("/front/")
public class FrontController {

    @RequestMapping("/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/downLoad")
    @ResponseBody
    public JSONObject downLoad(HttpServletRequest request,HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        System.out.println("------------------------------------");
        // String realPath = request.getServletContext().getRealPath("/static");
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(path);
        File file = new File(path + "resume.pdf");
        if (file.exists() && file.isFile()) {
            System.out.println("存在这个文件");
            // 配置文件下载
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + "lchresume.pdf");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bis != null) {
                        try {
                            bis.close();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                jsonObject.put("code", 0);
            }
        } else {
            System.out.println("不存在这个文件");
            jsonObject.put("code", 1);
        }
        return jsonObject;
    }
}
