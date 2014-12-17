'use strict';

var DetroitPastApp = angular.module('DetroitPastApp', [
    'ngRoute',
    'placeControllers',
    'placeServices',
    'userControllers',
    'userServices'
]);

var config = {
    headers: {
        'Accept': 'application/json;version=1',
        'Content-Type': 'application/json'
    }
};

DetroitPastApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/places', {
                templateUrl: 'app/components/place/place.html',
                controller: 'PlaceCtrl'
            }).
            when('/register', {
                templateUrl: 'app/components/user/user.html',
                controller: 'UserCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
}]);

var apiURL = "http://localhost:8080";