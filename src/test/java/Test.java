import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {

	
	public static  void main(String[] args) throws UnsupportedEncodingException {
		String keyWord = URLDecoder.decode("", "UTF-8"); 
		System.out.println(keyWord);
	}
	
}
