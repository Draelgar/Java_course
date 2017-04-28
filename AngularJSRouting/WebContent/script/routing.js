/**
 * 
 */

(function() {
	
	var app = angular.module("routingApplication", ["ngRoute"]);
	
	app.config(function($routeProvider) {
		
		$routeProvider
		.when("/", {
			templateUrl : "main.htm", //"home.htm",
			controller : "homeController"
		})
		.when("/about", {
			templateUrl : "main.htm", //"about.htm",
			controller : "aboutController"
		})
		.when("/contact", {
			templateUrl : "main.htm", //"contact.htm",
			controller : "contactController"
		})
		.otherwise({ redirectTo : "/" });
		
	});
	
	app.controller("homeController", function($scope) {
		
		$scope.title = "Home";
		$scope.description  = "This is the home page, presented by angular routing.";
		
	});
	
	app.controller("aboutController", function($scope) {
			
			$scope.title = "About";
			$scope.description  = "This is the about page, presented by angular routing.";
			
		});

	app.controller("contactController", function($scope) {
		
		$scope.title = "Contact";
		$scope.description  = "This is the contact page, presented by angular routing.";
		
	});
	
}())