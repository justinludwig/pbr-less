package com.pitchstone.plugin.pbr.less.processor

import com.asual.lesscss.LessEngine
import com.asual.lesscss.LessOptions
import com.pitchstone.plugin.pbr.Module
import com.pitchstone.plugin.pbr.build.Builder
import com.pitchstone.plugin.pbr.build.Processor

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
        getEngine().compile lessFile, cssFile

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
            engine = new LessEngine(new LessOptions(config?.options))
        return engine
    }

    String getLessContentType() {
        config?.lessContentType ?: 'text/less'
    }

    String getCssContentType() {
        config?.cssContentType ?: 'text/css'
    }

    Map getConfig() {
        builder?.loader?.config?.processor?.less
    }

}
