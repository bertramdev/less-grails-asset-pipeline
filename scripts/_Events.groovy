// includeTargets << new File(assetPipelinePluginDir, "scripts/_AssetCompile.groovy")

eventAssetPrecompileStart = { assetConfig ->
	def lessAssetFile = classLoader.loadClass('asset.pipeline.less.LessAssetFile')
	if(config?.grails?.assets?.less?.compiler == 'less4j') {
		lessAssetFile.compilerMode = 'less4j'	
	}
	assetConfig.specs << 'asset.pipeline.less.LessAssetFile'
}
