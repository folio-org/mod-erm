package org.olf.kb

import grails.gorm.MultiTenant

/**
 * KB or Knowledgebase is a loaded term in ERM contexts. Originally derived from the more generic computing
 * use of a repository of facts used to drive an inference engine, and the problem space being
 * "Can I access Volume 1 Issue 5 of X on platform Y". The idea was that instead of storring individual issue records (Or article records)
 * a KB would infer from coverage if a user had access to a particular desired content item.
 *
 * In this context a "RemoteKB" is any repository that makes assertions about titles, platforms, packages and coverage. We also extend
 * this model to include the concept of "Activation" - recording in a KB that we have "Switched on" a particular content item. Recording
 * activation is separate to "Why" we activated something - We may have several agreements that entitle us to access Vol 1 Issue 1 - present of
 * Nature on the Platform Nature.com - But we only activate that title once in each resolver or KB.
 *
 */
public class RemoteKB implements MultiTenant<RemoteKB> {

  String id
  String name
  String type // this is the name of a spring bean which will act
  String cursor // This may be a datetimestring, transaction or other service specific means of determining where we are up to
  String uri
  String listPrefix
  String fullPrefix
  String principal
  String credentials
  String syncStatus
  Long lastCheck
  Long rectype  // 1-PACKAGE

  public static final Long RECTYPE_PACKAGE = new Long(1);

  // Mark KB as protected/readonly, e.g. the LOCAL KB
  boolean readonly = false
  // Mark KB as a trusted source of title instance metadata
  boolean trustedSourceTI = false
  // Harvesting role
  /** Does this remote KB support harvesting */
  Boolean supportsHarvesting
  /** If supported, is harvesting from this remote KB activated */
  Boolean active

  // Content activation
  /** Does this remote KB support content activation */
  Boolean activationSupported
  /** If supported, is recording actication in this remote KB enabled */
  Boolean activationEnabled

  static mapping = {
                     id column:'rkb_id', generator: 'uuid2', length:36
                version column:'rkb_version'
                   name column:'rkb_name'
                 cursor column:'rkb_cursor'
                    uri column:'rkb_uri'
             fullPrefix column:'rkb_full_prefix'
             listPrefix column:'rkb_list_prefix'
                   type column:'rkb_type'
              principal column:'rkb_principal'
            credentials column:'rkb_creds'
                rectype column:'rkb_rectype'
                 active column:'rkb_active'
     supportsHarvesting column:'rkb_supports_harvesting'
      activationEnabled column:'rkb_activation_enabled'
    activationSupported column:'rkb_activation_supported'
             syncStatus column:'rkb_sync_status'
              lastCheck column:'rkb_last_check'
               readonly column:'rkb_readonly'
        trustedSourceTI column:'rkb_trusted_source_ti'
  }

  static constraints = {
                   name(nullable:false, blank:false, unique:true)
                 cursor(nullable:true, blank:false)
                    uri(nullable:true, blank:false)
                   type(nullable:true, blank:false)
             fullPrefix(nullable:true, blank:false)
             listPrefix(nullable:true, blank:false)
              principal(nullable:true, blank:false)
            credentials(nullable:true, blank:false)
                 active(nullable:true, blank:false)
     supportsHarvesting(nullable:true, blank:false)
      activationEnabled(nullable:true, blank:false)
    activationSupported(nullable:true, blank:false)
             syncStatus(nullable:true, blank:false)
              lastCheck(nullable:true, blank:false)
               readonly(nullable:true, blank:false, bindable:false)
        trustedSourceTI(nullable: false, blank: false)
  }


  public String toString() {
    return "RemoteKB ${name} - ${type}/${uri}/${cursor}".toString()
  }

  // We want SOME properties to be editable, some not to be, so we have custom setters to declare which we can and can't set
  def setName( String name ) {
    if (this.readonly == true) {
      log.debug("Denying update of name as 'readonly' is set to 'true'")
      return
    }
    this.name = name
  }

  def setType( String type ) {
    if (this.readonly == true) {
      log.debug("Denying update of type as 'readonly' is set to 'true'")
      return
    }
    this.type = type
  }

  def setCursor( String cursor ) {
    if (this.readonly == true) {
      log.debug("Denying update of cursor as 'readonly' is set to 'true'")
      return
    }
    this.cursor = cursor
  }

  def setUri( String uri ) {
    if (this.readonly == true) {
      log.debug("Denying update of uri as 'readonly' is set to 'true'")
      return
    }
    this.uri = uri
  }

  def setListPrefix( String listPrefix ) {
    if (this.readonly == true) {
      log.debug("Denying update of listPrefix as 'readonly' is set to 'true'")
      return
    }
    this.listPrefix = listPrefix
  }

  def setFullPrefix( String fullPrefix ) {
    if (this.readonly == true) {
      log.debug("Denying update of fullPrefix as 'readonly' is set to 'true'")
      return
    }
    this.fullPrefix = fullPrefix
  }

  def setPrincipal( String principal ) {
    if (this.readonly == true) {
      log.debug("Denying update of principal as 'readonly' is set to 'true'")
      return
    }
    this.principal = principal
  }

  def setCredentials( String credentials ) {
    if (this.readonly == true) {
      log.debug("Denying update of credentials as 'readonly' is set to 'true'")
      return
    }
    this.credentials = credentials
  }

  def setSyncStatus( String syncStatus ) {
    if (this.readonly == true) {
      log.debug("Denying update of syncStatus as 'readonly' is set to 'true'")
      return
    }
    this.syncStatus = syncStatus
  }

  def setLastCheck( Long lastCheck ) {
    if (this.readonly == true) {
      log.debug("Denying update of lastCheck as 'readonly' is set to 'true'")
      return
    }
    this.lastCheck = lastCheck
  }

  def setRectype( Long rectype ) {
    if (this.readonly == true) {
      log.debug("Denying update of rectype as 'readonly' is set to 'true'")
      return
    }
    this.rectype = rectype
  }

  def setReadonly( boolean readonly ) {
    if (this.readonly == true) {
      log.debug("Denying update of readonly as 'readonly' is set to 'true'")
      return
    }
    this.readonly = readonly
  }

  def setTrustedSourceTI( boolean trustedSourceTI ) {
    this.trustedSourceTI = trustedSourceTI
  }

  def setSupportsHarvesting( Boolean supportsHarvesting ) {
    if (this.readonly == true) {
      log.debug("Denying update of supportsHarvesting as 'readonly' is set to 'true'")
      return
    }
    this.supportsHarvesting = supportsHarvesting
  }

  def setActive( Boolean active ) {
    if (this.readonly == true) {
      log.debug("Denying update of active as 'readonly' is set to 'true'")
      return
    }
    this.active = active
  }

  def setActivationSupported( Boolean activationSupported ) {
    if (this.readonly == true) {
      log.debug("Denying update of activationSupported as 'readonly' is set to 'true'")
      return
    }
    this.activationSupported = activationSupported
  }

  def setActivationEnabled( Boolean activationEnabled ) {
    if (this.readonly == true) {
      log.debug("Denying update of activationEnabled as 'readonly' is set to 'true'")
      return
    }
    this.activationEnabled = activationEnabled
  }

  def beforeDelete() {
    if (this.readonly == true) {
      log.debug("Denying to delete KB ${this.id} / ${this.name} because 'readonly' is set to 'true'")
      return false
    }
  }

}
