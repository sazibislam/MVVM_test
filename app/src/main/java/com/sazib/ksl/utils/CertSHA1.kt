package com.sazib.ksl.utils

import android.annotation.SuppressLint
import android.content.Context

object CertSHA1 {

    private const val X509 = "X509"
    private const val SHA1 = "SHA1"

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun getCertificateSHA1(
        argContext: Context?
    ): String {
        if (argContext == null) return "Context is NULL"

        return "test"
    }
}