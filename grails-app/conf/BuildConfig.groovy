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
		compile "com.github.sommeri:less4j:1.3.0"
	}

	plugins {

		runtime ":asset-pipeline:1.7.0"


		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
