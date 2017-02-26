import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PigLatinTranslator {
	
	public static void main(String[] args) throws IOException {
		System.out.println("Connect to port 888");
		ServerSocket ss = new ServerSocket(888);
		Socket s = ss.accept();
		System.out.println("Connection Established");
		PrintStream ps = new PrintStream(s.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String str, str1;
			while ((str = br.readLine()) != null) {
				str1 = translatePigLatin(str);
				ps.println(str1);
			}
			ps.close();
			br.close();
			kb.close();
			ss.close();
			s.close();
			System.exit(0);
		}
		
	}

	public static String translatePigLatin(String p) {
		
		StringBuilder output = new StringBuilder();
		int i = 0;
		while (i < p.length()) {

			// Take care of punctuation and spaces
			while (i < p.length() && !isChar(p.charAt(i))) {
				output.append(p.charAt(i));
				i++;
			}
			if (i >= p.length())
				break;

			int begin = i;
			while (i < p.length() && isChar(p.charAt(i))) {
				i++;
			}

			int end = i;
			output.append(translateWord(p.substring(begin, end)));
		}
		return output.toString();
	}

	private static boolean isChar(char c) {
		return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
	}

	private static String translateWord(String word) {
		String presult;
		StringBuilder result = new StringBuilder();
		char first;
		boolean cap = false;
		first = word.charAt(0);
		if ('A' <= first && first <= 'Z') 
		{
			first = Character.toLowerCase(first);
			cap = true;
		} else
			cap = false;
		if (beginsWithVowel(word))
			result = result.append(word + "yay");
		else {
			if (cap) {
				int split = firstVowel(word);
				presult = word.substring(split);
				result.append(Character.toUpperCase(presult.charAt(0))); 
				result.append(word.substring(split + 1) + word.substring(0, split).toLowerCase() + "ay");
			} else {
				int split = firstVowel(word);
				result.append(word.substring(split) + word.substring(0, split) + "ay");
			}
		}
		return result.toString();
	}

	private static boolean beginsWithVowel(String word) {
		String vowels = "aeiou";
		char letter = word.charAt(0);
		return (vowels.indexOf(letter) != -1);
	}

	private static int firstVowel(String word) {
		word = word.toLowerCase();
		for (int i = 0; i < word.length(); i++)
			if (word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i' || word.charAt(i) == 'o'
					|| word.charAt(i) == 'u')
				return i;
		return 0;
	}

}