// includeTargets << new File(assetPipelinePluginDir, "scripts/_AssetCompile.groovy")

eventAssetPrecompileStart = { assetSpecs ->
	def lessAssetFile = classLoader.loadClass('asset.pipeline.less.LessAssetFile')
	assetSpecs.specs << lessAssetFile
}
