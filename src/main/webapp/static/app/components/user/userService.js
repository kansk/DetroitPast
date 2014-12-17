"use strict";

var userServices = angular.module('userServices', ['ngResource']);

userServices.factory('userService', ['$http', '$q',
    function ($http, $q) {
        return {
            save: function (user) {
                return $http.post(apiURL + '/api/users/register', JSON.stringify(user), config)
                    .then(function (response) {
                        return response.data
                    });
            }
        };
    }
]);