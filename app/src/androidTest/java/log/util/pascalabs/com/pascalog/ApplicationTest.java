package log.util.pascalabs.com.pascalog;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
   val fragToTest =  Log1Fragment()

    override fun isMockServerEnabled(): Boolean = true

    @Before
    override fun setUp(){
        super.setUp()
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun testLog1_loadData_whenSuccess() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

    @Test
    fun testLog1_loadData_whenServerError() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                            .setBody(getJson("Generic_http_response_php_error.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

    @Test
    fun testLog1_loadData_whenNetworkNotAvailable() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }
}
