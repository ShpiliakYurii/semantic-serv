'use strict';


angular.module('myApp').controller("DictionaryController", [
    '$scope',
    '$rootScope',
    '$mdDialog',
    'ConceptService',
    '$sce',
    'ConceptInThesisService',
    'MainService',
    function ($scope, $rootScope, $mdDialog, ConceptService, $sce, ConceptInThesisService, MainService) {
        ConceptInThesisService.getAll().then(function (response) {
            $scope.initGraph(response);
        });

        $scope.initGraph = function (response) {
            var container = document.getElementById('dictionary');
            var nodes = [];
            for (var i = 0; i < response.nodes.length; i++) {
                var flag = true;
                for (var j = 0; j < nodes.length; j++) {
                    if (nodes[j].id === response.nodes[i].id) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    nodes.push(response.nodes[i]);
            }
            var data = {
                nodes: response.nodes,
                edges: response.edges
            };
            var options = {
                nodes: {
                    shape: 'dot',
                    scaling: {
                        min: 10,
                        max: 30
                    },
                    font: {
                        size: 12,
                        face: 'Tahoma'
                    }
                },
                edges: {
                    color: {inherit: true},
                    width: 0.15,
                    smooth: {
                        type: 'continuous',
                        roundness: 1
                    }
                },
                interaction: {
                    tooltipDelay: 200
                },
                physics:{
                    enabled: false
                }
            };
            var network = new vis.Network(container, data, options);
            network.on('doubleClick', function (properties) {
                var ids = properties.nodes;
                if (ids.length > 0)
                    window.open('http://rd-client-side.com/concept.html?id=' + ids[0], '_blank');
            });
        };

        ConceptService.getAllOrderByConcept().then(function (data) {
            var concepts = data;
            var a = {
                label: concepts[0].concept[0],
                concepts: []
            };
            for (var i = 0; i < concepts.length; i++) {
                if (concepts[i].concept[0] !== a.label) {
                    $scope.abcConcepts.push(a);
                    a = {
                        label: concepts[i].concept[0],
                        concepts: []
                    };
                }
                a.concepts.push(concepts[i]);
            }
            $scope.abcConcepts.push(a);
            console.log($scope.abcConcepts);
        });

        $scope.abcConcepts = [];
        $scope.prepareUrl = function (page) {
            return MainService.prepareUrl(page);
        };

    }
]);
