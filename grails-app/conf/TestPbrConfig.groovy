
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
    definition {
        application {
            submodules = '*'
            stylesheet {
                url = 'css/test/app.less'
                disposition = 'head'
            }
            script = 'js/test/app.js'
        }
    }
}

