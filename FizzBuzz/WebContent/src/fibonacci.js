/**
 * This java script is for displaying the fibonacci algorithm.
 */

fibonacci = function(n) {
	if(n == 0)
		return 0;
	else if(n == 1)
		return 1;
	else if(n > 1)
		return fibonacci(n - 1) + fibonacci(n - 2);
}

run = function(end) {		
	var result = [0, 1];
	
	for(i = 2; i <= end; i++){
		result.push(fibonacci(i));
	}
	
	var string = "0, 1";
	
	for(i = 2; i < result.length; i++) {
		string += ", " + result[i];
	}
	
	// Display the results.
	document.getElementById("results").innerHTML = string;
}

/* Triggers when the start button is clicked. */
document.getElementById("fibonacci").onclick = function() {
	run(30);
}
