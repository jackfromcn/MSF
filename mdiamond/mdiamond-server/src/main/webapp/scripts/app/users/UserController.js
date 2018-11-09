/**
 * Created by hengyunabc on 15-5-21.
 */

'use strict';

angular.module('xdiamondApp').controller("UserController", ['$scope', '$state', 'users', 'UserService', '$modal',
    function ($scope, $state, users, UserService, $modal) {
        console.log('UserContoller....');
        //TODO user要排好序
        $scope.users = users;

        $scope.user = {provider: 'standard'};

        $scope.create = function () {
            UserService.create($scope.user).then(function(){
                $state.reload();
            });
            $scope.user = {provider: 'standard'};
        }

        $scope.delete = function (userId) {
            UserService.delete(userId).then(function(){
                $state.reload();
            });
        }

        $scope.popNewUserModal = function (size) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'scripts/app/users/users.new.html',
                controller: 'UserNewController',
                size: size
            });
        };

        $scope.popUpdateUserModal = function (user, size) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'scripts/app/users/users.update.html',
                controller: 'UserUpdateController',
                size: size,
                resolve: {
                    user: function () {
                        return user;
                    }
                }
            });
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


angular.module('xdiamondApp').controller("UserUpdateController",
    ['$scope', '$state', '$modal', '$modalInstance', 'UserService', 'user',
        function ($scope, $state, $modal, $modalInstance, UserService, user) {
            user.password = undefined;
            user.passwordSalt = undefined;
            $scope.user = user;

            $scope.update = function () {
                UserService.patch(user).then(function () {
                	/* UserService.patch(user).then(function () {*/
                		 $scope.showAlert();

                            $state.reload();
/*                     })
                     $modalInstance.close();*/
                })
                $modalInstance.close();
            }

            $scope.ok = function () {
                $modalInstance.close();
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            
           $scope.showAlert = function () {
                  var alert = $modal.open({
                      animation: true,
                      title: '提示', 
                      backdrop: false,
                      controller:"alertController",
                      templateUrl: 'scripts/app/users/changeUserMessageDialog.html',
                  });
              }  
              
              
        }]);
angular.module('xdiamondApp').controller("alertController",
	    ['$scope', '$state', '$modal', '$modalInstance',
	        function ($scope, $state, $modal, $modalInstance) {
   		    setTimeout(function () { 
 	    	$modalInstance.close();
		    }, 1500);
            $scope.ok = function () {
                $modalInstance.close();
            };
	    }]);

angular.module('xdiamondApp').controller("UserUpdatePasswordController",
    ['$scope', '$state', '$modal', '$modalInstance', 'UserService', 'user',
        function ($scope, $state, $modal, $modalInstance, UserService, user) {
            $scope.user = user;

            $scope.update = function () {
                UserService.patch(user).then(function () {
                	 $scope.showAlert();
                    $state.reload();
                })
                $modalInstance.close();
            }

            $scope.ok = function () {
                $modalInstance.close();
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            $scope.showAlert = function () {
                var alert = $modal.open({
                    animation: true,
                    title: '提示', 
                    backdrop: false,
                    controller:"alertController",
                    templateUrl: 'scripts/app/users/changePwdDialog.html',
                });
            } 
        }]);