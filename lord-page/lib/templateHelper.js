(function() {
    'use strict';

    var path = require('path');
    var logger = require('./logger')(path.basename(__filename));

    var templateHelper = {
        imports: function(template) {
            var helper = template.defaults.imports;

            helper.timestamp = function(value) {
                return value;
            };

            helper.toJSON = function(obj) {
                return JSON.stringify(obj);
            };
        }
    };

    module.exports = templateHelper;
})();