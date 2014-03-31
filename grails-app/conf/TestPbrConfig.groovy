
processor {
    order = '''
        org.c02e.plugin.pbr.build.processor.FillInContentType
        org.c02e.plugin.pbr.build.processor.FillInDisposition
        org.c02e.plugin.pbr.build.processor.FillInLastModified
        org.c02e.plugin.pbr.build.processor.ApplyBaseUrl
        org.c02e.plugin.pbr.less.processor.CompileLess
        org.c02e.plugin.pbr.build.processor.DeployToTargetDir
    '''
    less {
        options {
            // compress = true
            lineNumbers = 'comments'
        }
    }
}

module {
    hook = '''
        org.c02e.plugin.pbr.load.hook.StarSubModuleHook
        org.c02e.plugin.pbr.load.hook.SimpleModuleHook
        org.c02e.plugin.pbr.load.hook.StarUrlHook
    '''
    definition {
        application {
            submodules = '*'
            script = 'js/test/app.js'
            stylesheet = 'css/test/app.less'
        }
    }
}

