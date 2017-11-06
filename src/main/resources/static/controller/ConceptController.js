'use strict';


angular.module('myApp').controller("ConceptController", [
    '$scope',
    '$rootScope',
    '$mdDialog',
    'ConceptService',
    '$sce',
    'ConceptInThesisService',
    'MainService',
    function ($scope, $rootScope, $mdDialog, ConceptService, $sce, ConceptInThesisService, MainService) {

        $scope.theses = {
            definition: [],
            destination: [],
            entity: [],
            image: [],
            other: []
        };
        $scope.getParameter = function (paramName) {
            var searchString = window.location.search.substring(1),
                i, val, params = searchString.split("&");

            for (i = 0; i < params.length; i++) {
                val = params[i].split("=");
                if (val[0] == paramName) {
                    return val[1];
                }
            }
            return null;
        };

        $scope.id = $scope.getParameter('id');
        ConceptInThesisService.get($scope.id).then(function (data) {
            $scope.initGraph(data);
            $scope.concept = data.concept;
            $scope.prepareTheses($scope.concept.theses);
            var title = document.getElementsByTagName('title')[0];
            title.innerHTML = $scope.concept.concept + '';
        });

        $scope.initGraph = function (response) {
            var container = document.getElementById('dictionary');
            var nodes = [];
            for (var i = 0; i < response.nodes.length; i++) {
                var flag = true;
                for(var j = 0; j < nodes.length; j++){
                    if(nodes[j].id === response.nodes[i].id){
                        flag = false;
                        break;
                    }
                }
                if(flag)
                    nodes.push(response.nodes[i]);
            }
            var data = {
                nodes: nodes,
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
                    enabled: true,
                    barnesHut: {
                        gravitationalConstant: -2000,
                        centralGravity: 0.3,
                        springLength: 95,
                        springConstant: 0.04,
                        damping: 0.09,
                        avoidOverlap: 0
                    },
                    forceAtlas2Based: {
                        gravitationalConstant: -50,
                        centralGravity: 0.01,
                        springConstant: 0.08,
                        springLength: 100,
                        damping: 0.4,
                        avoidOverlap: 0
                    },
                    repulsion: {
                        centralGravity: 0.2,
                        springLength: 200,
                        springConstant: 0.05,
                        nodeDistance: 100,
                        damping: 0.09
                    },
                    hierarchicalRepulsion: {
                        centralGravity: 0.0,
                        springLength: 100,
                        springConstant: 0.01,
                        nodeDistance: 120,
                        damping: 0.09
                    },
                    maxVelocity: 50,
                    minVelocity: 0.1,
                    solver: 'barnesHut',
                    stabilization: {
                        enabled: true,
                        iterations: 1000,
                        updateInterval: 100,
                        onlyDynamicEdges: false,
                        fit: true
                    },
                    timestep: 0.5,
                    adaptiveTimestep: true
                }
            };
            var network = new vis.Network(container, data, options);
        };

        $scope.prepareUrl = function (page) {
            return MainService.prepareUrl(page);
        };

        $scope.prepareTheses = function (theses) {
            for (var i = 0; i < theses.length; i++) {
                // $scope.theses[theses[i].tClass.name.toString()].push(theses[i]);
                // console.log(theses[i].tClass.name);
                switch (theses[i].tClass.name) {
                    case "Definition":
                        $scope.theses.definition.push(theses[i]);
                        break;
                    case "Destination":
                        $scope.theses.destination.push(theses[i]);
                        break;
                    case "Entity":
                        $scope.theses.entity.push(theses[i]);
                        break;
                    case "Image":
                        $scope.theses.image.push(theses[i]);
                        break;
                    case "Other":
                        $scope.theses.other.push(theses[i]);
                        break;
                    default:
                        $scope.theses.other.push(theses[i]);
                        break;
                }
            }
        };


        // var nodes = null;
        // var edges = null;
        // var network = null;
        //
        // function draw() {
        //     nodes = [];
        //     edges = [];
        //     // randomly create some nodes and edges
        //     var data = getScaleFreeNetwork(60);
        //
        //     // create a network
        //     var container = document.getElementById('mynetwork');
        //
        //     var options = {
        //         physics: {
        //             stabilization: false
        //         },
        //         configure: {
        //             filter:function (option, path) {
        //                 if (path.indexOf('physics') !== -1) {
        //                     return true;
        //                 }
        //                 if (path.indexOf('smooth') !== -1 || option === 'smooth') {
        //                     return true;
        //                 }
        //                 return false;
        //             },
        //             container: document.getElementById('config')
        //         }
        //     };
        //     network = new vis.Network(container, data, options);
        // }


    }
]);

















