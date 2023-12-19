using RestSharpTests.Utilties;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata;
using System.Text;
using System.Threading.Tasks;

namespace RestSharpTests.Base
{
    public class TestDataProvider
    {
        public static IEnumerable<string> GetTestData(string casesNames)
        {
            if (!JsonHandler.IsInitialized())
            {
                JsonHandler handler =  new JsonHandler(new FileInfo(ObjectHelper.GetSampleTestDataFile));
            }
            Dictionary<string, string> TestData = JsonHandler.GetTestDataFromJson();
            string[] cases = casesNames.Split('|');

            //Here is what I changed
            List<String> tcData = new List<String>();

            foreach(KeyValuePair<string, string> pair in TestData)
            {
                if (cases.Contains(pair.Key))
                {
                    tcData.Add(pair.Value);
                }
            }
            return tcData.ToArray();
        }
    }
}
