/**
 * This file counts letters and words.
 */

document.getElementById("wl").onclick = function() {
	var phrase = document.getElementById("phrase").value;
	if(phrase == "")
		phrase = document.getElementById("phrase").placeholder;
	
	document.getElementById("results").innerHTML = "The phrase "
								+ "\"" + phrase + "\""
								+ " has " + countWords(phrase) + " words "
								+ " & " + countLetters(phrase) + " letters!";
}

document.getElementById("words").onclick = function() {
	var phrase = document.getElementById("phrase").value;
	if(phrase == "")
		phrase = document.getElementById("phrase").placeholder;
	
	document.getElementById("results").innerHTML = "The phrase "
								+ "\"" + phrase + "\""
								+ " has " + countWords(phrase) + " words!";
}

countWords = function(phrase) {
	var begun = false;
	var count = 0;
	var string = phrase.toLowerCase();
	
	for(i = 0; i < phrase.length; i++) {
		var character = string.charAt(i);
		if((character >= 'a' && character <= 'z') ||
				character == 'å' || character == 'ä' || character == 'ö' ||
				(character >= '0' && character <= '9')) {
			
			begun = true;
		}
		else if(begun) {
			count++;
			begun = false;
		}
	}
	
	return count;
}

document.getElementById("letters").onclick = function() {
	var phrase = document.getElementById("phrase").value;
	if(phrase == "")
		phrase = document.getElementById("phrase").placeholder;
	
	document.getElementById("results").innerHTML = "The phrase "
								+ "\"" + phrase + "\""
								+ " has " + countLetters(phrase)
								+ " letters!";
}

countLetters = function(phrase) {
	var string = phrase.replace(/[^A-Z|^Å|^Ä|^Ö]/gi, "");
	return string.length;
}