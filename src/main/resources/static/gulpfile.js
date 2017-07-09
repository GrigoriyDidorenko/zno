var gulp = require('gulp');
var concatCss = require('gulp-concat-css');
var cleanCSS = require('gulp-clean-css');
var uncss = require('gulp-uncss');
var sass = require('gulp-sass');
var browserSync = require('browser-sync');

gulp.task ('css', function() {
  return gulp.src('css/style.sass')
  	.pipe(sass())
    .pipe(concatCss("styles/style.css"))
    .pipe(cleanCSS({compatibility: 'ie8'}))
    .pipe(uncss({
        html: ['*.html']
    }))
    .pipe(gulp.dest('out'))
    .pipe(browserSync.stream());
});

gulp.task('server', function() {
    browserSync({
        server: {
            baseDir: ''
        },
        port: 8080
    });

    gulp.watch(["css/*"], ['css']);
    gulp.watch("*.html").on('change', browserSync.reload);

});

gulp.task('default', ['server']);