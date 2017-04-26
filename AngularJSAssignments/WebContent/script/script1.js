/**
 * Author: Gustaf Peter Hultgren.
 */

/* Leave global scope. */
(function () {
	/* Define the angular module. */
	var angularApp = angular.module("storeApp", []);
	
	var storeController = function($scope) {
		var item = {
			name: "Starcraft",
			price: "199",
			description: "A computer game."
		};
		
		$scope.item = item;
	};
	
	angularApp.controller("storeController",['$scope', storeController]);
	
}());