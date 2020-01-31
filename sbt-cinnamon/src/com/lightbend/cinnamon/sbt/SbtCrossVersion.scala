/*
 * Copyright © 2015–2020 Lightbend, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form
 * or by any means without the express written permission of Lightbend, Inc.
 */

package com.lightbend.cinnamon.sbt

import sbt.CrossVersion

// CrossVersion.Disabled in sbt 0.13 and CrossVersion.disabled in sbt 1.0
object SbtCrossVersion {
  val binary: CrossVersion = CrossVersion.binary
  val disabled: CrossVersion = CrossVersion.disabled

  def apply(cross: Boolean): CrossVersion = {
    if (cross) binary else disabled
  }
}
