import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.iapps.deera.etc.Api
import com.iapps.deera.ui.BaseUITest
import com.iapps.libs.helpers.BaseConstants
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginFragmentTest : BaseUITest() {

    val fragToTest =  LoginFragment()

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
    fun testGetCountryList_loadData_whenSuccess() {

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
    fun testGetCountryList_loadData_whenServerError() {

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

        //default to +60 and +65 on the selection

    }

    @Test
    fun testGetCountryList_loadData_whenNetworkNotAvailable() {

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

        //default to +60 and +65 on the selection

    }


    @Test
    fun testGetCountryList_loadData_whenAuthNotValid() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

        //default to +60 and +65 on the selection

    }

    @Test
    fun testGetCountryList_loadData_whenTimeout() {
        //had to change it in shorter time or else the test will taken much longer
        BaseConstants.TIMEOUT = 3000
        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
                            .setBody(getJson("GetCountryList_success.json"))
                            .throttleBody(1, BaseConstants.TIMEOUT.toLong(), TimeUnit.MILLISECONDS)

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

        //default to +60 and +65 on the selection

    }

    @Test
    fun testGetCountryList_loadData_whenResultNull() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_result_null.json"))
                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

        //default to +60 and +65 on the selection

    }

    @Test
    fun testGetCountryList_loadData_whenSuccessIncorrectStatusCode() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("Generic_http_response_incorrect_statuscode.json"))
                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

        //default to +60 and +65 on the selection

    }



    @Test
    fun testPostUserLogin_loadData_whenSuccess() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("PostUserLogin_success.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

    @Test
    fun testPostUserLogin_loadData_whenServerError() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
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
    fun testPostUserLogin_loadData_whenNetworkNotAvailable() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
                            .setBody(getJson("PostUserLogin_success.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }


    @Test
    fun testPostUserLogin_loadData_whenAuthNotValid() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
                            .setBody(getJson("PostUserLogin_success.json"))

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

    @Test
    fun testPostUserLogin_loadData_whenTimeout() {
        //had to change it in shorter time or else the test will taken much longer
        BaseConstants.TIMEOUT = 3000
        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
                            .setBody(getJson("PostUserLogin_success.json"))
                            .throttleBody(1, BaseConstants.TIMEOUT.toLong(), TimeUnit.MILLISECONDS)

                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

    @Test
    fun testPostUserLogin_loadData_whenResultNull() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("PostUserLogin_result_null.json"))
                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

    @Test
    fun testPostUserLogin_loadData_whenSuccessIncorrectStatusCode() {

        if(isMockServerEnabled())
            this.mockServer.setDispatcher(object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if(request.requestUrl.toString().contains(Api.countryList)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("GetCountryList_success.json"))

                    }else if(request.requestUrl.toString().contains(Api.userLogin)){
                        return MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getJson("Generic_http_response_incorrect_statuscode.json"))
                    }else{
                        return MockResponse().setResponseCode(404)
                    }
                }
            })

        launchFragment(fragToTest)

    }

}
