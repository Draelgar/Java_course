/**
 * This java script is for displaying the fibonacci algorithm.
 */

fibonacci = function(a, b, n) {
	if(n <= 1 && n >=0)
		return n;
	else if(n > 1) {
		var limit = Number.MAX_SAFE_INTEGER;
		var first = 0;
		if(a < 0) {
			first = fibonacci(n - 1);
			if(first > limit || first == -1)
				return -1; 
		}
		else {
			first = a;
		}
		
		var second = 0;
		if(b < 0) {
			second = fibonacci(n - 2);
			if(second > limit || second == -1)
				return -1;
		}
		else {
			second = b;
		}
		
		return first + second;
	}
}

run = function(end) {	
	var result = [-1];
	var f = 0;
	for(i = 0; i <= end; i++){
		
		var a = -1;
		if(i >= 1)
			a = result[i];
		
		var b = -1;
		if(i >= 2)
			b = result[i - 1];	
		
		f = fibonacci(a, b, i);
		if(f != -1)
			result.push(f);
		else
			break;
	}
	
	var string = "" + result[1];
	
	for(i = 2; i < result.length; i++) {
		string += ", " + result[i];
	}
	
	// Display the results.
	document.getElementById("results").innerHTML = string;
}

/* Triggers when the start button is clicked. */
document.getElementById("fibonacci").onclick = function() {
	var itString = document.getElementById("fibit").value;
	if(itString == "")
		itString = document.getElementById("fibit").placeholder;
	
	var iterations = parseInt(itString);
	run(iterations);
}
