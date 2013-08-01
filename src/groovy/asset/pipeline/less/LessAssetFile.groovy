package asset.pipeline.less
import asset.pipeline.processors.CssProcessor
class LessAssetFile {
	static final String contentType = 'text/css'
	static extensions = ['less', 'css.less']
	static final String compiledExtension = 'css'
	static processors = [LessProcessor,CssProcessor]

	File file

	LessAssetFile(file) {
		this.file = file
	}

	def processedStream() {
		def fileText = file?.text
		for(processor in processors) {
			def processInstance = processor.newInstance()
			fileText = processInstance.process(fileText, this)
		}
		return fileText
		// Return File Stream
	}

	def directiveForLine(line) {
		line.find(/\*=(.*)/) { fullMatch, directive -> return directive }
	}
}
