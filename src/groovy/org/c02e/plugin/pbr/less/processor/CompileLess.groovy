package org.c02e.plugin.pbr.less.processor

import com.asual.lesscss.LessEngine
import com.asual.lesscss.LessOptions
import com.asual.lesscss.loader.ChainedResourceLoader
import com.asual.lesscss.loader.ClasspathResourceLoader
import com.asual.lesscss.loader.CssProcessingResourceLoader
import com.asual.lesscss.loader.HTTPResourceLoader
import com.asual.lesscss.loader.JNDIResourceLoader
import com.asual.lesscss.loader.ResourceLoader
import com.asual.lesscss.loader.UnixNewlinesResourceLoader
import org.c02e.plugin.pbr.less.SourceFileResourceLoader
import org.c02e.plugin.pbr.Module
import org.c02e.plugin.pbr.build.Builder
import org.c02e.plugin.pbr.build.Processor

/**
 * Compiles less modules (those with 'text/less' content type) to css.
 */
class CompileLess implements Processor {

    String name
    Builder builder
    LessEngine engine

    void process(Module module) {
        // apply only to less modules
        def contentType = module.builtContentType ?: module.sourceContentType
        if (contentType != lessContentType) return

        def cssContentType = this.cssContentType
        def cssExt = builder.tools.getExtensionFromContentType(cssContentType)

        // compile less file to css
        def lessFile = builder.tools.getWorkingFile(module)
        def cssFile = new File(lessFile.parentFile, "${lessFile.name}.${cssExt}")
        // load main module less file from working dir
        // but pass to the engine the module source url as the source location
        // so that imports can be resolved relative to the original source file
        cssFile.setText(getEngine().compile(
            lessFile.getText(charset), module.sourceUrl, compress
        ), charset)

        // update built url and content-type for new css file
        module.builtContentType = cssContentType
        module.builtUrl = cssFile.path

        // update target url and content-type to be css instead of less
        if (module.targetContentType == lessContentType) {
            module.targetContentType = cssContentType
            module.targetUrl = builder.tools.setExtension(module.targetUrl, cssExt)
        }
    }

    LessEngine getEngine() {
        if (!engine)
            engine = new LessEngine(new LessOptions(options ?: [:]), resourceLoader)
        return engine
    }

	ResourceLoader getResourceLoader() {
		def loader = new ChainedResourceLoader(
            // replace default engine FilesystemResourceLoader
            new SourceFileResourceLoader(builder: builder),
            new ClasspathResourceLoader(LessEngine.class.classLoader),
            new JNDIResourceLoader(),
            new HTTPResourceLoader()
        )

		if (options?.css)
			loader = new CssProcessingResourceLoader(loader)

		loader = new UnixNewlinesResourceLoader(loader)
		return loader
	}

    String getLessContentType() {
        config?.lessContentType ?: 'text/less'
    }

    String getCssContentType() {
        config?.cssContentType ?: 'text/css'
    }

    String getCharset() {
        options?.charset ?: 'UTF-8'
    }

    boolean isCompress() {
        options?.compress
    }

    Map getOptions() {
        // ignore empty ConfigObject instances that are
        // created automatically if the config property is not specified
        config?.options?.findAll { k,v -> !(v instanceof ConfigObject) }
    }

    Map getConfig() {
        builder?.loader?.config?.processor?.less
    }

}
