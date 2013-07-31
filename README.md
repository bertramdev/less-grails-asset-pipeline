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
