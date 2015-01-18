"use strict";

var authenticationServices = angular.module('authenticationServices', ['ngResource']);

authenticationServices.factory('authenticationService', ['$http', '$cookieStore', '$rootScope', '$timeout',
    function ($http, $cookieStore, $rootScope, $timeout) {
        var service = {};
        var authUrl = apiURL;
        service.isAuthenticated = function () {
            return $rootScope.globals.currentUser != undefined
        };
        service.Login = function (username, password, callback) {

            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
            //$timeout(function(){
            //    var response = { success: username === 'test' && password === 'test' };
            //    if(!response.success) {
            //        response.message = 'Username or password is incorrect';
            //    }
            //    callback(response);
            //}, 1000);


            this.SetCredentials(username, password);
            /* Use this for real authentication
             ----------------------------------------------*/
            $http.post(authUrl + '/api/users/authenticate', {username: username, password: password})
                .success(function (response) {
                    console.log("AuthService: Authentication Success, setting credentials");
                    callback(true);
                });

        };

        service.SetCredentials = function (username, password) {
            var authdata = window.btoa(username + ':' + password);
            //var authdata = Base64.encode(username + ':' + password);

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    authdata: authdata
                }
            };

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
        };

        service.ClearCredentials = function () {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        };

        return service;
    }]);