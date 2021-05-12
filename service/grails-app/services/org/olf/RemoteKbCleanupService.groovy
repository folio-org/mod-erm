package org.olf
import org.olf.kb.RemoteKB

/**
 * This service checks for existing Local KBs with the title 'LOCAL' and where readonly
 * is not set to TRUE. It sets readonly to TRUE for this RemoteKB.
 */
public class RemoteKbCleanupService {
    private static final String LOCAL_KB_UPDATE = '''UPDATE remotekb
SET rkb_readonly=TRUE
WHERE rkb_name LIKE 'LOCAL';
    '''
    
    def checkLocal() {
        log.debug("RemoteKbCleanupService: Check for RemoteKBs with name LOCAL")
        
        // #1 - Raw SQL
        RemoteKB.executeQuery(PENDING_JOBS_HQL)
        
        // #2 - ORM
        RemoteKB kb = RemoteKB.findByName('LOCAL_KB_UPDATE')
        if (kb) {
            if (!kb.readonly) {
                kb.readonly = Boolean.TRUE
                kb.save(flush:true, failOnError:true)
                log.info("RemoteKbCleanupService: Set readonly to TRUE for existing RemoteKB 'LOCAL'")
            }
        }
        
    }

}
