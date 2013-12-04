package com.pitchstone.plugin.pbr.less.processor

import com.pitchstone.plugin.pbr.build.base.BaseBuilder
import com.pitchstone.plugin.pbr.load.base.BaseLoader
import com.pitchstone.plugin.pbr.load.base.BaseModule
import spock.lang.Specification

class CompileLessSpec extends Specification {

    def workingDir = new File("${System.properties.'java.io.tmpdir'}/pbr-test/work")
    def processor = new CompileLess(name: 'test-processor',
        builder: new BaseBuilder(new BaseLoader(workingDir: workingDir.path)))

    def setup() {
        workingDir.deleteDir()
    }

    def "process with empty module does nothing"() {
        when: processor.process new BaseModule()
        then: !workingDir.list()
    }

    def "process with non-less module does noting"() {
        when: processor.process new BaseModule(url: 'foo.txt')
        then: !workingDir.list()
    }

    def "process with missing less file throws IOException"() {
        when: processor.process new BaseModule(
            url: 'foo.less',
            contentType: 'text/less',
        )
        then: thrown IOException
    }

    def "process with less file compiles it to css"() {
        when: processor.process new BaseModule(
            id: 'app',
            url: 'css/test/app.less',
            contentType: 'text/less',
        )
        then: new File(workingDir, 'app.less.css').length() > 0
    }

    def "process with less file updates module's built and target settings"() {
        setup: def module = new BaseModule(
            id: 'app',
            url: 'css/test/app.less',
            contentType: 'text/less',
        )
        when: processor.process module
        then:
            module.builtUrl == new File(workingDir, 'app.less.css').path
            module.builtContentType == 'text/css'
            module.targetUrl == 'css/test/app.css'
            module.targetContentType == 'text/css'
    }

    def "when module's target settings are not less, doesn't update target settings"() {
        setup: def module = new BaseModule(
            id: 'app',
            url: 'css/test/app.less',
            contentType: 'text/less',
            targetUrl: 'foo/bar',
            targetContentType: 'application/x-foo',
        )
        when: processor.process module
        then:
            module.builtUrl == new File(workingDir, 'app.less.css').path
            module.builtContentType == 'text/css'
            module.targetUrl == 'foo/bar'
            module.targetContentType == 'application/x-foo'
    }
}
