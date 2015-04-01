package MazeSolverFileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	public static void writeToFile(String sToWrite,String fileNum){
		try { 
			String path = "Maze Pics and Infos\\" + fileNum + ".txt";
			File file = new File(path);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sToWrite);
			bw.close();
 
			System.out.println("Done Saving");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String loadTextFile(String filePath){
        String textToReturn = "";       
        try{
			// OPEN A STREAM TO READ THE TEXT FILE
			FileReader fr = new FileReader("Maze Pics and Infos\\" + filePath + ".txt");
			BufferedReader reader = new BufferedReader(fr);
			// READ THE FILE, ONE LINE OF TEXT AT A TIME
			String inputLine = reader.readLine();
			while (inputLine != null) {
				// APPEND EACH LINE TO THE STRING
				textToReturn += inputLine + "\n";
				// READ THE NEXT LINE
				inputLine = reader.readLine();
			}
        }catch(Exception e){}
        // RETURN THE TEXT
        return textToReturn; 
	}

}
