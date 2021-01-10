/*
 * Copyright 2020 Pegasystems Inc. All rights reserved.
 * Use of this source code is governed by a Apache 2.0 license that can be found in the LICENSE file.
 */

package com.pega.mmock.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class MMockPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
        }
    }
}
