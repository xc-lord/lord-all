(function() {
    'use strict';

    var log4js = require('log4js');

    var _level;

    var logger = function(category) {
        category = category || 'default';

        return {
            configure: function(config) {
                if (config) {
                    log4js.configure(config);
                }
            },
            level: function(level) {
                if (level) {
                    _level = level;
                }
            },
            trace: function(msg) {
                var log;

                log = log4js.getLogger(category);
                
                if (_level) {
                    log.level = _level;
                }
                
                log.trace(msg);
            },
            debug: function(msg) {
                var log;

                log = log4js.getLogger(category);

                if (_level) {
                    log.level = _level;
                }

                log.debug(msg);
            },
            info: function(msg) {
                var log;

                log = log4js.getLogger(category);

                if (_level) {
                    log.level = _level;
                }

                log.info(msg);
            },
            warn: function(msg) {
                var log;

                log = log4js.getLogger(category);

                if (_level) {
                    log.level = _level;
                }

                log.warn(msg);
            },
            error: function(msg) {
                var log;

                log = log4js.getLogger(category);

                if (_level) {
                    log.level = _level;
                }

                log.error(msg);
            },
            fatal: function(msg) {
                var log;

                log = log4js.getLogger(category);

                if (_level) {
                    log.level = _level;
                }

                log.fatal(msg);
            }
        };
    };

    module.exports = logger;
})();