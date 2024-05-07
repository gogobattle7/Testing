package com.example.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Files;

public class DownloadController {
    @GetMapping("/api/download")
    public ResponseEntity<byte[]> downloadPDF(@RequestParam String name, @RequestParam String password, HttpServletResponse response) {
        try {
            // PDF 파일 생성 코드 (generateReport 메서드 호출)
            String fileName = "report.pdf"; // 생성된 PDF 파일의 경로

            // 생성된 PDF 파일을 읽어와 byte 배열로 변환
            File file = new File(fileName);
            byte[] data = Files.readAllBytes(file.toPath());

            // 클라이언트에게 다운로드할 수 있는 응답 반환
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
