/**
 * 
 */
class Fish {
		constructor(name, type, age, weight, length) {
			this.name = name;
			this.type = type;
			this.age = age;
			this.weight = weight;
			this.length = length;
		}
	}

var module = angular.module('fish_app', []);
module.controller('fish_controller', function($scope, $http) {
	
	$http.get("FetchDataServlet").then(function (response) {
		//var rows = response.data.split(":");
        //$scope.debugData = response;
        
        var fishes = JSON.parse(response);
        
        $scope.debugData = fishes.data[0].name;
        
        /*for(var i = 0; i < rows.length; i++) {
        	var values = rows[i].split(",");
        	fishes.push(new Fish(values[0], values[1], values[2], values[3], values[4]));
        }*/
        
        $scope.fishes = fishes;
    });
	
});