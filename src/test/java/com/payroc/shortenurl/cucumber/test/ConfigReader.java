package com.payroc.shortenurl.cucumber.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

	
	public static final Properties getConfiguration()  {
		Properties prop = new Properties();
		String[] pathNames = { "src", "test", "resources", "rest-assured.properties" };
		String path = String.join(File.separator, pathNames);
		
		try(FileReader fr = new FileReader(new File(path))){

		prop.load(fr);
		
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return prop;
	}
	

}
