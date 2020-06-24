package org.olf

import org.olf.kb.ErmTitleList

class ComparisonService {
  
  public void compare ( OutputStream out, ErmTitleList... titleLists) {
    if (titleLists.length < 1) throw new IllegalArgumentException("titleLists length less than 1")
  }
}
