/**
 * This javaScript file handles simple calculations.
 */

partition = function(formula) {
	var depth = 0;
	var indices = [0];

	for(var i = 0; i < formula.length; i++) {
		var char = formula.charAt(i);
		
		if(char == '(') {
			depth++;
			indices.push(i);
		}
		else if(char == ')' && depth > 0) {
			var substring = formula.substring(indices[depth] + 1, i);
			var value = add(substring);
			var begin = formula.substring(0, indices[depth]);
			var end = formula.substring(i + 1);

			i -= indices[depth] + value.toString().length;
			
			// Replace the block by its calculated value.
			formula = begin + value.toString() + end;
			
			// Remove the index and lower the depth.
			indices.splice(depth, 1);
			depth--;
		}
	}
	
	return formula;
}

divide = function(formula) {
	var values = formula.split("/");
	
	var sum = 0.0;
	
	for(var i = 0; i < values.length; i++) {
		if(i > 0)
			sum /= Number(values[i]);
		else
			sum = Number(values[i]);
	}
	
	return sum;
}

mult = function(formula) {
	var values = formula.split("*");
	var sum = 1.0;
	
	for(var i = 0; i < values.length; i++) {
		if(values[i].indexOf("/") != -1) {
			sum *= divide(values[i]);
		}
		else {
			sum *= Number(values[i]);
		}
	}
	
	return sum;
}

subtract = function(formula) {		
	var minus = formula.split("-");
	
	var sum = 0.0;
	var part = 0.0;
	
	for(var i = 0; i < minus.length; i++) {
		if(minus[i].indexOf("*") != -1) {
			part = mult(minus[i]);
		}
		else if(minus[i].indexOf("/") != -1) {
			part = divide(minus[i]);
		}
		else {
			part = Number(minus[i]);
		}
		
		if(i > 0)
			sum -= part;
		else
			sum = part;
	}
	
	return sum;
}

add = function(formula) {
	var plus = formula.split("+");
	var sum = 0.0;
	
	for(var i = 0; i < plus.length; i++) {
		// If the block contains a minus sign, send it to the subtracter.
		if(plus[i].indexOf("-") != -1) {
			sum += subtract(plus[i]);
		}
		// If the block contains a multiply sign, send it to the multiplier.
		else if(plus[i].indexOf("*") != -1) {
			sum += mult(plus[i]);
		}
		// If the block contains a division sign, send it to the divider.
		else if(plus[i].indexOf("/") != -1) {
			sum += divide(plus[i]);
		}
		// Plain numbers are just added.
		else {
			sum += Number(plus[i]);
		}
	}
	
	return sum;
}

document.getElementById("calc").onclick = function () {
	var formula = document.getElementById("formula").value;
	if(formula == "")
		formula = document.getElementById("formula").placeholder;
	
	// Calculate all parenthesis blocks first.
	formula = partition(formula);
	
	var sum = add(formula);
	
	// Display the results.
	document.getElementById("results").innerHTML = sum;
}