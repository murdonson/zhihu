package com.xiangyang.zhihu.controller;
import com.xiangyang.zhihu.model.User;
import com.xiangyang.zhihu.util.ReadExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
@Controller
public class FileController {
    @RequestMapping(value = "excel")
    public String excel()
    {
        return "excel";
    }
    @RequestMapping(value = "uploadexcel",method = RequestMethod.POST)
    public void uploadexcel(@RequestParam("file") MultipartFile file
    ,HttpServletRequest request,HttpServletResponse response)
    {
        if(file==null)
        {
            return ;
        }
        String name=file.getOriginalFilename();
        long size=file.getSize();
        if(name==null||name.equals("")||size==0)
        {
            return ;
        }
        try {
            List<List<Object>> bankListByExcel = ReadExcel.getBankListByExcel(file.getInputStream(), name);
            for (List<Object> objectList : bankListByExcel) {
                for (Object o : objectList) {
                  //  User user= (User) o;
                  //  System.out.println(user);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    @RequestMapping("/file")
    public String filetest()
    {
        return "fileupload";
    }


    /**
     *  文件上传
     * @param file
     * @param description
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload1",method = RequestMethod.POST)
    public String upload(@RequestParam("file")MultipartFile file,
                       @RequestParam(value = "description",required = false) String description,
                       HttpServletRequest request)
    {
            if(!file.isEmpty())
            {
                String path="D://images";
                String filename="dj_"+file.getOriginalFilename();

                File filepath=new File(path,filename);
                if(!filepath.getParentFile().exists())
                {
                    filepath.getParentFile().mkdirs();
                }



                BufferedInputStream bin=null;
                BufferedOutputStream bout=null;
                //  将文件输出到硬盘上面
                try {

                    bin=new BufferedInputStream(file.getInputStream());

                    bout=new BufferedOutputStream(new FileOutputStream(filepath));

                    int len=0;
                    byte[] buffer=new byte[1024];
                    while((len=bin.read(buffer))!=-1)
                    {
                        bout.write(buffer,0,len);
                    }


                    bout.flush();
                    bout.close();
                    bin.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            return "success";
    }


    /**
     *    web文件下载
     * @param response
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response)
    {
        File file=new File("d://images//dj_ml_prec.png");
        System.out.println(file.exists());
        ServletOutputStream out=null;
        FileInputStream fin=null;
        //   读文件   然后输出文件
        try {
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode("zhangyihao.png","utf-8"));
            byte[] buffer=new byte[1024];
            fin=new FileInputStream(file);
            int len=0;
            out=response.getOutputStream();
            while((len=fin.read(buffer))!=-1)
            {
                out.write(buffer,0,len);
            }
            out.flush();
            out.close();
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *    选定服务器上的一些文件 然后以压缩包的形式让用户下载
     */
    @RequestMapping("/zipdownload")
    public void zipDownload(HttpServletResponse response)
    {


        //  打包在服务器上面
        File file1=new File("D://images//ml_ndcg.png");
        File file2=new File("D://images//ml_prec.png");
        Map<String,File> map=new HashMap<String,File>();
        map.put(file1.getName(),file1);
        map.put(file2.getName(),file2);
        File ziptempFile=new File("d://temp.zip");
        byte[] buf = new byte[1024];
        int len;
        ZipOutputStream zout=null;
        try {
            zout =new ZipOutputStream(new FileOutputStream(ziptempFile));
            for (Map.Entry<String, File> entry : map.entrySet()) {

                FileInputStream fin=new FileInputStream(entry.getValue());
                zout.putNextEntry(new ZipEntry(entry.getKey()));
                len=0;
                while((len=fin.read(buf))!=-1)
                {
                    zout.write(buf,0,len);
                }

                zout.closeEntry();
                fin.close();;
            }
            zout.close();

            // 从服务器上面下载
            FileInputStream zipInput =new FileInputStream(ziptempFile);
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=images.zip");
            while ((len=zipInput.read(buf))!= -1){
                out.write(buf,0,len);
            }
            zipInput.close();
            out.flush();
            out.close();
            //删除压缩包
            ziptempFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
