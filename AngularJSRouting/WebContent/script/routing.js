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
		.when("/books", {
			templateUrl : "books.htm",
			controller : "booksController"
		})
		.when("/addbook", {
			templateUrl : "addbook.htm",
			controller : "addBookController"
		})
		.when("/book:id", {
			templateUrl : "book.htm",
			controller : "bookController"
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
	
	app.controller("booksController", function($scope, $http) {
		
		$scope.title = "Books";
		
		$scope.author = function(id) {
			// TODO result of clicking in the author.
		}

		var onBooksComplete = function(response) {
			$scope.books = response.data;
		}
		
		var onError = function(reason) {
			$scope.title = "Could not fetch data: " + reason.status;
		}
		
		$http.get("Books").then(onBooksComplete, onError);
		
	});
	
	app.controller("addBookController", function($scope, $http) {
		
		var onBookAdded = function(response) {
			$scope.title = "Successfully added a book!";
			$scope.books = response.data;
		}
		
		var onError = function(reason) {
			$scope.title = "Could not fetch data: " + reason.status;
		}
		
		$scope.addBook = function() {
			
			var parameters = {'title' : $scope.bookTitle,
					'first_name' : $scope.bookAuthorFirstName,
					'last_name' : $scope.bookAuthorLastName};
			
			var jsonParameters = JSON.stringify(parameters);
			
			$http.post("AddBook", jsonParameters).then(onBookAdded, onError);
			
		}
		
	});
	
	app.controller("bookController", function($scope, $http, $routeParams) {
		
		$scope.title = "Book Info";

		var bookId = $routeParams.id;
		
		var onBookComplete = function(response) {
			$scope.book = response.data;
		}
		
		var onError = function(reason) {
			$scope.title = "Could not fetch data: " + reason.status;
		}
		
		$http.get("Book?id=" + bookId).then(onBookComplete, onError);
		
	});
	
}())