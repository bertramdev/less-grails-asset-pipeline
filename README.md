LESS Grails Asset Pipeline
==========================
The Grails `less-asset-pipeline` is a plugin that provides LESS support for the asset-pipeline static asset management plugin.

For more information on how to use asset-pipeline, visit [here](http://www.github.com/bertramdev/asset-pipeline).


Usage
-----

Simply create files in your standard `assets/stylesheets` folder with extension `.less` or `.css.less`. You also may require other files by using the following requires syntax at the top of each file or the standard LESS import:

```css
/*
*= require test
*= require_self
*= require_tree .
*/

/*Or use this*/
@import 'test'

```

Including Less files into your GSP files is easy but there are a few things worth mentioning. Say we have a file called `application.less`. You would include it into your gsp by its compiled extension instead of its original extension. aka, use `.css` instead of `.less`

```gsp
<head>
  <asset:stylesheet src="application.css"/>
</head>
```

Less4j Support
--------------

This plugin now defaults to compiling your less files with less4j instead of the standard less compiler. To Turn this off you must adjust your config:

```
grails.assets.less.compiler = 'standard'
```


Production
----------
During war build your less files are compiled into css files. This is all well and good but sometimes you dont want each individual less file compiled, but rather your main base less file. It may be best to add a sub folder for those LESS files and exclude it in your precompile config...

Config.groovy:
```groovy
grails.assets.excludes = ["mixins/*.less"]
```
