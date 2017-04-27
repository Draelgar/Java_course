/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	
	var app = angular.module("FizzBuzzApp", []);
	
	var controller = function($scope) {
		
		$scope.start = 0;
		$scope.end = 100;
		$scope.fizz = 3;
		$scope.buzz = 4;
		$scope.fizzText = "Fizz";
		$scope.buzzText = "Buzz";
		
		$scope.range = function() {
			var interval = [$scope.start + 1];
			
			for(var i = $scope.start + 2; i <= $scope.end; i++) {
				interval.push(i);
			}
			
			return interval;
		}
		
		$scope.f = function(number) {
			var fizzNum = number % $scope.fizz;
			var buzzNum = number % $scope.buzz;
			
			if(fizzNum == 0 && buzzNum == 0) {
				return $scope.fizzText + $scope.buzzText;
			}
			else if(fizzNum == 0) {
				return $scope.fizzText;
			}
			else if(buzzNum == 0) {
				return $scope.buzzText;
			}
			else {
				return number;
			}
		}
		
		$scope.color = function(value) {
			if(value == $scope.fizzText + $scope.buzzText)
				return {"color":"hsl(360, 100%, 40%)"};
			else if(value == $scope.fizzText)
				return {"color":"hsl(120, 100%, 20%)"};
			else if(value == $scope.buzzText)
				return {"color":"hsl(233, 100%, 40%)"};
			else
				return {"color":"black"};
		}
	};
	
	app.controller("FizzBuzzController", ["$scope", controller]);
	
}())