'use strict';

angular.module('myApp').factory('PageService', ['$http', '$q', function ($http, $q) {

    var URL = "http://localhost:8090/";

    return {
        findWikiPage: findWikiPage,
        findPage: findPage
    };

    function findWikiPage(search) {
        var deferred = $q.defer();
        $http.get(URL+"wiki?s="+search).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function findPage(url) {
        var deferred = $q.defer();
        $http.get(URL +"/p?url="+ url).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

}]);
