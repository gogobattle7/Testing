package com.example.demo.controller;
import com.example.demo.compiler.JasperCompiler;
import com.example.demo.model.FormData;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {

    @GetMapping("/api/report")
    public ResponseEntity<Resource> generateReport(@RequestParam String name, @RequestParam String password, HttpServletResponse response) throws Exception {
        // .jasper 파일 로드
        InputStream inputStream = getClass().getResourceAsStream("/report.jasper");
        // JasperReport 객체 생성
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

        List<FormData>dataList  = new ArrayList<>();
        dataList.add(new FormData());
        // 받아온 데이터를 jasper datasource로 등록
        JRDataSource ds = new JRBeanCollectionDataSource(dataList); //들어가는 데이터 형식을 정하는 것 같고

        // datasource를 매핑해 양식(jrxml)에 맞게 컴파일
        HashMap<String, Object> parameterMap =new HashMap<>();
        parameterMap.put("name", name); // name 매개변수 추가
        parameterMap.put("password", password); // password 매개변수 추가

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap, ds);
        String fileName = "report.pdf";

        // PDF 파일 생성
        File pdfFile = File.createTempFile("report", ".pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

        // 다운로드 링크 생성
        Path path = Paths.get(pdfFile.getAbsolutePath());
        Resource resource = new UrlResource(path.toUri());

        // 다운로드 링크 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}