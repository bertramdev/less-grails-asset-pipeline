grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		grailsPlugins()
		mavenCentral()
	}

	plugins {

		compile ":asset-pipeline:0.7.0"


		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
