package com.example.cryptoproject

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Test
import org.junit.runner.RunWith
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun TestFun() {
        val rnd = Random
        val k =
            javax.crypto.Cipher.getInstance("GOST3412-2015/CTR/NoPadding", BouncyCastleProvider())
        val key = SecretKeySpec(rnd.nextBytes(32), "GOST3412-2015")
        k.init(
            javax.crypto.Cipher.ENCRYPT_MODE,
            key,
            IvParameterSpec(rnd.nextBytes(16))
        )
    }

}
