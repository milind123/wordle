# wordle
Solve Wordle by changing these line in the program

		Set<Character> absentChars = strToSet("scpbthlatnor".toLowerCase());
		String posPresent = "r@@@@";
		String incorrectPos = "2u3e3o3r4r4o";

absentChars --> list of alphabets that are not present in the word
posPresent ---> if you know the position, enter the alphabet at that position, otherwise it will be @
incorrectPos ---> in above example, u is part of the word but it does not come at position 2
                                    e is part of the word but it does not come at position 3
                                    .....
                                    o is part of the word but it does not come at position 4
