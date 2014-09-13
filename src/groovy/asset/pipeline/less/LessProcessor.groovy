package asset.pipeline.less

import grails.util.Holders
import groovy.util.logging.Log4j
import asset.pipeline.AssetCompiler
import asset.pipeline.AssetFile
import asset.pipeline.less.compilers.*

@Log4j
class LessProcessor {
  AssetCompiler compiler
  
  LessProcessor(AssetCompiler compiler) {
    this.compiler = compiler
  }

  public def process (String input, AssetFile assetFile) {
    
    AbstractLessCompilerImpl lessCompiler
    if ((Holders.grailsApplication.config.grails.assets.less.compiler ?: 'less4j') == 'less4j') {
        lessCompiler = new Less4jCompiler(compiler)
    } else {
        lessCompiler = new LessJSCompiler(compiler)
    }
    
    // Return the processed text
    lessCompiler.process(input, assetFile)
  }
}
