import org.olf.erm.LicenseAmendmentStatus
import groovy.transform.Field

@Field
LicenseAmendmentStatus licenseAmendmentStatus

json g.render(licenseAmendmentStatus, [expand: ['status']])