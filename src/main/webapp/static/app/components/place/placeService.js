"use strict";

var placeServices = angular.module('placeServices', ['ngResource']);

placeServices.factory('placeService', ['$http', '$q',
    function ($http, $q) {
        return {
            getPlaces: function () {
                return $http.get("http://127.0.0.1/api/places", config)
                    .then(function (response) {
                        return response.data
                    });
            },

            save: function (place) {
                return $http.post('http://127.0.0.1/api/places', JSON.stringify(place), config)
                    .then(function (response) {
                        return response.data
                    });
            }
        };
    }
]);