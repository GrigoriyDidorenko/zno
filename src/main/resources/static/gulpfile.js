var gulp = require('gulp');
var cleanCSS = require('gulp-clean-css');
var sass = require('gulp-sass');
var concat = require('gulp-concat');

var browserSync = require('browser-sync');

gulp.task ('css', function() {
    return gulp.src('src/css/style.sass')
        .pipe(sass())
        .pipe(cleanCSS({compatibility: 'ie8'}))
        .pipe(gulp.dest('src/css'))
        .pipe(browserSync.stream());
});


gulp.task('server', ['css'], function() {
    browserSync({
        server: {
            baseDir: ''
        },
        port: 8080
    });

    gulp.watch(["src/css/*"], ['css']);
    gulp.watch("*.html").on('change', browserSync.reload);

});

gulp.task('default', ['server']);