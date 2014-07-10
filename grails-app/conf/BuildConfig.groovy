grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		grailsPlugins()
		mavenCentral()
	}

	dependencies {
		compile "com.github.sommeri:less4j:1.6.0"
        compile "com.github.sommeri:less4j-javascript:0.0.1"
	}

	plugins {
		runtime ":asset-pipeline:1.9.2"

		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
