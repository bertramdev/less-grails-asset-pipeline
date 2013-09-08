package asset.pipeline.less
import asset.pipeline.AssetHelper
import org.mozilla.javascript.Context
import org.mozilla.javascript.JavaScriptException
import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.NativeArray
import org.springframework.core.io.ClassPathResource
import groovy.util.logging.Log4j
import asset.pipeline.CacheManager

@Log4j
class LessProcessor {
  public static final java.lang.ThreadLocal threadLocal = new ThreadLocal();
  Scriptable globalScope
  ClassLoader classLoader
  def precompilerMode

  LessProcessor(precompiler=false){
    this.precompilerMode = precompiler
    try {
      classLoader = getClass().getClassLoader()

      def shellJsResource    = new ClassPathResource('asset/pipeline/less/shell.js', classLoader)
      def envRhinoJsResource = new ClassPathResource('asset/pipeline/less/env.rhino.js', classLoader)
      def hooksJsResource    = new ClassPathResource('asset/pipeline/less/hooks.js', classLoader)
      def lessJsResource     = new ClassPathResource('asset/pipeline/less/less-1.3.3.js', classLoader)
      def compileJsResource  = new ClassPathResource('asset/pipeline/less/compile.js', classLoader)

      Context cx = Context.enter()
      cx.setOptimizationLevel(-1)
      globalScope = cx.initStandardObjects()
      this.evaluateJavascript(cx, shellJsResource)
      this.evaluateJavascript(cx, envRhinoJsResource)
      this.evaluateJavascript(cx, hooksJsResource)
      this.evaluateJavascript(cx, lessJsResource)
      this.evaluateJavascript(cx, compileJsResource)

    } catch (Exception e) {
      throw new Exception("LESS Engine initialization failed.", e)
    } finally {
      try {
        Context.exit()
      } catch (IllegalStateException e) {}
    }
  }

  def evaluateJavascript(context, resource) {
    def inputStream = resource.inputStream
    context.evaluateReader(globalScope, new InputStreamReader(inputStream, 'UTF-8'), resource.filename, 0, null)
  }

  def process(input, assetFile) {
    try {
      if(!this.precompilerMode) {
        threadLocal.set(assetFile);
      }

      def paths = AssetHelper.getAssetPaths()
      def pathstext = paths.collect{
        def p = it.replaceAll("\\\\", "/")
        if (p.endsWith("/")) {
          "'${p}'"
        } else {
          "'${p}/'"
        }
      }.toString()

      def cx = Context.enter()
      def compileScope = cx.newObject(globalScope)
      compileScope.setParentScope(globalScope)
      compileScope.put("lessSrc", compileScope, input)

      def result = cx.evaluateString(compileScope, "compile(lessSrc, ${pathstext})", "LESS compile command", 0, null)
      return result
    } catch (JavaScriptException e) {
      throw new Exception("""
        LESS Engine compilation of LESS to CSS failed.
        $e: ${e.value}
        """,e)
    } catch (Exception e) {
      throw new Exception("""
        LESS Engine compilation of LESS to CSS failed.
        $e
        """)
    } finally {
      Context.exit()
    }
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
        if(assetFile) {
          CacheManager.addCacheDependency(assetFile.file.canonicalPath, file)
        }
        return file.toURI().toString()
      }
    }

    return null
  }
}
