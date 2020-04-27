package org.olf.kb

import grails.gorm.MultiTenant


/**
 * Recording the availability of a titleInstance on a platform
 */
public class PlatformTitleInstance extends ErmResource implements MultiTenant<PlatformTitleInstance> {

  TitleInstance titleInstance
  Platform platform
  String url
  
  String getName() {
    String truncTIName = titleInstance.name
    String truncPlatName = platform.name

    if (titleInstance.name.length() > 70) {
      truncTIName = titleInstance.name.take(70) << "..."
    }
    if (platform.name.length() > 70) {
      truncPlatName = platform.name.take(70) << "..."
    }

    return "'${truncTIName}' on Platform '${truncPlatName}'"
  }

  String getLongName() {
    "'${titleInstance.name}' on Platform '${platform.name}'"
  }

  static transients = ['longName']
  
  static hasMany = [
    packageOccurences: PackageContentItem,
  ]

  static mappedBy = [
    packageOccurences: 'pti'
  ]

  static mapping = {
        titleInstance column:'pti_ti_fk'
             platform column:'pti_pt_fk'
                  url column:'pti_url'
  }

  static constraints = {
          titleInstance(nullable:false, blank:false)
               platform(nullable:false, blank:false)
                    url(nullable:true, blank:false)
  }

}
