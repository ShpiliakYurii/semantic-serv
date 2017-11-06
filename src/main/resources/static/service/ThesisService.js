'use strict';

angular.module('myApp').factory('ThesisService', ['$http', '$q', function ($http, $q) {

    var DOMAIN = "http://localhost:8090";
    var URL = DOMAIN + "/api/thesis";

    return {
        add: add,
        save: save,
        // findAllByDomain: findAllByDomain,
        removeById: removeById
    };

    function add(thesis) {
        var deferred = $q.defer();
        $http.post(URL, thesis).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function save(thesis) {
        var deferred = $q.defer();
        $http.put(URL, thesis).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    // function findAllByDomain(domain) {
    //     var deferred = $q.defer();
    //     $http.get(URL + "/domain?url=" + domain).then(
    //         function (response) {
    //             deferred.resolve(response.data);
    //         },
    //         function (errResponse) {
    //             console.error(errResponse.toString());
    //             deferred.reject(errResponse);
    //         });
    //     return deferred.promise;
    // }

    function removeById(id) {
        var deferred = $q.defer();
        $http.delete(URL + "/" + id).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function checkIfExist(concepts, concept) {
        var conceptLowerCase = concept.toLowerCase();
        for (var i = 0; i < concepts.length; i++) {
            if (concepts[i].concept.toLowerCase() === conceptLowerCase) {
                return true;
            } else {
                var forms = concepts[i].forms.split('; ');
                for (var j = 0; j < forms.length; j++) {
                    if (forms[j].toLowerCase() === conceptLowerCase)
                        return true;
                }
            }
        }
    }

}]);
