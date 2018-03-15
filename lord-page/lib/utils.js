(function() {
    'use strict';

    var path = require('path');
    var logger = require('./logger')(path.basename(__filename));

    var utils = {
        isFunction: function(func) {
            var self = this;
            return '[object Function]' === self.typeof(func);
        },
        notFunction: function(func) {
            return !this.isFunction(func);
        },
        isString: function(str) {
            var self = this;
            return '[object String]' === self.typeof(str);
        },
        notString: function(str) {
            return !this.isString(str);
        },
        isArray: function(arr) {
            var self = this;
            return '[object Array]' === self.typeof(arr);
        },
        notArray: function(arr) {
            return !this.isArray(arr);
        },
        isObject: function(obj) {
            var self = this;
            return '[object Object]' === self.typeof(obj);
        },
        notObject: function(obj) {
            return !this.isObject(obj);
        },
        isNumber: function(num) {
            var self = this;
            return '[object Number]' === self.typeof(num);
        },
        notNumber: function(num) {
            return !this.isNumber(num);
        },
        toLowerCase: function(str) {
            return str.toLowerCase();
        },
        typeof: function(o) {
            /**
             *  [object Function]
             *  [object String]
             *  [object Array]
             *  [object Object]
             *  [object Number]
             *  [object Null]
             *  [object Undefined]
             */
            return Object.prototype.toString.call(o);
        }
    };

    module.exports = utils;
})();