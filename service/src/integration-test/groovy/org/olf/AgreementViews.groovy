package org.olf

import com.k_int.okapi.OkapiHeaders
import grails.testing.mixin.integration.Integration
import groovy.util.logging.Slf4j
import spock.lang.*

@Slf4j
@Integration
@Stepwise
class AgreementViews extends BaseSpec {
  
  def 'Ingest a test package' () {
    
    when: 'Testing package added'
      Map resp = doPost('/erm/package/import') {
        header {
          dataSchema {
            name "mod-agreements-package"
            version 1.0
          }
        }
        records ([
          {
            source "Folio Testing"
            reference "access_start_access_end_examples"
            name "access_start_access_end_tests Package"
            packageProvider {
              name "DIKU"
            }
            contentItems ([
              {
                depth "fulltext"
                accessStart "2011-01-01"
                accessEnd "2018-12-31"
                coverage ([
                  {
                    startDate "2018-04-01"
                    startVolume "1"
                    startIssue "1"
                  }
                ])
                platformTitleInstance {
                  platform "EUP Publishing"
                  platform_url "https://www.euppublishing.com"
                  url "https://www.euppublishing.com/loi/afg"
                  titleInstance {
                    name "Afghanistan"
                    identifiers ([
                      {
                        value "2399-357X"
                        namespace "issn"
                      }
                      {
                        value "2399-3588"
                        namespace "eissn"
                      }
                    ])
                    type "serial"
                  }
                }
              }
              {
                depth "fulltext"
                accessStart "2011-01-01"
                coverage ([
                  {
                    startDate "2017-01-01"
                    startVolume "1"
                    startIssue "1"
                  }
                ])
                platformTitleInstance {
                  platform "Equinox Journals"
                  platform_url "http://www.equinoxjournals.com"
                  url "http://www.equinoxjournals.com/AEFS/"
                  titleInstance {
                    name "Archaeological and Environmental Forensic Science"
                    identifiers ([
                      {
                        value "2052-3378"
                        namespace "issn"
                      }
                      {
                        value "2052-3386"
                        namespace "eissn"
                      }
                    ])
                    type "serial"
                  }
                }
              }
              {
                depth "fulltext"
                accessEnd "2018-12-31"
                coverage ([
                  {
                    startDate "2000-02-01"
                    startVolume "27"
                    startIssue "1"
                  }
                ])
                platformTitleInstance {
                  platform "EUP Publishing"
                  platform_url "https://www.euppublishing.com"
                  url "https://www.euppublishing.com/loi/anh"
                  titleInstance {
                    name "Archives of Natural History"
                    identifiers ([
                      {
                        value "0260-9541"
                        namespace "issn"
                      }
                      {
                        value "1755-6260"
                        namespace "eissn"
                      }
                    ])
                    type "serial"
                  }
                }
              }
              {
                depth "fulltext"
                accessStart "2025-01-01"
                coverage ([
                  {
                    startDate "2015-01-01"
                    startVolume "33"
                  }
                ])
                platformTitleInstance {
                  platform "JSTOR"
                  platform_url "https://www.jstor.org"
                  url "https://www.jstor.org/journal/bethunivj"
                  titleInstance {
                    name "Bethlehem University Journal"
                    identifiers ([
                      {
                        value "2521-3695"
                        namespace "issn"
                      }
                      {
                        value "2410-5449"
                        namespace "eissn"
                      }
                    ])
                    type "serial"
                  }
                }
              }
            ])
          }
        ])
      }
    and: 'Find the package by name'
      resp = doGet("/erm/package", [filters: ['name==access_start_access_end_tests']])
   
    then: 'Expect package found'
      assert resp.name == 'access_start_access_end_tests'
  }
}
