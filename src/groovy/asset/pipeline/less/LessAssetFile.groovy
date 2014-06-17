package asset.pipeline.less

import asset.pipeline.AbstractAssetFile
import asset.pipeline.AssetHelper
import asset.pipeline.CacheManager
import asset.pipeline.processors.CssProcessor

class LessAssetFile extends AbstractAssetFile {
    static final String contentType = 'text/css'
    static extensions = ['less', 'css.less']
    static final String compiledExtension = 'css'
    static processors = [CssProcessor]
    static compilerMode = 'standard'

    String processedStream(precompiler) {
        def fileText
        def skipCache = precompiler ?: (!processors || processors.size() == 0)

        if (baseFile?.encoding || encoding) {
            fileText = file?.getText(baseFile?.encoding ? baseFile.encoding : encoding)
        } else {
            fileText = file?.text
        }

        def md5 = AssetHelper.getByteDigest(fileText.bytes)
        if (!skipCache) {
            def cache = CacheManager.findCache(file.canonicalPath, md5)
            if (cache) {
                return cache
            }
        }

        def lessProcessor
        if (compilerMode == 'less4j') {
            lessProcessor = new Less4jProcessor(precompiler)
        } else {
            lessProcessor = new LessProcessor(precompiler)
        }
        fileText = lessProcessor.process(fileText, this)

        for (processor in processors) {
            def processInstance = processor.newInstance(precompiler)
            fileText = processInstance.process(fileText, this)
        }

        if (!skipCache) {
            CacheManager.createCache(file.canonicalPath, md5, fileText)
        }

        return fileText
    }

    String directiveForLine(String line) {
        line.find(/\*=(.*)/) { fullMatch, directive -> return directive }
    }
}
