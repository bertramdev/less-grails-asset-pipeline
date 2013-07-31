// includeTargets << new File(assetPipelinePluginDir, "scripts/_AssetCompile.groovy")

eventAssetPrecompileStart = {
	asset.pipeline.AssetHelper.assetSpecs << asset.pipeline.less.LessAssetFile
}
