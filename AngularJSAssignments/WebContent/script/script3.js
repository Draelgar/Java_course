/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	var app = angular.module("dbApp", []);
	
	var controller = function($scope) {
		$scope.textString = "Enter Text";
		$scope.reverseText = $scope.textString.split('').reverse().join('');
		
		$scope.update = function() {
			$scope.reverseText = $scope.textString.split('').reverse().join('');
		}
	};
	
	app.controller("dbController", ["$scope", controller]);
	
}());