package com.pitchstone.plugin.pbr.less

import com.asual.lesscss.loader.ResourceLoader
import com.pitchstone.plugin.pbr.build.Builder

/**
 * Loads files (module files and imports) for LESS engine.
 * Module source paths are loaded from working dir,
 * while other paths are loaded relative to source dir.
 */
class SourceFileResourceLoader implements ResourceLoader {

    Builder builder

    // ResourceLoader

    @Override
    boolean exists(String path) throws IOException {
        // use working file for modules
        def module = builder.loader.getModuleForSourceUrl(path)
        if (module)
            return builder.tools.canWorkingFile(module)

        // otherwise (for imports) load from source dir
        path = path.replaceFirst(/^file:/, '')
        if (builder.tools.isLocalFile(path))
            return builder.tools.getLocalFile(path).exists()

        return false
    }

    @Override
    String load(String path, String charset) throws IOException {
        // use working file for modules
        def module = builder.loader.getModuleForSourceUrl(path)
        if (module)
            return builder.tools.getWorkingFile(module).getText(charset)

        // otherwise (for imports) load from source dir
        path = path.replaceFirst(/^file:/, '')
        builder.tools.getLocalFile(path).getText(charset)
    }
}
