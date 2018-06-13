package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class PropUtils {

	private static Properties properties = null;
	private PropUtils() {
	}

	private static synchronized void initialize(String prop) {
		FileInputStream is=null;
		try {
			is = new FileInputStream(prop);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (is == null) {
		    System.out.println("The prop is null.");
			return;
		}
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
		    System.out.println("properties loading fails.");
			throw new RuntimeException(e);
		}finally{
			try{
				if(is!=null)
					is.close();
			}catch(Exception ex){
			    System.out.println("properties loading fails for runtime exception.");
				throw new RuntimeException(ex);
			}
		}
	}

	public static synchronized Properties getProperties(String prop)
			throws RuntimeException {
		initialize(prop);
		return properties;
	}
	
	public static String getFormatString(String value, Object[] params) {
	    if (params != null) {
	    	MessageFormat mf = new MessageFormat(value);
	    	value = mf.format(params);
	    }
	    return value;
	}
	
	public static void main(String[] args){
		Properties p = getProperties("C:\\007StarProject\\star-auto-test\\src\\test\\java\\resources\\message.properties");
		System.out.print(p.get("login.invalidusrpwd"));
	}
}
