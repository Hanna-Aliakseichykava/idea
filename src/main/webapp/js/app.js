'use strict';
/**
 * @name ideaApp
 *
 * @description
 * A main module.
 */
 
angular
	.module('ideaApp', [
		'app.directives',
		'app.services',
		'app.filters', 
		'app.testData',
		'app.Controller'
	]);
	
	angular.module('app.directives', []); // set Directives
	angular.module('app.services', []); // set Services
	angular.module('app.Controller', []); // set Ctrls
	angular.module('app.filters', []); // set Filters
