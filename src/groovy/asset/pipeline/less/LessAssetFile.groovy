package asset.pipeline.less
import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper
import asset.pipeline.processors.CssProcessor

class LessAssetFile {
	static final String contentType = 'text/css'
	static extensions = ['less', 'css.less']
	static final String compiledExtension = 'css'
	static processors = [LessProcessor,CssProcessor]

	File file
	def baseFile
	LessAssetFile(file, baseFile=null) {
		this.file = file
		this.baseFile = baseFile
	}

	def processedStream(precompiler=false) {
		def fileText = file?.text
		def md5
		if(!precompiler) {
			def cache = CacheManager.findCache(file.canonicalPath, fileText)
			if(cache) {
				return cache
			} else {
				md5 = AssetHelper.getByteDigest(fileText.bytes)
			}
		}
		for(processor in processors) {
			def processInstance = processor.newInstance(precompiler)
			fileText = processInstance.process(fileText, this)
		}

		if(!precompiler) {
			CacheManager.createCache(file.canonicalPath,md5,fileText)
		}

		return fileText
		// Return File Stream
	}

	def directiveForLine(line) {
		line.find(/\*=(.*)/) { fullMatch, directive -> return directive }
	}
}
