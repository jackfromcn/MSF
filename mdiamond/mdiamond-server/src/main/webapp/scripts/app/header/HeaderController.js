/**
 * Created by hengyunabc on 15-6-2.
 */

'use strict';

angular.module('xdiamondApp').controller("HeaderController",
    ['$scope', '$state', '$log', '$modal', 'authenticateInfo', 'AuthService', 'UserService',
        function ($scope, $state, $log, $modal, authenticateInfo, AuthService, UserService) {
            console.log('HeaderController....')

            $scope.authenticateInfo = authenticateInfo;

            AuthService.authenticateInfo().then(function (data) {
                $scope.authenticateInfo = data;
            })

            $scope.isLoggedIn = function () {
                //return true;
                return UserService.isLoggedIn;
            }

            $scope.logout = function () {
                UserService.logout().then(function (success) {
                    $scope.authenticateInfo = {};
                    console.log(success);
                    $log.info('logout:' + success);
                    $state.go('main');
                })
            }

            $scope.popUpdateUserPasswordModal = function (user, size) {
                var modalInstance = $modal.open({
                    animation: true,
                    templateUrl: 'scripts/app/users/users.updatepassword.html',
                    controller: 'UserUpdatePasswordController',
                    size: size,
                    resolve: {
                        user: function () {
                            var u = {id: user.id};
                            return u;
                        }
                    }
                });
            }
            
        }]);

