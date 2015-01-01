'use strict';

var DetroitPastApp = angular.module('DetroitPastApp', [
    'ui.bootstrap',
    'ngRoute',
    'placeControllers',
    'placeServices',
    'userControllers',
    'userServices',
    'alertControllers',
    'alertServices'
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

DetroitPastApp.factory('requestResponseInterceptor', function ($q, $injector) {
    return {
        // On request success
        request: function (config) {
            var alertService = $injector.get('alertService');
            alertService.closeAlerts();
            // Return the config or wrap it in a promise if blank.
            return config || $q.when(config);
        },

        // On request failure
        requestError: function (rejection) {
            // Return the promise rejection.
            return $q.reject(rejection);
        },

        // On response success
        response: function (response) {
            // Return the response or promise.
            return response || $q.when(response);
        },

        // On response failure
        responseError: function (rejection) {
            var alertService = $injector.get('alertService');
            if(rejection.status === 500) {
                alertService.add("danger", rejection.data);
            }

            // Return the promise rejection.
            return $q.reject(rejection);
        }
    };
});

DetroitPastApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('requestResponseInterceptor');
}]);

var apiURL = "http://localhost:8080";