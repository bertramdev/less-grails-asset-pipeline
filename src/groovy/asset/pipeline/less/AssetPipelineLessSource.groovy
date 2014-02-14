package asset.pipeline.less

import com.github.sommeri.less4j.*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


class AssetPipelineLessSource extends LessSource {
	File sourceFile
	String contents
	Map options
	public AssetPipelineLessSource(File file, contents, options=[:]) {
		sourceFile = file
		this.options = options
		this.contents = contents

	}

 	public LessSource relativeSource(String filename) {
 		    def assetRelativePath = relativePath(sourceFile)
	        def paths = AssetHelper.getAssetPaths()
	        def relativePaths = paths.collect { [it,assetRelativePath].join(AssetHelper.DIRECTIVE_FILE_SEPARATOR)}
	        paths = relativePaths + paths
 		    // def paths = options.paths//[sourceFile.getParent()] + options.paths
 		    def matchedPath = paths.find { path ->
 		    	def file = new File(path, filename)
 		    	if(file.exists()) {
 		    		return true
 		    	}
 		    }

 		    if(matchedPath) {
 		    	def matchedFile = new File(matchedPath,filename)
 		    	if(options.baseFile) {
					CacheManager.addCacheDependency(options.baseFile.file.canonicalPath, matchedFile)

 		    	}
 		    	return new AssetPipelineLessSource(matchedFile,null,options)
 		    }

 		   //  def matchingPath = 
		    // log.debug "resolveUri: path=${path}"
		    // for (Object index : paths.getIds()) {
		    //   def it = paths.get(index, null)
		    //   def file = new File(it, path)
		    //   log.trace "test exists: ${file}"
		    //   if (file.exists()) {
		    //     log.trace "found file: ${file}"
		    //     if(assetFile) {
		    //       CacheManager.addCacheDependency(assetFile.file.canonicalPath, file)
		    //     }
		    //     return file.toURI().toString()
		    //   }
		    // }

		    return null
 	}

	def relativePath(file, includeFileName=false) {
	    def path
	    if(includeFileName) {
	      path = file.class.name == 'java.io.File' ? file.getCanonicalPath().split(AssetHelper.QUOTED_FILE_SEPARATOR) : file.file.getCanonicalPath().split(AssetHelper.QUOTED_FILE_SEPARATOR)
	    } else {
	      path = file.getParent().split(AssetHelper.QUOTED_FILE_SEPARATOR)
	    }

	    def startPosition = path.findLastIndexOf{ it == "grails-app" }
	    if(startPosition == -1) {
	      startPosition = path.findLastIndexOf{ it == 'web-app' }
	      if(startPosition+2 >= path.length) {
	        return ""
	      }
	      path = path[(startPosition+2)..-1]
	    }
	    else {
	      if(startPosition+3 >= path.length) {
	        return ""
	      }
	      path = path[(startPosition+3)..-1]
	    }

	    return path.join(AssetHelper.DIRECTIVE_FILE_SEPARATOR)
	}

	public String getContent() {
		if(contents) {
			return contents
		}
		return sourceFile.text
	}

	public byte[] getBytes() {
		if(contents) {
			return contents.bytes
		}
		return sourceFile.bytes
	}
}