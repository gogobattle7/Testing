package com.example.demo.compiler;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class JasperCompiler {

    public static void compileJRXMLToJasper(String jrxmlFilePath) {
        System.out.println("compiler execute");
        try {
            File file = new File(jrxmlFilePath);
            if (!file.exists()) {
                throw new FileNotFoundException("JRXML file not found at path: " + jrxmlFilePath);
            }

            // Get the name of the file without the extension
            String fileName = file.getName();
            int pos = fileName.lastIndexOf(".");
            if (pos > 0) {
                fileName = fileName.substring(0, pos);
            }

            // Compile JRXML to Jasper
            JasperCompileManager.compileReportToFile(jrxmlFilePath, "src/main/resources/" + fileName + ".jasper");

            System.out.println("Compilation Successful!");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }
}