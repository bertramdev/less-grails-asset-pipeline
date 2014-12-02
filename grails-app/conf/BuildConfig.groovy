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
		compile 'com.bertramlabs.plugins:less-asset-pipeline:2.0.8'
	}

	plugins {
		runtime ":asset-pipeline:2.0.8"

		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
