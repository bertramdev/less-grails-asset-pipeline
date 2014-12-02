grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		grailsPlugins()
		mavenCentral()
		jcenter()
	}

	dependencies {
<<<<<<< HEAD
		compile 'com.bertramlabs.plugins:less-asset-pipeline:2.0.7'
=======
		compile "com.github.sommeri:less4j:1.8.2"
        compile "com.github.sommeri:less4j-javascript:0.0.1"
>>>>>>> 3733184f7f45eba6175e64143b9d7be0d4b9f74a
	}

	plugins {
		runtime ":asset-pipeline:2.0.8"

		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
