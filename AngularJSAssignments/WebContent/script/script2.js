/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	var app = angular.module("shApp", []);
	
	var controller = function($scope) {
		$scope.showText = false;
		$scope.buttonText = "hide";
		
		$scope.toggle = function() {
			$scope.showText = !$scope.showText
			
			if($scope.showText == false)
				$scope.buttonText = "hide";
			else
				$scope.buttonText = "show";
		}
	};
	
	document.getElementById("shButton").onclick = function($scope) {
		val = !val;
	};
	
	app.controller("shController", ['$scope', controller]);
}());