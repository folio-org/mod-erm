package org.olf

import org.grails.io.support.PathMatchingResourcePatternResolver
import org.grails.io.support.Resource
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import static groovy.io.FileType.FILES

import groovy.transform.CompileStatic

@CompileStatic
class DashboardDefinitionsService {
  PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver()
  def jsonSlurper = new JsonSlurper()

  public getWidgetDefinitions() {
    Resource[] widgetDefs = resolver.getResources("classpath:sample_data/widgetDefinitions/*")
    ArrayList<JSONObject> definitions = [];
    widgetDefs.each { resource ->
      def stream = resource.getInputStream()
      def wd = jsonSlurper.parse(stream)
      definitions << wd
    }
    definitions
  }

}