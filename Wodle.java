import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wodle {
	final static int wordLen = 5;
	public static void main(String[] args) {
		String fileName = "/Users/Milind/Downloads/english3.txt";
				
		Set<Character> absetChars = strToSet("scpbthlatnor".toLowerCase());
		String posPresent = "r@@@@";
		String incorrectPos = "2u3e3o3r4r4o";
				
		Set<Character>  absentList =  absetChars.
									  stream().
									  filter(c -> posPresent.toLowerCase().indexOf(c) == -1).
									  filter(c -> incorrectPos.toLowerCase().indexOf(c) == -1).
									  distinct().
									  collect(Collectors.toSet());
		
		Set<Character> hasCharsList = (incorrectPos + posPresent).toLowerCase().
									  chars().
									  mapToObj( c-> (char) c).
									  filter(c -> c >= 'a' && c <= 'z').
									  distinct().
									  collect(Collectors.toSet());
		
		Map<Integer, Character> posPresentMap = processPosPresent(posPresent.toLowerCase());
		Map<Integer, Character> incorrectPosMap = procesIncorrectPos(incorrectPos.toLowerCase());
				
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.
			filter(word -> word.length() == wordLen).
			filter (word -> !strToSet(word).removeAll(absentList)).
			filter (word -> strToSet(word).containsAll(hasCharsList)).
			filter (word -> checkPositionMap(word, posPresentMap, (chr, val)  -> chr != val)).
			filter (word -> checkPositionMap(word, incorrectPosMap, (chr, val) -> chr == val)).
			forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Set<Character> strToSet(String str) {
		  return str.
		  toLowerCase().
		  chars().
		  mapToObj( c-> (char) c).
		  collect(Collectors.toSet());
	}
	
	private static boolean checkPositionMap(String s, Map<Integer, Character> posPresentMap, BiFunction<Character, Character, Boolean> fun) {		
		Set<Entry<Integer, Character>> entrySet = posPresentMap.entrySet();
		
		for (Entry<Integer, Character> entry : entrySet) {
			Integer pos = entry.getKey();
			Character val = entry.getValue();
			
			if (fun.apply(s.charAt(pos), val))
				return false;
		}		
		return true;	
	}

	private static Map<Integer, Character> procesIncorrectPos(String incorrectPos) {
		Map<Integer, Character> map = new HashMap<>();
		
		for(int i = 0; i < incorrectPos.length(); i++, i++) {
			map.put(incorrectPos.charAt(i) - 48 - 1,  incorrectPos.charAt(i + 1));		
		}
		return map;
	}

	private static Map<Integer, Character> processPosPresent(String posPresent) {
		Map<Integer, Character> map = new HashMap<>();
		
		for (int i= 0; i < posPresent.length(); i++) {
			Character c = posPresent.charAt(i);
			
			if (c != '@')			
				map.put(i, c);
		}		
		return map;
	}
}