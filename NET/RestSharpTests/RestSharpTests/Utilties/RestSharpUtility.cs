using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using RestSharp;

namespace RestSharpTests.Utilties
{
    public class RestSharpUtility
    {
        private string BaseURI;

        private IRestClient restClient;

        private RestRequest restRequest;
        public RestSharpUtility(string baseURI)
        {
            BaseURI = baseURI;
        }

        public RestRequest GetRestRequest(string endpoint)
        {
            restClient = new RestClient(BaseURI);
            restRequest = new RestRequest(endpoint);
            Console.WriteLine(endpoint);
            return restRequest;
        }
        public RestRequest GetRestRequestWithAuthentication(string endpoint)
        {

            restClient = new RestClient(BaseURI);
            restRequest = new RestRequest(endpoint);
            return restRequest;
        }

        public RestRequest addHeader(RestRequest request, string name, string value)
        {
            request.AddHeader(name, value);
            return request;
        }

        public RestRequest addHeaders(RestRequest request, Dictionary<string, string> customHeader)
        {
            request.AddHeaders(customHeader);
            return request;
        }

        public RestRequest addQueryParam(RestRequest request, string queryParam, string queryValue, bool encode = false)
        {
            Console.WriteLine("Add query param " + queryParam + " value " + queryValue);
            request.AddQueryParameter(queryParam, queryValue, encode);
            return request;
        }

        public RestRequest addPathParam(RestRequest request, string pathParam, string paramVal)
        {
            request.AddUrlSegment(pathParam, paramVal);
            return request;
        }

        public RestRequest addBody(RestRequest request, string body)
        {
            request.AddBody(body);
            return request;
        }

        public RestResponse GetRestResponse(RestRequest request, Method method)
        {
            Console.WriteLine("Request being called");
            
            RestResponse response;
            if (method == Method.Get)
            {
                response = restClient.GetAsync(request).GetAwaiter().GetResult();
            }

            else if (method == Method.Post)
            {
                response = restClient.GetAsync(request).GetAwaiter().GetResult();
            }

            else if (method == Method.Put)
            {
                response = restClient.PutAsync(request).GetAwaiter().GetResult();
            }

            else if (method == Method.Patch)
            {
                response = restClient.PatchAsync(request).GetAwaiter().GetResult();
            }
            else if (method == Method.Delete)
            {
                response = restClient.DeleteAsync(request).GetAwaiter().GetResult();
            }
            else
            {
                response = restClient.GetAsync(request).GetAwaiter().GetResult();
            }
            Console.WriteLine(response.Content);
            return response;
        }
    }
}
