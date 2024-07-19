package com.ias.test.imageprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileOperation {
	public static void CreateFiles(String path) {
		 File file = new File(path);
         
         if (!file.exists()) {
             file.mkdirs();
             System.out.println("Dizin oluşturuldu: " + path);
         }
         
	}
    public static void WriteText(String path, String text) {
        try {
            File file = new File(path);
            
            if (!file.getParentFile().exists()) {
               CreateFiles(file.getParent());
            }
            
            FileWriter writer = new FileWriter(file);
            BufferedWriter yaz = new BufferedWriter(writer);
            yaz.write(text);
            yaz.close();
            System.out.println("Yazma İşlemi Başarılı");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
