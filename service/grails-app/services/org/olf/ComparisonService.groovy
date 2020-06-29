package org.olf

import static groovy.transform.TypeCheckingMode.SKIP

import java.time.LocalDate

import org.olf.erm.ComparisonPoint
import org.olf.erm.SubscriptionAgreement
import org.olf.kb.ErmTitleList
import org.olf.kb.Pkg
import org.olf.kb.TitleInstance

import groovy.transform.CompileStatic

@CompileStatic
class ComparisonService {
  
  public void compare ( OutputStream out, ComparisonPoint... titleLists) {
    List results = compare (titleLists)
    // Write to output stream..
  }
  
  public List compare ( ComparisonPoint... comparisonPoints ) {
    if (comparisonPoints.length < 1) throw new IllegalArgumentException("Require at least 2 Comparison Points to compare")
    comparisonPoints.collect { ComparisonPoint cp -> queryForComparisonResults(cp) }
  }
  
  private List queryForComparisonResults( ComparisonPoint comparisonPoints ) {
    Class<? extends ErmTitleList> type = comparisonPoints.titleList.class
    
    switch (type) {
      case SubscriptionAgreement:
        return queryForAgreementTitles(comparisonPoints.titleList.id, comparisonPoints.date)
        break
      case Pkg:
        return queryForPackageTitles(comparisonPoints.titleList.id, comparisonPoints.date)
        break
        
      default:
        throw new IllegalArgumentException("Invalid class of type ${type}")
    }
  }
  
  @CompileStatic(SKIP)
  List queryForPackageTitles ( final Serializable pkgId, LocalDate onDate = LocalDate.now() ) {
    TitleInstance.executeQuery("""
      SELECT pci, pti, ti, COALESCE( pci_coverage, pti_coverage, ti_coverage ), CAST(null as char)
      FROM TitleInstance as ti
        INNER JOIN ti.platformInstances as pti
          LEFT JOIN pti.coverage as pti_coverage
            INNER JOIN pti.packageOccurences as pci
              ON pci.pkg.id = :pkgId
              LEFT JOIN pci.coverage as pci_coverage
  
        LEFT JOIN ti.coverage as ti_coverage
      WHERE
        ( pci_coverage IS NOT NULL
          AND ( (pci_coverage.endDate IS NULL OR pci_coverage.endDate >= :onDate) AND (pci_coverage.startDate IS NULL OR pci_coverage.startDate <= :onDate) ))
        OR (
          pci_coverage IS NULL
          AND (
            ( pti_coverage IS NOT NULL
              AND ( (pti_coverage.endDate IS NULL OR pti_coverage.endDate >= :onDate) AND (pti_coverage.startDate IS NULL OR pti_coverage.startDate <= :onDate) ))
            OR (
              pti_coverage IS NULL
              AND (
                ( ti_coverage IS NOT NULL
                  AND ( (ti_coverage.endDate IS NULL OR ti_coverage.endDate >= :onDate) AND (ti_coverage.startDate IS NULL OR ti_coverage.startDate <= :onDate) ))
                OR (
                  ti_coverage IS NULL
                )
              )
            )
          )
        )
      ORDER BY ti.name, pci.id, pti.id
    """, ['onDate': onDate, 'pkgId': pkgId], [readOnly: true])
  }
  
  @CompileStatic(SKIP)
  List queryForAgreementTitles ( final Serializable agreementId, LocalDate onDate = LocalDate.now() ) {
    TitleInstance.executeQuery("""
      SELECT
          COALESCE( case when type(link) = PackageContentItem then link else pkg_pci end ) as pci,
          COALESCE( pkg_pci_pti, pci_pti, link ) as pti,
          COALESCE( pkg_pci_pti_ti, pci_pti_ti, pti_ti ) as ti,
          COALESCE( root_coverage, pkg_pci_cov, pkg_pci_pti_cov, pci_pti_cov, pkg_pci_pti_ti_cov, pci_pti_ti_cov, pti_ti_cov) as coverage,
          ent
      FROM Entitlement as ent
        INNER JOIN ent.resource as link

        LEFT JOIN link.coverage as root_coverage
        LEFT JOIN link.contentItems as pkg_pci
          LEFT JOIN pkg_pci.coverage as pkg_pci_cov
          LEFT JOIN pkg_pci.pti as pkg_pci_pti
            LEFT JOIN pkg_pci_pti.coverage as pkg_pci_pti_cov
            LEFT JOIN pkg_pci_pti.titleInstance as pkg_pci_pti_ti
              LEFT JOIN pkg_pci_pti.coverage as pkg_pci_pti_ti_cov
        LEFT JOIN link.pti as pci_pti
          LEFT JOIN pci_pti.coverage as pci_pti_cov
          LEFT JOIN pci_pti.titleInstance as pci_pti_ti
            LEFT JOIN pci_pti_ti.coverage as pci_pti_ti_cov
        LEFT JOIN link.titleInstance as pti_ti
          LEFT JOIN pti_ti.coverage as pti_ti_cov
      WHERE
        ent.owner.id = :agreementId
        
        AND (
          ( (ent.activeTo IS NULL OR ent.activeTo >= :onDate) AND ent.activeFrom <= :onDate)
          OR ( (ent.activeFrom IS NULL OR ent.activeFrom <= :onDate) AND ent.activeTo >= :onDate)
          OR (
            COALESCE( ent.activeTo, ent.activeFrom) IS NULL
            
            AND (
              ( root_coverage IS NOT NULL
                AND ( (root_coverage.endDate IS NULL OR root_coverage.endDate >= :onDate) AND (root_coverage.startDate IS NULL OR root_coverage.startDate <= :onDate) ))
              OR (
                root_coverage IS NULL
                AND (
                  ( pkg_pci_cov IS NOT NULL
                    AND ( (pkg_pci_cov.endDate IS NULL OR pkg_pci_cov.endDate >= :onDate) AND (pkg_pci_cov.startDate IS NULL OR pkg_pci_cov.startDate <= :onDate) ))
                  OR (
                    pkg_pci_cov IS NULL
                    AND (
                      ( pkg_pci_pti_cov IS NOT NULL
                        AND ( (pkg_pci_pti_cov.endDate IS NULL OR pkg_pci_pti_cov.endDate >= :onDate) AND (pkg_pci_pti_cov.startDate IS NULL OR pkg_pci_pti_cov.startDate <= :onDate) ))
                      OR (
                        pkg_pci_pti_cov IS NULL
                        AND (
                          ( pci_pti_cov IS NOT NULL
                            AND ( (pci_pti_cov.endDate IS NULL OR pci_pti_cov.endDate >= :onDate) AND (pci_pti_cov.startDate IS NULL OR pci_pti_cov.startDate <= :onDate) ))
                          OR (
                            pci_pti_cov IS NULL
                            AND (
                              ( pkg_pci_pti_ti_cov IS NOT NULL
                                AND ( (pkg_pci_pti_ti_cov.endDate IS NULL OR pkg_pci_pti_ti_cov.endDate >= :onDate) AND (pkg_pci_pti_ti_cov.startDate IS NULL OR pkg_pci_pti_ti_cov.startDate <= :onDate) ))
                              OR (
                                pkg_pci_pti_ti_cov IS NULL
                                AND (
                                  ( pci_pti_ti_cov IS NOT NULL
                                    AND ( (pci_pti_ti_cov.endDate IS NULL OR pci_pti_ti_cov.endDate >= :onDate) AND (pci_pti_ti_cov.startDate IS NULL OR pci_pti_ti_cov.startDate <= :onDate) ))
                                  OR (
                                    pci_pti_ti_cov IS NULL
                                    AND (
                                      ( pti_ti_cov IS NOT NULL
                                        AND ( (pti_ti_cov.endDate IS NULL OR pti_ti_cov.endDate >= :onDate) AND (pti_ti_cov.startDate IS NULL OR pti_ti_cov.startDate <= :onDate) ))
                                      OR (
                                        pti_ti_cov IS NULL
                                      )
                                    )
                                  )
                                )
                              )
                            )
                          )
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      ORDER BY pkg_pci_pti_ti.name, pci_pti_ti.name, pti_ti.name, pkg_pci.id, pkg_pci_pti.id, pci_pti.id, link.id, pkg_pci_pti.id, pci_pti.id
    """, ['onDate': onDate, 'agreementId': agreementId], [readOnly: true])
  }
}
