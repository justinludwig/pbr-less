
processor {
    order = '''
        com.pitchstone.plugin.pbr.build.processor.FillInContentType
        com.pitchstone.plugin.pbr.build.processor.FillInDisposition
        com.pitchstone.plugin.pbr.build.processor.FillInLastModified
        com.pitchstone.plugin.pbr.build.processor.ApplyBaseUrl
        com.pitchstone.plugin.pbr.less.processor.CompileLess
        com.pitchstone.plugin.pbr.build.processor.DeployToTargetDir
    '''
}

module {
    definition {
        application {
            submodules {
                stylesheet {
                    url = 'css/test/app.less'
                    disposition = 'head'
                }
                script = 'js/test/app.js'
            }
        }
    }
}

