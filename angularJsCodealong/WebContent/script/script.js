/**
 * Author: Gustaf Peter Hultgren
 */
/* Wrap in the code to avoid global scope. */
(function () {

var myApp = angular.module('myApp', []);

var myController = function ($scope) {
  $scope.name = "Peter";
  
  var person = {
    firstName: "Gustaf Peter",
    lastName: "Hultgren"
  };
  
  $scope.person = person;
};

var secondController = function($scope) {
  $scope.day = "Wednesday";
  $scope.fullName = "Text";
};

myApp.controller("minController", ['$scope', myController]);
myApp.controller("secondController", ['$scope', secondController]);

})();
