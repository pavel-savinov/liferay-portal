'use string';

const metal = require('gulp-metal');
const srcBlob = 'src/**/*.es.js';
const testBlob = 'src/**/*.js';

metal.registerTasks({
	buildSrc: [srcBlob],
	formatGlobs: [srcBlob, testBlob],
	lintGlobs: [srcBlob, testBlob],
});