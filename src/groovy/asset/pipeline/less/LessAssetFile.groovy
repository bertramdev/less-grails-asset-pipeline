package asset.pipeline.less

import groovy.util.logging.Log4j
import asset.pipeline.AbstractAssetFile
import asset.pipeline.less.compilers.*
import asset.pipeline.processors.CssProcessor
@Log4j
class LessAssetFile extends AbstractAssetFile {
  static final String contentType = 'text/css'
  static extensions = ['less', 'css.less']
  static final String compiledExtension = 'css'
  static processors = [LessProcessor, CssProcessor]

  @Override
  public String directiveForLine (String line) {
    return null;
  }
}