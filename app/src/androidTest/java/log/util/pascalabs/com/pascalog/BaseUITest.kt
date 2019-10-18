package com.pascalabs.log

import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.iapps.deera.BuildConfig
import com.iapps.deera.etc.Api
import com.iapps.deera.ui.activity.ActivityMain
import com.iapps.deera.ui.fragment.BaseFragment
import com.iapps.libs.helpers.BaseConstants
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.util.concurrent.TimeUnit
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.ByteArrayOutputStream
import java.util.concurrent.CountDownLatch

abstract class BaseUITest {

    lateinit var mockServer: MockWebServer
    lateinit var activity : ActivityMain

    @Rule
    @JvmField
    var homeActivityTestRule = ActivityTestRule(ActivityMain::class.java, true, false)

    @Before
    open fun setUp(){
        BaseConstants.IS_FOR_UNIT_TESTING = true
        BaseConstants.STOP_FOR_AWHILE_SEE_RESULT = true
        this.configureMockServer()
    }

    @After
    open fun tearDown(){
        if(BaseConstants.STOP_FOR_AWHILE_SEE_RESULT){
            // this code will stop from exiting after the test done
            val countdown = CountDownLatch(1)
            countdown.await()
            //Thread.sleep(5000)
        }
        this.stopMockServer()
    }

    // MOCK SERVER
    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    open fun configureMockServer(){

        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }

        checkServerUrlSetting()
    }

    open fun checkServerUrlSetting(){
        if (isMockServerEnabled()){
            val baseUrl = mockServer.url("/")

            Api.BASE_URL = baseUrl.url().toURI().toString()
            Api.WHICH_SERVER = BuildConfig.WHICH_SERVER

        }else{
            Api.BASE_URL = BuildConfig.BASE_URL
            Api.WHICH_SERVER = BuildConfig.WHICH_SERVER
        }
    }

    open fun stopMockServer() {
        try {
            if (isMockServerEnabled()){
                mockServer.shutdown()
            }
        } catch (e: Exception) {}
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    open fun mockHttpResponseWithTimeout(fileName: String, responseCode: Int, timeout: Long) {
        mockServer.enqueue(MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
                .throttleBody(1, timeout, TimeUnit.MILLISECONDS))
    }

    public fun getJson(path : String) : String {
        val inputstream = InstrumentationRegistry.getContext().getAssets().open(path)

        val buffer = ByteArrayOutputStream()
        var nRead: Int
        val data = ByteArray(1024)
        while (true) {
            val nRead = inputstream.read(data, 0, data.size)
            if(nRead == -1) break;
            buffer.write(data, 0, nRead)
        }

        buffer.flush()
        val byteArray = buffer.toByteArray()
        return String(byteArray)
    }

    fun launchFragment(fragToTest : BaseFragment) {
        homeActivityTestRule.launchActivity(null)
        checkServerUrlSetting()
        activity = homeActivityTestRule.activity
        InstrumentationRegistry.getInstrumentation().runOnMainSync(Runnable {
            activity.setFragment(fragToTest)
        })
    }
}
