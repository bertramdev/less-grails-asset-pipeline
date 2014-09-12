package asset.pipeline.less

import groovy.util.logging.Log4j;

import org.mozilla.javascript.NativeArray;

import asset.pipeline.AbstractAssetFile
import asset.pipeline.AssetHelper
import asset.pipeline.CacheManager
import asset.pipeline.less.LessProcessor
import asset.pipeline.less.compilers.*
import asset.pipeline.processors.CssProcessor
@Log4j
class LessAssetFile extends AbstractAssetFile {
  public static final java.lang.ThreadLocal threadLocal = new ThreadLocal();
  static final String contentType = 'text/css'
  static extensions = ['less', 'css.less']
  static final String compiledExtension = 'css'
  static processors = [LessProcessor, CssProcessor]

  @Override
  public String directiveForLine (String line) {
    return null;
  }

  static void print(text) {
    log.debug text
  }

  static String resolveUri(String path, NativeArray paths) {
    def assetFile = threadLocal.get();
    log.debug "resolveUri: path=${path}"
    for (Object index : paths.getIds()) {
      def it = paths.get(index, null)
      def file = new File(it, path)
      log.trace "test exists: ${file}"
      if (file.exists()) {
        log.trace "found file: ${file}"
        if (assetFile) {
          CacheManager.addCacheDependency(assetFile.file.canonicalPath, file)
        }
        return file.toURI().toString()
      }
    }

    return null
  }
}