/**
 * Assignment 10 - FizzBuzz
 */

/* Checks the value and returns it or returns fizz, buzz or fizzbuzz 
 * if the value can be divided by any or both of the given parameters.*/
fizz_buzz = function(value, fizz, buzz) {
	var result = 0;
	if(value % fizz == 0)
		result++;
	if(value % buzz == 0)
		result+=2;
	
	if(result == 3)
		return "FizzBuzz";
	else if(result == 2)
		return "Buzz";
	else if(result == 1)
		return "Fizz";
	else
		return value.toString();
}

/* Start the algorithm. */
begin = function(start, stop, fizz, buzz) {
	var values = [];
	
	for(i = start; i <= stop; i++) {
		values.push(fizz_buzz(i, fizz, buzz));
	}
	
	return values;
}

/* Triggers when the start button is clicked. */
document.getElementById("button").onclick = function() {
	var value = document.getElementById("start").value;
	if(value == "")
		value = document.getElementById("start").placeholder;
		
	var start = parseInt(value, 10);
	
	value = document.getElementById("end").value;
	if(value == "")
		value = document.getElementById("end").placeholder;
	
	var end = parseInt(value, 10);
	
	value = document.getElementById("fizz").value;
	if(value == "")
		value = document.getElementById("fizz").placeholder;
	
	var fizz = parseInt(value, 10);
	
	value = document.getElementById("buzz").value;
	if(value == "")
		value = document.getElementById("buzz").placeholder;
	
	var buzz = parseInt(value, 10);
	
	// Run the algorithm.
	var results = begin(start, end, fizz, buzz);
	
	// Loop through the list and convert it to a single string.
	var list = "";
	for(i = 0; i < results.length; i++) {
		if(i > 0)
			list += ", "
				
		list += results[i];
	}
	
	// Display the results.
	document.getElementById("results").innerHTML = list;
}


