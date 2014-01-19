class PreBuiltResourcesLessGrailsPlugin {
    // the plugin version
    def version = "0.1-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        'grails-app/conf/Test*',
        'grails-app/views/error.gsp',
        'grails-app/*/test/*.*',
        'web-app/*/test/*.*',
        'web-app/*/test/**/*.*',
    ]

    def title = "Pre-Built Resources: LESS Plugin"
    def author = "Justin Ludwig"
    def authorEmail = "justin@codetechnology.org"
    def description = '''
LESS CSS support for Pre-Built Resources Plugin.
    '''

    // URL to the plugin's documentation
    def documentation = ''//"http://grails.org/plugin/pre-built-resources-less"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [
        name: "CODE Technology",
        url: "http://codesurvey.org",
    ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

}
