"use strict";

var placeServices = angular.module('placeServices', ['ngResource']);

placeServices.factory('placeService', ['$http', '$q',
    function ($http, $q) {
        return {
            getPlaces: function () {
                return $http.get(apiURL + "/api/places", config)
                    .then(function (response) {
                        return response.data
                    });
            },

            save: function (place) {
                return $http.post(apiURL + 'api/places', JSON.stringify(place), config)
                    .then(function (response) {
                        return response.data
                    });
            }
        };
    }
]);