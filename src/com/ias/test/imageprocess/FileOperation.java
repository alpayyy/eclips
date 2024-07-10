package com.ias.test.imageprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileOperation {
	public static void WriteText(String path, String text) {
		try {
			File file = new File(path);
			if(!file.getParentFile().exists()) {
				Files.createDirectory(Paths.get(file.getParent()));
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
