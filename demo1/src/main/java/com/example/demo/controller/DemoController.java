package com.example.demo.controller;
import com.example.demo.model.FormData;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {
    @GetMapping("/api/report")
    public String generateReport(@RequestParam String name, @RequestParam String password, HttpServletResponse response) throws Exception {
        // 받아온 데이터를 사용하여 보고서를 생성하고 PDF로 변환하는 작업 수행
        // 예를 들어, .jrxml 파일을 .jasper로 컴파일하고, 데이터를 넣어서 PDF를 생성하는 작업을 수행
        List<FormData>arrayList = new ArrayList<>();
        arrayList.add(new FormData());
        // 받아온 데이터를 jasper datasource로 등록
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(arrayList); //들어가는 데이터 형식을 정하는 것 같고
        System.out.println("here1");

        // jasper 컴파일할 양식 설정 - 만들어둔 jrxml 파일 경로 설정
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/report.jrxml"));

        System.out.println("here2");
        // datasource를 매핑해 양식(jrxml)에 맞게 컴파일
        HashMap<String, Object> map =new HashMap<>();
        map.put("name", name); // name 매개변수 추가
        map.put("password", password); // password 매개변수 추가
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        System.out.println("here3");


			JasperExportManager.exportReportToPdfFile(report, "report.pdf");
			return "generated";


    }
}
