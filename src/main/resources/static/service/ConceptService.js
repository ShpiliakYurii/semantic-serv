'use strict';

angular.module('myApp').factory('ConceptService', ['$http', '$q', function ($http, $q) {

    var DOMAIN = "http://localhost:8090";
    var URL = DOMAIN + "/api/concept";

    return {
        add: add,
        save: save,
        findAllByPage: findAllByPage,
        removeById: removeById,
        checkIfExist: checkIfExist,
        getAllOrderByConcept: getAllOrderByConcept,
        findAllByOrigin: findAllByOrigin,
        findByConceptStr: findByConceptStr
    };

    function add(concept) {
        var deferred = $q.defer();
        $http.post(URL, concept).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function save(concept) {
        var deferred = $q.defer();
        $http.put(URL, concept).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function findAllByPage(id) {
        var deferred = $q.defer();
        $http.get(URL + "/page/" + id).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

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

    function getAllOrderByConcept() {
        var deferred = $q.defer();
        $http.get(URL + "/ordered").then(
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

    function findAllByOrigin(id) {
        var deferred = $q.defer();
        $http.get(URL + "/origin/" + id).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function findByConceptStr(str) {
        var deferred = $q.defer();
        $http.get(URL + "/search?str=" + str).then(
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
