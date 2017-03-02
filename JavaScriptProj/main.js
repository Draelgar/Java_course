
function hello(name) {
	var text = "Hello " + name;
	
	return text;
}

function mult(a, b) {
	var sum = a * b;
	
	return sum;
}

function math(a, b) {
	var sum = a * b;
	var text = a + " * " + b + " = " + sum;
	return text;
}

function hexa(value) {
	var hex = "0x" + value.toString(16);
	return hex;
}