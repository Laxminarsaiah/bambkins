package com.lnragi.tools.bambkins;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileCreater {

	public static void main(String[] args) {
		FileCreater fc = new FileCreater();
		try {
			// fc.saveSession("1.1.1.1.3", "laxmi", "password", "26");
			System.out.println(fc.readSession("1.1.1.1.3"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readSession(String sessionName) throws IOException {
		sessionName = "D:\\bambkins\\sessions\\" + sessionName;
		System.out.println(sessionName);
		BufferedReader br = Files.newBufferedReader(Paths.get(sessionName));
		Stream<String> lines = br.lines();
		return lines.findFirst().get();
	}

	public String[] readSessions() throws IOException {
		File dir = new File("D:\\bambkins\\sessions");
		String[] files = null;
		if (dir.exists()) {
			files = dir.list();
		}
		return files;
	}

	public void saveSession(String host, String username, String password, String port) throws IOException {
		String filePath = FileCreater.createDir(host);
		String fileContent = host + "," + username + "," + password + "," + port;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		writer.write(fileContent);
		writer.close();
	}

	private static String createDir(String fileName) throws IOException {
		File dir = new File("D:\\bambkins\\sessions");
		if (!dir.exists()) {
			if (dir.mkdirs()) {
				System.out.println("New dirc craeted in new dir");
				return createNewFile(dir, fileName);
			}
		} else {
			return createNewFile(dir, fileName);
		}
		return null;
	}

	private static String createNewFile(File dir, String fileName) throws IOException {
		File newFile = new File(dir + File.separator + fileName);
		if (!newFile.exists()) {
			if (newFile.createNewFile()) {
				System.out.println("New File craeted in new dir" + newFile.getAbsolutePath());

			}
		}
		return newFile.getAbsolutePath();
	}
}