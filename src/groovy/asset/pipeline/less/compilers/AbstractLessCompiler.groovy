package asset.pipeline.less.compilers

import asset.pipeline.AssetFile
import asset.pipeline.AssetHelper

abstract class AbstractLessCompiler {

  def precompilerMode

  AbstractLessCompiler(precompiler = false) {
    this.precompilerMode = precompiler ? true : false
  }

  def relativePath(file, includeFileName = false) {
    def path
    if (includeFileName) {
      path = file.class.name == 'java.io.File' ? file.getCanonicalPath().split(AssetHelper.QUOTED_FILE_SEPARATOR) : file.file.getCanonicalPath().split(AssetHelper.QUOTED_FILE_SEPARATOR)
    } else {
      path = file.getParent().split(AssetHelper.QUOTED_FILE_SEPARATOR)
    }

    def startPosition = path.findLastIndexOf { it == "grails-app" }
    if (startPosition == -1) {
      startPosition = path.findLastIndexOf { it == 'web-app' }
      if (startPosition + 2 >= path.length) {
        return ""
      }
      path = path[(startPosition + 2)..-1]
    } else {
      if (startPosition + 3 >= path.length) {
        return ""
      }
      path = path[(startPosition + 3)..-1]
    }

    return path.join(AssetHelper.DIRECTIVE_FILE_SEPARATOR)
  }

  abstract def process(String input, AssetFile assetFile)
}
