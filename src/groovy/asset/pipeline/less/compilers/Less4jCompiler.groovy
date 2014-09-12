package asset.pipeline.less.compilers

import groovy.util.logging.Log4j
import asset.pipeline.AssetFile
import asset.pipeline.AssetHelper
import asset.pipeline.less.AssetPipelineLessSource

import com.github.sommeri.less4j.LessCompiler
import com.github.sommeri.less4j.core.ThreadUnsafeLessCompiler
import com.github.sommeri.less4j_javascript.Less4jJavascript

@Log4j
class Less4jCompiler extends A_LessCompilerImpl {

  Less4jCompiler(precompiler = false) {
    super(precompiler)
  }

  @Override
  public def process (String input, AssetFile assetFile) {
    try {
      def assetRelativePath = relativePath(assetFile.file)
      def paths = AssetHelper.getAssetPaths()
      def relativePaths = paths.collect { [it, assetRelativePath].join(AssetHelper.DIRECTIVE_FILE_SEPARATOR) }
      paths = relativePaths + paths

      def lessSource = new AssetPipelineLessSource(assetFile.file, input, [paths: paths, baseFile: assetFile])

      LessCompiler.Configuration configuration = new LessCompiler.Configuration()
      Less4jJavascript.configure(configuration);
      LessCompiler compiler = new ThreadUnsafeLessCompiler();
      def compilationResult = compiler.compile(lessSource, configuration);

      def result = compilationResult.getCss()

      return result
    } catch (Exception e) {
      if (precompilerMode) {
        def errorDetails = "LESS Engine Compiler Failed - ${assetFile.file.name}.\n"
        errorDetails += "**Did you mean to compile this file individually (check docs on exclusion)?**\n"
        log.error(errorDetails, e)
      } else {
        throw e
      }
    }
  }
}
