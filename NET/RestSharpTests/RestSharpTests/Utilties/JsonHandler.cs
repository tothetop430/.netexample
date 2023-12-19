using System;
using System.Collections.Generic;
using System.IO;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace RestSharpTests.Utilties
{
    public class JsonHandler
    {
        private static FileInfo? jsonFile;

       public static bool IsInitialized()
        {
            return jsonFile!= null;
        }
        public JsonHandler()
        {

        }
        public JsonHandler(FileInfo jsonFile)
        {
            JsonHandler.jsonFile = jsonFile;
        }

        public bool IsJsonValid(string text)
        {
            try
            {
                JToken.Parse(text);
            }
            catch (JsonException)
            {
                return false;
            }
            return true;
        }

        public JArray GetArrayFromJsonFile()
        {
            JArray jsonArray = null;
            try
            {
                using (StreamReader reader = new StreamReader(jsonFile.FullName))
                {
                    using (JsonTextReader jsonReader = new JsonTextReader(reader))
                    {
                        jsonArray = JArray.Load(jsonReader);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return jsonArray;
        }

        public static JObject GetObjFromJsonFile()
        {
            JObject jsonObj = null;
            try
            {
                using (StreamReader reader = new StreamReader(jsonFile.FullName))
                {
                    using (JsonTextReader jsonReader = new JsonTextReader(reader))
                    {
                        jsonObj = JObject.Load(jsonReader);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return jsonObj;
        }

        public List<Dictionary<string, string>> GetJsonData()
        {
            JArray jsonArray = GetArrayFromJsonFile();
            List<Dictionary<string, string>> jsonData = new List<Dictionary<string, string>>();

            foreach (JObject jObj in jsonArray)
            {
                Dictionary<string, string> map = new Dictionary<string, string>();
                foreach (var keyValuePair in jObj)
                {
                    map.Add(keyValuePair.Key, keyValuePair.Value.ToString());
                }
                jsonData.Add(map);
            }
            return jsonData;
        }

        public static Dictionary<string, string> GetTestDataFromJson()
        {
            JObject jsonObj = GetObjFromJsonFile();
            Dictionary<string, string> jsonData = new Dictionary<string, string>();

            foreach (var key in jsonObj.Properties())
            {
                JObject valueObj = (JObject)key.Value;
                jsonData.Add(key.Name, valueObj.ToString());
            }
            return jsonData;
        }

        public Dictionary<string, string> JsonObjectToStringMap(JObject jsonObject)
        {
            Dictionary<string, string> objMap = new Dictionary<string, string>();
            try
            {
                foreach (var keyValuePair in jsonObject)
                {
                    objMap.Add(keyValuePair.Key, keyValuePair.Value.ToString());
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return objMap;
        }
    }
}
