package ksp.utils

import java.io.OutputStream

fun OutputStream.streamAndClose(code: String) {
    this.write(code.toByteArray())
    this.close()
}
