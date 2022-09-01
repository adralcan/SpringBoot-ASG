var gulp = require('gulp');
var babel = require('gulp-babel');
var scripts = {
    origin : "./src/main/webapp/resources/scripts/"
};
gulp.task('transpileES6toES5', async function () {
    return gulp.src(scripts.origin + "index.js")
        .pipe(babel({presets: ['@babel/preset-env'] }))
        .pipe(gulp.dest(scripts.origin + "wro"));
});

module.exports = {
 default: gulp.series('transpileES6toES5')
};